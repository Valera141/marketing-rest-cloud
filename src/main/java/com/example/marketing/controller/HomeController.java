package com.example.Marketing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Redirige a la ruta exacta que definiste en application.properties
        return "redirect:/doc/swagger-ui.html"; 
    }
}
