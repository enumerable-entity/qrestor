package com.qrestor.menu.entity;


import com.qrestor.menu.api.dto.list.MenuListDTO;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "preparedMenus")
public class AggregatedMenuDocumentEntity {

    /**
     * The unique identifier of the restaurant
     */
    @Id
    private UUID id;

    private List<MenuListDTO> menuAggregate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AggregatedMenuDocumentEntity that = (AggregatedMenuDocumentEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
