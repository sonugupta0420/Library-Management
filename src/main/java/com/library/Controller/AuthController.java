package com.library.Controller;

import com.library.DTO.LoginResponseDTO;
import com.library.DTO.RegisterRequestDTO;
import com.library.Entity.User;
import com.library.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/registeruser")
    public ResponseEntity<User> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        // Implementation for registering a new user by admin
        return ResponseEntity.ok(authenticationService.registerUser(registerRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO>  loginUser(@RequestBody LoginResponseDTO loginRequestDTO) {
        // Implementation for admin login
        return ResponseEntity.ok(authenticationService.loginUser(loginRequestDTO));
    }
}
