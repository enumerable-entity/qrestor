package com.qrestor.auth.api.controller;

import com.qrestor.auth.api.dto.LoginRequestDTO;
import com.qrestor.auth.api.dto.LoginResponseDTO;
import com.qrestor.auth.api.dto.PasswordChangeDTO;
import com.qrestor.auth.api.dto.PasswordResetDTO;
import com.qrestor.auth.user.service.interfaces.UserAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.qrestor.auth.api.RestEndpoints.*;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class UserAuthenticationController {

    private final UserAuthenticationService userAuthenticationServiceImpl;

    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO tokens = userAuthenticationServiceImpl.authenticate(loginRequestDTO);
        return ResponseEntity.ok().body(tokens);
    }

    @PostMapping(FORGOT_PASSWORD)
    public ResponseEntity<Void> forgotPasswordRequest(@RequestBody PasswordResetDTO resetDTO) {
        userAuthenticationServiceImpl.sendResetPasswordEmail(resetDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping(FORGOT_PASSWORD_RESET)
    public ResponseEntity<Void> resetPassword(@RequestBody PasswordResetDTO resetDTO) {
        userAuthenticationServiceImpl.resetPassword(resetDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping(PASSWORD_CHANGE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordChangeDTO changeDTO) {
        userAuthenticationServiceImpl.changePassword(changeDTO);
        return ResponseEntity.ok().build();
    }

}
