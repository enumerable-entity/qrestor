package com.qrestor.auth.api.dto;

public record PasswordResetDTO(String email, String token, String password) {
}
