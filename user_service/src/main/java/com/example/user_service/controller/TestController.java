package com.example.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/test")
public class TestController {

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to User Service";
    }
}
