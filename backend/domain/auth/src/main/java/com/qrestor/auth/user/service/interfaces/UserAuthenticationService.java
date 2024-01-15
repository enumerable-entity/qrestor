package com.qrestor.auth.user.service.interfaces;

import com.qrestor.auth.api.dto.LoginRequestDTO;
import com.qrestor.auth.api.dto.LoginResponseDTO;
import com.qrestor.auth.api.dto.PasswordChangeDTO;
import com.qrestor.auth.api.dto.PasswordResetDTO;

public interface UserAuthenticationService {
    void sendResetPasswordEmail(PasswordResetDTO resetDTO);

    void resetPassword(PasswordResetDTO resetDTO);

    void changePassword(PasswordChangeDTO changeDTO);

    LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO);
}
