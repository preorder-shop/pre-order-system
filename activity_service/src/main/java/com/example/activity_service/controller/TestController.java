package com.example.activity_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
public class TestController {

    @GetMapping("/health-check")
    public String test(){
        return "success";
    }
}
