package com.example.reservation.service;

import com.example.reservation.dto.CustomUserDetails;
import com.example.reservation.entity.UserEntity;
import com.example.reservation.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserEntityRepository userRepository;

    public CustomUserDetailsService(UserEntityRepository userRepository) {

        this.userRepository = userRepository;
    }


    @Override // 필터를 통해 자동으로 db에 있는 값을 가져와서 검증
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userData = userRepository.findByUsername(username); // 나중에 바꿀때 username으로 객체를 찾기때문에
        // 고유한 값으로 변경

        if(userData!=null)
            return new CustomUserDetails(userData);

        return null;
    }
}
