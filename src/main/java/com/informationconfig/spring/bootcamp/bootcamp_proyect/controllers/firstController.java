package com.informationconfig.spring.bootcamp.bootcamp_proyect.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class firstController {
    
    @GetMapping("/first-endpoint")
    public String firstEndpoint() {
        return "first-view";
    }
    
    @GetMapping("/second-endpoint")
    public String secondEndpoint() {
        return "second-view";
    }
    
    @GetMapping("/third-endpoint")
    public String thirdEndpoint() {
        return "third-view";
    }

    @GetMapping("/fourth-endpoint")
    public String fourthEndpoint() {
        return "fourth-view";
    }
    
    
}
