package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    // Solo necesitas ESTA clase, nada más
    
    @PostMapping("/string")
    public String testString(@RequestBody String raw) {
        System.out.println("✅ STRING RECIBIDO: " + raw);
        return "Received: " + raw;
    }

    @PostMapping("/map") 
    public Map<String, Object> testMap(@RequestBody Map<String, Object> data) {
        System.out.println("✅ MAP RECIBIDO: " + data);
        return data;
    }

    @PostMapping("/simple")
    public String testSimple(@RequestBody SimpleObject obj) {
        System.out.println("✅ OBJETO RECIBIDO: " + obj.name + ", " + obj.email);
        return "OK: " + obj.name;
    }

    // Clase interna - NO es una entidad JPA, solo un POJO simple
    public static class SimpleObject {
        public String name;
        public String email;
        
        // Constructor por defecto
        public SimpleObject() {}
        
        // Getters y setters (opcionales para esta prueba)
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}
