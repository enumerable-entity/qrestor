package com.qrestor.menu.entity;

import com.qrestor.commons.entity.OwnedEntity;
import com.qrestor.commons.entity.PublicEntity;
import com.qrestor.menu.systemuser.enitity.SyncUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@Entity
@FieldNameConstants
@Table(name = "menu_items", schema = "menu", indexes = {
        @Index(name = "menu_items_public_id_idx", columnList = "public_id", unique = true)
})
public class MenuItemEntity extends OwnedEntity implements PublicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false, unique = true)
    private UUID publicId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @ManyToOne(optional = false, targetEntity = ItemCategoryEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_category_id", nullable = false)
    private ItemCategoryEntity itemCategory;

    @Column(name = "user_id", updatable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = SyncUser.class)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    private SyncUser user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "menu_items_to_ingredients",
            joinColumns = @JoinColumn(name = "menu_item_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<IngredientEntity> ingredients = new LinkedHashSet<>();

    @ManyToOne(optional = false, fetch = FetchType.LAZY, targetEntity = MenuEntity.class)
    @JoinColumn(name = "menu_id")
    private MenuEntity menu;

    @Column(name = "is_vegetarian")
    private Boolean isVegetarian;

    @Column(name = "is_vegan")
    private Boolean isVegan;

    @Column(name = "is_gluten_free")
    private Boolean isGlutenFree;

    @Column(name = "is_spicy")
    private Boolean isSpicy;

    @Column(name = "is_halal")
    private Boolean isHalal;

    @Column(name = "is_kosher")
    private Boolean isKosher;

    @Column(name = "is_nuts")
    private Boolean isNuts;

    @Column(name = "is_dairy")
    private Boolean isDairy;

    @Column(name = "is_eggs")
    private Boolean isEggs;

    @Column(name = "is_fish")
    private Boolean isFish;

    @Column(name = "is_shellfish")
    private Boolean isShellfish;

    @Column(name = "is_soy")
    private Boolean isSoy;

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<MenuItemOptionEntity> menuItemOptions = new ArrayList<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ?
                hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ?
                hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        MenuItemEntity that = (MenuItemEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ?
                hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}