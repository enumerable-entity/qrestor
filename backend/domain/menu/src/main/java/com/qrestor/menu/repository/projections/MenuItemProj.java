package com.qrestor.menu.repository.projections;

import java.util.UUID;

public interface MenuItemProj {
    UUID getPublicId();
    void setPublicId(UUID publicId);
    String getTitle();
    void setTitle(String title);
    Long getPrice();
    void setPrice(Long price);
}
