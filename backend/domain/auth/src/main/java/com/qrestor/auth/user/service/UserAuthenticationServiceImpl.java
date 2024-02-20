package com.qrestor.auth.user.service;

import com.qrestor.auth.api.dto.LoginRequestDTO;
import com.qrestor.auth.api.dto.LoginResponseDTO;
import com.qrestor.auth.api.dto.PasswordChangeDTO;
import com.qrestor.auth.api.dto.PasswordResetDTO;
import com.qrestor.auth.security.JwtService;
import com.qrestor.auth.token.enums.TokenType;
import com.qrestor.auth.token.entity.TokenEntity;
import com.qrestor.auth.token.service.TokenService;
import com.qrestor.models.dto.auth.UserDescriptorDTO;
import com.qrestor.auth.user.entity.SystemUserEntity;
import com.qrestor.auth.user.enums.UserEventType;
import com.qrestor.auth.user.events.UserEvent;
import com.qrestor.auth.user.mapper.UserDescriptorMapper;
import com.qrestor.auth.user.repository.SystemUserRepository;
import com.qrestor.auth.user.service.interfaces.UserAuthenticationService;
import com.qrestor.security.QrestorPrincipal;
import com.qrestor.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final SystemUserRepository userRepository;
    private final TokenService tokenService;
    private final ApplicationEventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDescriptorMapper userDescriptorMapper;

    @Override
    public void sendResetPasswordEmail(PasswordResetDTO resetDTO) {
        Optional<SystemUserEntity> user = userRepository.findOneByEmail(resetDTO.email());
        user.ifPresentOrElse(systemUserEntity -> {
            TokenEntity resetPassToken = tokenService.getNewTokenFor(systemUserEntity, TokenType.PASSWORD_RESET);
            tokenService.save(resetPassToken);
            eventPublisher.publishEvent(
                    new UserEvent(this, UserEventType.PASSWORD_RESET, systemUserEntity, resetPassToken));
        }, () -> {
            throw new RuntimeException("User not found");
        });
    }


    @Override
    public void resetPassword(PasswordResetDTO resetDTO) {
        Optional<TokenEntity> receivedToken = tokenService.getToken(resetDTO.token(), TokenType.PASSWORD_RESET);
        receivedToken.ifPresentOrElse(token -> {
            if (token.getUsedAt() != null) {
                throw new RuntimeException("Token already used");
            }
            token.setUsedAt(LocalDateTime.now());
            token.getUser().setPassword(passwordEncoder.encode(resetDTO.password()));
            tokenService.save(token);
        }, () -> {
            throw new RuntimeException("Invalid email confirmation token");
        });

    }

    @Override
    public void changePassword(PasswordChangeDTO changeDTO) {
        Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        boolean isOldPasswordCorrect = passwordEncoder.matches(changeDTO.oldPassword(), credentials.toString());
        if (!isOldPasswordCorrect) {
            throw new RuntimeException("Old password is incorrect");
        }else{
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            SystemUserEntity loggedUser = userRepository.findOneByUsername(username).orElseThrow();
            loggedUser.setPassword(passwordEncoder.encode(changeDTO.newPassword()));
            userRepository.save(loggedUser);
        }

    }

    @Override
    public LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO) {
        Authentication authenticate = authenticationManager.authenticate(mapLoginRequestToAuthentication(loginRequestDTO));
        return new LoginResponseDTO(
                jwtService.generateToken((QrestorPrincipal) authenticate.getPrincipal()),
                jwtService.generateToken((QrestorPrincipal) authenticate.getPrincipal()));
        //todo: implement refresh token
    }

    @Override
    public UserDescriptorDTO aboutMe() {
        Optional<SystemUserEntity> loggedInUser = userRepository.findOneByUsername(SecurityUtils.getPrincipalUsername());
        return loggedInUser.map(userDescriptorMapper::toDto).orElse(null);
    }

    private Authentication mapLoginRequestToAuthentication(LoginRequestDTO loginRequestDTO) {
        return new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password());
    }
}
