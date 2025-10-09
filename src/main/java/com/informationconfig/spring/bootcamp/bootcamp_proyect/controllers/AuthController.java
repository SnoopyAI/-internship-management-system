package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.informationconfig.spring.bootcamp.bootcamp_proyect.dto.LoginDTO;
import com.informationconfig.spring.bootcamp.bootcamp_proyect.security.CustomUserDetailsService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            // Buscar usuario por email
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
            
            // Verificar contraseña
            if (passwordEncoder.matches(loginDTO.getPassword(), userDetails.getPassword())) {
                // Login exitoso
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Login exitoso");
                response.put("email", userDetails.getUsername());
                response.put("roles", userDetails.getAuthorities());
                
                return ResponseEntity.ok(response);
            } else {
                // Contraseña incorrecta
                return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "Credenciales incorrectas"
                ));
            }
        } catch (UsernameNotFoundException e) {
            // Usuario no encontrado
            return ResponseEntity.status(401).body(Map.of(
                "success", false,
                "message", "Credenciales incorrectas"
            ));
        }
    }
}
