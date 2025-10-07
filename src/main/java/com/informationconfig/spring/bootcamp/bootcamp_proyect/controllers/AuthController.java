package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.LoginDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(), 
                loginDTO.getPassword()
            )
        );

        // Usar la variable authentication para devolver información del usuario
        return ResponseEntity.ok(Map.of(
            "message", "Login successful",
            "email", authentication.getName()
        ));
    }
}