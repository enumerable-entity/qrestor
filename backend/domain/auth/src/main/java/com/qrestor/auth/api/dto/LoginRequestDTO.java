package com.qrestor.auth.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO (
        @NotNull @Size(min = 4, max = 20) String email,
        @NotNull @Size(min = 8, max = 30) String password) { }