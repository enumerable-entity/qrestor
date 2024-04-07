package com.qrestor.resolverqr.entity;

import com.qrestor.commons.entity.OwnedEntity;
import com.qrestor.commons.entity.PublicEntity;
import com.qrestor.resolverqr.systemuser.enitity.SyncUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "qr_codes", schema = "qresolver", indexes = {
        @Index(name = "IDX_QR_CODE_URL", columnList = "qr_code", unique = true)
})
public class QrCodeMappingEntity extends OwnedEntity implements PublicEntity {
    @Id
    @SequenceGenerator(name = "qr_codes_id_seq", sequenceName = "qr_codes_id_seq", allocationSize = 1, schema = "qresolver", initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qr_codes_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true, updatable = false, length = 200)
    private UUID publicId;

    @Column(name = "table_id")
    private Integer tableId;

    @Column(name = "sell_point_id", nullable = false)
    private UUID sellingPointId;

    @Column(name = "menu_id", nullable = false)
    private UUID menuId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "user_id", updatable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = SyncUser.class)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    private SyncUser user;

    @Override
    public UUID getPublicId() {
        return this.publicId;
    }

    @Override
    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }
}
