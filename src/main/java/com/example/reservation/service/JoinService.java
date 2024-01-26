package com.example.reservation.service;

import com.example.reservation.dto.JoinDTO;
import com.example.reservation.entity.UserEntity;
import com.example.reservation.repository.UserEntityRepository;
import com.example.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class JoinService {
    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public void joinProcess(JoinDTO joinDTO){
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        boolean isExist = userEntityRepository.existsByUsername(username);

        if(isExist){
            return;
        }


        UserEntity data =new UserEntity();
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");

        userEntityRepository.save(data);



    }
}
