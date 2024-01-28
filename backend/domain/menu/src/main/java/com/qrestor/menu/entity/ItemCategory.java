package com.qrestor.menu.entity;

import com.qrestor.commons.entity.PublicEntity;
import com.qrestor.menu.systemuser.enitity.SyncUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "item_categories", schema = "menu")
public class ItemCategory implements PublicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false, unique = true)
    private UUID publicId;

    @Column(name = "nls_key", nullable = false)
    private String nls_key;

    @Column(name = "order")
    private int order;

    @Column(name = "icon_code")
    private String iconCode;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = SyncUser.class)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    private SyncUser user;

    @OneToMany(mappedBy = "itemCategory", orphanRemoval = true)
    private Set<MenuItemEntity> menuItems = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ItemCategory that = (ItemCategory) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}