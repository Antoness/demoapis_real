package com.example.demoapis.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/welcomes")
public class WelcomeApi {
    
    @GetMapping
    public String welcom(){
        return "Hello spring boot ";
    }
}
