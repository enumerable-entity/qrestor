package com.qrestor.auth.user.events;


import com.qrestor.auth.token.entity.TokenEntity;
import com.qrestor.auth.user.entity.SystemUserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static com.qrestor.auth.api.RestEndpoints.*;

@Component
public class UserRegistrationEventListener implements ApplicationListener<UsersEvent> {

    @Value("${app.domain}")
    private String domain;
    @Override
    public void onApplicationEvent(UsersEvent event) {

        switch (event.getUserEventType()) {
            case REGISTRATION:
                handleRegistration(event);
                break;
            case PASSWORD_RESET:
                handlePasswordReset(event);
                break;
            default:
                throw new RuntimeException("Invalid user event type");
        }



    }

    private void handlePasswordReset(UsersEvent event) {
        SystemUserEntity user = event.getNewUser();
        TokenEntity resetPassToken = event.getToken();
        //send token to kafka topic for password reset

        String url = domain + FORGOT_PASSWORD + resetPassToken.getValue();
        System.out.println("PASSWORD RESET REQUEST SENDED TO KAFKA WITH CONFIRAMTION URL: " + url);
    }

    private void handleRegistration(UsersEvent event) {
        SystemUserEntity newUser = event.getNewUser();
        TokenEntity activationEmailToken = event.getToken();
        //send token to kafka topic for email confirmation
        //send user to kafka topic for users

        String url = domain + AUTH + VERIFY_EMAIL + activationEmailToken.getValue();
        System.out.println("EMAIL CONFIRMATION REQUEST SENDED TO KAFKA WITH CONFIRAMTION URL: " + url);
    }

}
