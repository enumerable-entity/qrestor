package com.qrestor.auth.user.events;

import com.qrestor.auth.token.entity.TokenEntity;
import com.qrestor.auth.user.entity.SystemUserEntity;
import com.qrestor.auth.user.enums.UserEventType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserEvent extends ApplicationEvent {

    private final UserEventType userEventType;
    private final SystemUserEntity newUser;
    private final TokenEntity token;


    public UserEvent(Object source, UserEventType userEventType, SystemUserEntity newUser, TokenEntity token) {
        super(source);
        this.userEventType = userEventType;
        this.newUser = newUser;
        this.token = token;
    }
}
