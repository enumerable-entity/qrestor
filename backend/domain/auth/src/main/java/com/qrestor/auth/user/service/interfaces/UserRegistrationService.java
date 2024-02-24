package com.qrestor.auth.user.service.interfaces;

import com.qrestor.auth.api.dto.RegistrationRequestDTO;

public interface UserRegistrationService {
    void registerNewUser(RegistrationRequestDTO registrationRequest);

    boolean confirmNewUserEmail(String emailConfirmationToken);


}
