package com.example.newsfeed_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feeds/test")
public class TestController {

    @GetMapping("")
    public String test(){
        return "success";
    }
}
