package com.example.reservation.service;

import com.example.reservation.dto.CustomUserDetails;
import com.example.reservation.entity.User;
import com.example.reservation.entity.UserEntity;
import com.example.reservation.repository.UserEntityRepository;
import com.example.reservation.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService { // DB에 접근해서 값을 가져옴.

    private final UserRepository userRepository;




    @Override // 필터를 통해 자동으로 db에 있는 값을 가져와서 검증
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> byEmail = userRepository.findByEmail(email);// 나중에 바꿀때 username으로 객체를 찾기때문에
// 고유한 값으로 변경

        if(byEmail!=null)
            return new CustomUserDetails(byEmail.get());

        return null;
    }
}
