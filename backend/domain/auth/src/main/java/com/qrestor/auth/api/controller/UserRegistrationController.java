package com.qrestor.auth.api.controller;

import com.qrestor.auth.api.dto.RegistrationRequestDTO;
import com.qrestor.auth.user.service.interfaces.UserRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.qrestor.auth.api.RestEndpoints.VERIFY_EMAIL;
import static com.qrestor.auth.api.RestEndpoints.REGISTRATION;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(REGISTRATION)
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @PostMapping
    public ResponseEntity<Void> registerUser(@Valid @RequestBody RegistrationRequestDTO registrationRequest) {
        userRegistrationService.registerNewUser(registrationRequest);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping(VERIFY_EMAIL + "{emailConfirmationToken}")
    public ResponseEntity<Void> confirmEmail(@PathVariable String emailConfirmationToken) {
        userRegistrationService.confirmNewUserEmail(emailConfirmationToken);
        return ResponseEntity.ok().build();
    }

}
