package com.qrestor.auth.authority;

import com.qrestor.auth.user.entity.SystemUserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "ROLE", schema = "AUTH")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemRoleEntity implements GrantedAuthority {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "authority", nullable = false, length = 20)
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Collection<SystemUserEntity> systemUserEntities;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SystemRoleEntity that = (SystemRoleEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}