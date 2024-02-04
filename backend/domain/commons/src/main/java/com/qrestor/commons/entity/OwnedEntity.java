package com.qrestor.commons.entity;

import com.qrestor.security.SecurityUtils;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class OwnedEntity {

    public abstract UUID getUserId();

    public abstract void setUserId(UUID userId);

    @PrePersist
    public void prePersist() {
        if (getUserId() == null) {
            setUserId(SecurityUtils.getPrincipalUUID());
        }
    }

}