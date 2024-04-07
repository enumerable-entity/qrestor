package com.qrestor.feedback.systemuser.enitity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "sync_users", schema = "feedback", indexes = {
        @Index(name = "system_users_uuid_idx", columnList = "uuid", unique = true)
})
public class SyncUser {

    @Id
    @Column(name = "uuid", nullable = false, updatable = false, unique = true)
    private UUID uuid;

    @Column(name = "username", nullable = false, updatable = false, unique = true)
    private String username;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SyncUser that = (SyncUser) o;
        return getUuid() != null && Objects.equals(getUuid(), that.getUuid());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
               "uuid = " + uuid + ", " +
               "username = " + username + ")";
    }
}