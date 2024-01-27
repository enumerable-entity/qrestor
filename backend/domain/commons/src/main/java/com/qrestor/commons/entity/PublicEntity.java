package com.qrestor.commons.entity;

import java.util.UUID;

public interface PublicEntity {

    void setPublicId(UUID publicId);
    UUID getPublicId();
}
