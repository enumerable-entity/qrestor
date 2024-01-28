package com.qrestor.menu.entity;

import com.qrestor.commons.entity.PublicEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "menu_item_categories", schema = "menu", indexes = {
        @Index(name = "menu_item_categories_public_id_idx", columnList = "public_id", unique = true)
})
public class ItemCategoryEntity implements PublicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false, unique = true)
    private UUID publicId;

    @Column(name = "nls_key", nullable = false, unique = true, length = 50)
    private String nlsKey;

    @Column(name = "display_order")
    private int displayOrder;

    @Column(name = "icon_code", length = 20)
    private String iconCode;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ?
                hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ?
                hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ItemCategoryEntity that = (ItemCategoryEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ?
                hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}