package com.sonsenim.sonsenimbackend.api.controller.auth;

import com.sonsenim.sonsenimbackend.api.model.RegistrationBody;
import com.sonsenim.sonsenimbackend.exception.UserAlreadyExistsException;
import com.sonsenim.sonsenimbackend.model.LocalUser;
import com.sonsenim.sonsenimbackend.api.model.LoginBody;
import com.sonsenim.sonsenimbackend.api.model.LoginResponse;
import com.sonsenim.sonsenimbackend.model.dto.LocalUserDTO;
import com.sonsenim.sonsenimbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            userService.registerUser(registrationBody);

            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
        String jwt = userService.loginUser(loginBody);
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            LoginResponse response = new LoginResponse();
            response.setToken(jwt);
            return ResponseEntity.ok().body(response);
        }
    }
}
