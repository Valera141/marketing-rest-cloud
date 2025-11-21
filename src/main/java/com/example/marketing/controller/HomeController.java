package com.example.Marketing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Cuando alguien entre a la raíz "/", lo mandamos a la documentación
    @GetMapping("/")
    public String home() {
        return "redirect:/doc/swagger-ui/index.html";
    }
}
