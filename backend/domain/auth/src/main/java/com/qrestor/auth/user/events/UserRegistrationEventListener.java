package com.qrestor.auth.user.events;


import com.qrestor.auth.kafka.dto.KafkaEmailSendRequestDTO;
import com.qrestor.auth.kafka.dto.UserKafkaSyncDTO;
import com.qrestor.auth.kafka.producers.KafkaProducer;
import com.qrestor.auth.token.entity.TokenEntity;
import com.qrestor.auth.user.entity.SystemUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.qrestor.auth.api.RestEndpoints.FORGOT_PASSWORD;
import static com.qrestor.auth.api.RestEndpoints.VERIFY_EMAIL;
import static com.qrestor.auth.common.enums.EmailRequestType.EMAIL_VERIFICATION;
import static com.qrestor.auth.common.enums.EmailRequestType.PASSWORD_RESET;

@Component
@RequiredArgsConstructor
public class UserRegistrationEventListener implements ApplicationListener<UserEvent> {

    private static final String USERNAME = "username";
    private static final String URL = "url";
    private final KafkaProducer<UserKafkaSyncDTO> userProducer;
    private final KafkaProducer<KafkaEmailSendRequestDTO> mailerProducer;

    @Value("${app.domain}")
    private String domain;
    @Value("${server.servlet.context-path}")
    private String api_prefix;

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
                prepareParams(user, FORGOT_PASSWORD, resetPassToken)));
    }

    private void handleRegistration(UserEvent event) {
        SystemUserEntity newUser = event.getNewUser();
        TokenEntity activationEmailToken = event.getToken();
        mailerProducer.send(new KafkaEmailSendRequestDTO(
                newUser.getEmail(),
                EMAIL_VERIFICATION,
                prepareParams(newUser, VERIFY_EMAIL, activationEmailToken)));
    }

    private Map<String, String> prepareParams(SystemUserEntity newUser, String path, TokenEntity token) {
        return Map.of(
                USERNAME, newUser.getUsername(),
                URL, prepareUrl(path, token)
        );
    }

    private String prepareUrl(String path, TokenEntity token) {
        return domain + api_prefix + path + token.getValue();
    }

    private void handleRegistrationConfirmation(UserEvent event) {
        SystemUserEntity confirmedUser = event.getNewUser();
        userProducer.send(new UserKafkaSyncDTO(confirmedUser.getUuid(), confirmedUser.getUsername()));
    }

}
