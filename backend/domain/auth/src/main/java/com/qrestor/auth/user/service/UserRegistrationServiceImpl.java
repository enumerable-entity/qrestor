package com.qrestor.auth.user.service;

import com.qrestor.auth.api.dto.RegistrationRequestDTO;
import com.qrestor.auth.config.security.enums.SystemRole;
import com.qrestor.auth.config.security.enums.TokenType;
import com.qrestor.auth.token.entity.TokenEntity;
import com.qrestor.auth.token.service.TokenService;
import com.qrestor.auth.user.entity.SystemUserEntity;
import com.qrestor.auth.user.enums.UserEventType;
import com.qrestor.auth.user.events.UsersEvent;
import com.qrestor.auth.authority.RoleRepository;
import com.qrestor.auth.user.repository.SystemUserRepository;
import com.qrestor.auth.user.service.interfaces.UserRegistrationService;
import com.qrestor.auth.user.validation.SystemUserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final SystemUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SystemUserValidator systemUserValidator;
    private final ApplicationEventPublisher eventPublisher;
    private final TokenService tokenService;


    @Override
    @Transactional
    public void registerNewUser(RegistrationRequestDTO registrationRequest) {
        systemUserValidator.validateRegistration(registrationRequest);
        SystemUserEntity newSystemUser = SystemUserEntity.builder()
                .username(registrationRequest.username())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .email(registrationRequest.email())
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .enabled(false)
                .authorities(roleRepository.findByAuthority(SystemRole.RESTAURATEUR.name()))
                .build();
        userRepository.save(newSystemUser);
        eventPublisher.publishEvent(
                new UsersEvent(this, UserEventType.REGISTRATION, newSystemUser,
                        tokenService.getNewTokenFor(newSystemUser, TokenType.EMAIL_VERIFICATION)));
    }

    @Override
    public void confirmNewUserEmail(String emailConfirmationToken) {
        Optional<TokenEntity> receivedToken = tokenService.getToken(emailConfirmationToken, TokenType.EMAIL_VERIFICATION);
        receivedToken.ifPresentOrElse(token -> {
            if (token.getUsedAt() != null) {
                throw new RuntimeException("Email already confirmed");
            }
            token.setUsedAt(LocalDateTime.now());
            token.getUser().setEnabled(true);
            tokenService.save(token);
        }, () -> {
            throw new RuntimeException("Invalid email confirmation token");
        });
    }
}
