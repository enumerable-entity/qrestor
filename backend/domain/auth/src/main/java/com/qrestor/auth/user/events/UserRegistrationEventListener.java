package com.qrestor.auth.user.events;


import com.qrestor.auth.kafka.producers.KafkaProducer;
import com.qrestor.auth.token.entity.TokenEntity;
import com.qrestor.auth.user.entity.SystemUserEntity;
import com.qrestor.models.dto.kafka.KafkaEmailSendRequestDTO;
import com.qrestor.models.dto.kafka.UserKafkaSyncDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.qrestor.auth.api.RestEndpoints.*;
import static com.qrestor.models.EmailRequestType.EMAIL_VERIFICATION;
import static com.qrestor.models.EmailRequestType.PASSWORD_RESET;
import static com.qrestor.models.dto.kafka.KafkaEmailSendRequestDTO.URL_PARAM;
import static com.qrestor.models.dto.kafka.KafkaEmailSendRequestDTO.USER_NAME_PARAM;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserRegistrationEventListener implements ApplicationListener<UserEvent> {

    private final KafkaProducer<UserKafkaSyncDTO> userProducer;
    private final KafkaProducer<KafkaEmailSendRequestDTO> mailerProducer;

    @Value("${app.domain}")
    private String domain;

    @Override
    public void onApplicationEvent(UserEvent event) {

        switch (event.getUserEventType()) {
            case REGISTRATION:
                handleRegistration(event);
                break;
            case PASSWORD_RESET:
                handlePasswordReset(event);
                break;
            case REGISTRATION_CONFIRMATION:
                handleRegistrationConfirmation(event);
                break;
            default:
                throw new RuntimeException("Invalid user event type");
        }
    }

    private void handlePasswordReset(UserEvent event) {
        SystemUserEntity user = event.getNewUser();
        TokenEntity resetPassToken = event.getToken();
        mailerProducer.send(new KafkaEmailSendRequestDTO(
                user.getEmail(),
                PASSWORD_RESET,
                prepareParams(user, AUTH + FORGOT_PASSWORD, resetPassToken)));
    }

    private void handleRegistration(UserEvent event) {
        SystemUserEntity newUser = event.getNewUser();
        TokenEntity activationEmailToken = event.getToken();
        log.info("Sending activation email: {}", activationEmailToken);
        mailerProducer.send(new KafkaEmailSendRequestDTO(
                newUser.getEmail(),
                EMAIL_VERIFICATION,
                prepareParams(newUser, REGISTRATION + VERIFY_EMAIL, activationEmailToken)));
    }

    private Map<String, String> prepareParams(SystemUserEntity newUser, String path, TokenEntity token) {
        return Map.of(
                USER_NAME_PARAM, newUser.getUsername(),
                URL_PARAM, prepareUrl(path, token)
        );
    }

    private String prepareUrl(String path, TokenEntity token) {
        return domain + path + token.getValue();
    }

    private void handleRegistrationConfirmation(UserEvent event) {
        SystemUserEntity confirmedUser = event.getNewUser();
        userProducer.send(new UserKafkaSyncDTO(confirmedUser.getId(), confirmedUser.getUuid(), confirmedUser.getUsername()));
    }

}
