package com.qrestor.menu.entity;

import com.qrestor.commons.entity.PublicEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "menu_items", schema = "menu")
public class MenuItemEntity implements PublicEntity {
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

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_category_id", nullable = false)
    private ItemCategory itemCategory;

    @Column(name = "ingredients")
    @ElementCollection
    private List<String> ingredients;

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
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

}