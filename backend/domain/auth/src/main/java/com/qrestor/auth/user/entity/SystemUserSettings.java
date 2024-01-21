package com.qrestor.auth.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "system_user_settings")
public class SystemUserSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "ui_theme", nullable = false)
    private String theme;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "timezone", nullable = false)
    private String timezone;

    @Column(name= "mfa_enabled", nullable = false)
    private boolean mfaEnabled;

    @Column(name = "mfa_type", nullable = false)
    private String mfaType;

    @Column(name = "mfa_secret", nullable = false)
    private String mfaSecret;

    @Column(name = "mfa_recovery_codes", nullable = false)
    private String mfaRecoveryCodes;

    @Column(name = "mfa_recovery_codes_used", nullable = false)
    private String mfaRecoveryCodesUsed;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SystemUserSettings that = (SystemUserSettings) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "theme = " + theme + ", " +
                "language = " + language + ", " +
                "currency = " + currency + ", " +
                "timezone = " + timezone + ", " +
                "mfaEnabled = " + mfaEnabled + ", " +
                "mfaType = " + mfaType + ", " +
                "mfaSecret = " + mfaSecret + ", " +
                "mfaRecoveryCodes = " + mfaRecoveryCodes + ", " +
                "mfaRecoveryCodesUsed = " + mfaRecoveryCodesUsed + ")";
    }
}