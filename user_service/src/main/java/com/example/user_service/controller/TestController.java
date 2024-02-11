package com.example.user_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/users/test")
public class TestController {
    private final Environment env;

    @GetMapping("")
    public String test(){
        return "success";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to User Service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header){

        log.info(header);
        return header;

    }

    @GetMapping("/check")
    public String check(){
        return "Hi there. This is message from First Service";
    }

    @GetMapping("/health_check")
    public String status(){
        return String.format("It's Working in User Service on PORT %s",
                env.getProperty("local.server.port"));
    }

}
