package com.qrestor.commons.common;

import java.security.Principal;
import java.util.UUID;

public record QrestorPrincipal(String name, UUID guid) implements Principal {
    @Override
    public String getName() {
        return name;
    }
}