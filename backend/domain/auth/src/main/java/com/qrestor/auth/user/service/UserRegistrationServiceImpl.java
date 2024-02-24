package com.qrestor.auth.user.service;

import com.qrestor.auth.api.dto.RegistrationRequestDTO;
import com.qrestor.auth.authority.RoleService;
import com.qrestor.auth.token.entity.TokenEntity;
import com.qrestor.auth.token.enums.TokenType;
import com.qrestor.auth.token.service.TokenService;
import com.qrestor.auth.user.entity.SystemUserEntity;
import com.qrestor.auth.user.enums.UserEventType;
import com.qrestor.auth.user.events.UserEvent;
import com.qrestor.auth.user.mapper.AddressMapper;
import com.qrestor.auth.user.mapper.UserInformationMapper;
import com.qrestor.auth.user.mapper.UserSettingsMapper;
import com.qrestor.auth.user.repository.SystemUserRepository;
import com.qrestor.auth.user.service.interfaces.UserRegistrationService;
import com.qrestor.auth.user.validation.SystemUserValidator;
import com.qrestor.security.SystemRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final SystemUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SystemUserValidator systemUserValidator;
    private final ApplicationEventPublisher eventPublisher;
    private final TokenService tokenService;
    private final RoleService roleService;

    private final AddressMapper addressMapper;
    private final UserInformationMapper userInformationMapper;
    private final UserSettingsMapper userSettingsMapper;


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
                .authorities(roleService.findByAuthority(SystemRole.RESTAURATEUR.name()))
                .information(userInformationMapper.toEntity(registrationRequest.information()))
                .address(addressMapper.toEntity(registrationRequest.address()))
                .settings(userSettingsMapper.toEntity(registrationRequest.settings()))
                .build();
        userRepository.save(newSystemUser);
        userRepository.flush();
        eventPublisher.publishEvent(
                new UserEvent(this, UserEventType.REGISTRATION, newSystemUser,
                        tokenService.getNewTokenFor(newSystemUser, TokenType.EMAIL_VERIFICATION)));
        log.info("New user registered: {}", newSystemUser.getUsername());
    }

    @Override
    public boolean confirmNewUserEmail(String emailConfirmationToken) {
        Optional<TokenEntity> receivedToken = tokenService.getToken(emailConfirmationToken, TokenType.EMAIL_VERIFICATION);

        if(receivedToken.isPresent()){
            if (receivedToken.get().getUsedAt() != null) {
                return false;
            }
            receivedToken.get().setUsedAt(LocalDateTime.now());
            receivedToken.get().getUser().setEnabled(true);
            tokenService.save(receivedToken.get());

            eventPublisher.publishEvent(
                    new UserEvent(this, UserEventType.REGISTRATION_CONFIRMATION, receivedToken.get().getUser(), null));
            return true;
        }else {
            return true;
        }
    }
}
