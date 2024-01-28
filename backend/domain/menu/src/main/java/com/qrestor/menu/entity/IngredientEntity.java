package com.qrestor.menu.entity;

import com.qrestor.commons.entity.OwnedEntity;
import com.qrestor.commons.entity.PublicEntity;
import com.qrestor.menu.systemuser.enitity.SyncUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "menu_item_ingredients")
public class IngredientEntity extends OwnedEntity implements PublicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "public_id", nullable = false, updatable = false, unique = true)
    private UUID publicId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;

    @Column(name = "user_id", updatable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = SyncUser.class)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    private SyncUser user;

}