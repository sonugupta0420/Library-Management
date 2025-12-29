package com.library.Service;

import com.library.DTO.LoginRequestDTO;
import com.library.DTO.LoginResponseDTO;
import com.library.DTO.RegisterRequestDTO;
import com.library.Entity.User;
import com.library.Jwt.JwtService;
import com.library.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User registerUser(RegisterRequestDTO registerRequestDTO) {

        if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getUsername(), loginRequestDTO.getPassword()
        ));

        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // In a real application, you would generate a JWT or session token here
        String token = jwtService.generateToken(user);

        return LoginResponseDTO.builder()
                .token(token)
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }

    public User registerAdminUser(RegisterRequestDTO registerRequestDTO) {

        if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);
        return userRepository.save(user);
    }
}
