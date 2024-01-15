package com.qrestor.auth.user.events;

import com.qrestor.auth.token.entity.TokenEntity;
import com.qrestor.auth.user.entity.SystemUserEntity;
import com.qrestor.auth.user.enums.UserEventType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
@Setter
public class UsersEvent extends ApplicationEvent {

    private final UserEventType userEventType;
    private final SystemUserEntity newUser;
    private final TokenEntity token;


    public UsersEvent(Object source, UserEventType userEventType, SystemUserEntity newUser, TokenEntity token) {
        super(source);
        this.userEventType = userEventType;
        this.newUser = newUser;
        this.token = token;
    }

    public UsersEvent(Object source, Clock clock, UserEventType userEventType, SystemUserEntity newUser, TokenEntity token) {
        super(source, clock);
        this.userEventType = userEventType;
        this.newUser = newUser;
        this.token = token;
    }
}
