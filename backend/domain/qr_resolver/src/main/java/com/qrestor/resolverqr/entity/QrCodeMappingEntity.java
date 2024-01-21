package com.qrestor.resolverqr.entity;

import com.qrestor.resolverqr.user.enitity.SystemUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "qr_codes", schema = "qresolver", indexes = {
        @Index(name = "IDX_QR_CODE_URL", columnList = "qr_code", unique = true)
})
public class QrCodeMappingEntity {
    @Id
    @SequenceGenerator(name = "qr_codes_id_seq", sequenceName = "qr_codes_id_seq", allocationSize = 1, schema = "qresolver", initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qr_codes_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "qr_code", nullable = false, unique = true, updatable = false, length = 200)
    private String qrCode;

    @Column(name = "table_id")
    private Integer tableId;

    @Column(name = "restaurant_id", nullable = false)
    private UUID restaurantId;

    @Column(name = "menu_id", nullable = false)
    private UUID menuId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = SystemUser.class)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    private SystemUser user;

    @PrePersist
    public void prePersist() {
        if (userId == null) {
            userId = ((SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        }
    }
}
