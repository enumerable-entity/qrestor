package com.qrestor.menu.entity;

import com.qrestor.commons.entity.OwnedEntity;
import com.qrestor.commons.entity.PublicEntity;
import com.qrestor.menu.systemuser.enitity.SyncUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@FieldNameConstants
@NamedEntityGraph(name = "MenuItemOptionEntity.menuItemOptionPositions",
        attributeNodes = {
        @NamedAttributeNode("menuItemOptionPositions"), @NamedAttributeNode("menuItem")})
@Table(name = "menu_items_options", schema = "menu", indexes = {
        @Index(name = "menu_items_options_public_id_idx", columnList = "public_id", unique = true)
})
public class MenuItemOptionEntity extends OwnedEntity implements PublicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false, unique = true)
    private UUID publicId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "is_multi_select", nullable = false)
    private boolean isMultiSelect;

    @Column(name = "is_required", nullable = false)
    private boolean isRequired;

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItemEntity menuItem;

    @OneToMany(mappedBy = "menuItemOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItemOptionPositionEntity> menuItemOptionPositions = new ArrayList<>();

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;

    @Column(name = "user_id", updatable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = SyncUser.class)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    private SyncUser user;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ?
                hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ?
                hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        MenuItemOptionEntity that = (MenuItemOptionEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ?
                hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}