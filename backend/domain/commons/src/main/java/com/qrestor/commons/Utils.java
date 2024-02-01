package com.qrestor.commons;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class Utils {
    public UUID generatePublicId() {
        return UUID.randomUUID();
    }
}
