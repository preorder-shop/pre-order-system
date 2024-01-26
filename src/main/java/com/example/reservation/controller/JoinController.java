package com.example.reservation.controller;

import com.example.reservation.dto.JoinDTO;
import com.example.reservation.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO){

//        log.error("username={}",joinDTO.getUsername());
//        log.error("password={}",joinDTO.getPassword());

        joinService.joinProcess(joinDTO);


        return "회원가입완료";
    }
}
