package com.qrestor.commons.entity;

import com.qrestor.commons.security.SecurityUtils;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class OwnedEntity {

    public abstract void setUserId(UUID userId);
    public abstract UUID getUserId();

    @PrePersist
    public void prePersist() {
        if (getUserId() == null) {
            setUserId(SecurityUtils.getPrincipalUUID());
        }
    }

}