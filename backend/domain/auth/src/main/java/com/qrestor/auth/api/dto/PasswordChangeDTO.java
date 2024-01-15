package com.qrestor.auth.api.dto;

public record PasswordChangeDTO(String oldPassword, String newPassword) {
}
