package com.example.reservation.service;

import com.example.reservation.common.CertificationNumber;
import com.example.reservation.dto.EmailCertificationReq;
import com.example.reservation.dto.SignUpReq;
import com.example.reservation.entity.Certification;
import com.example.reservation.entity.User;
import com.example.reservation.provider.EmailProvider;
import com.example.reservation.repository.CertificationRepository;
import com.example.reservation.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder encoder;
    private final EmailProvider emailProvider;
    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;

    public void signup(SignUpReq signUpReq){

        // todo : 예외처리 -> 비밀번호 중복확인

        String password = signUpReq.getPassword();
        String encodePassword = encoder.encode(password); // 비번 암호화
        User user = signUpReq.toEntity(encodePassword); // entity 생성
        userRepository.save(user); // db에 push

    }

    public String emailCertificate( EmailCertificationReq emailCertificationReq){

        String email = emailCertificationReq.getEmail();
        
        // 이미 해당 이메일로 인증코드를 보낸적이 있는지 확인
        Optional<Certification> byEmail = certificationRepository.findByEmail(email);
        boolean present = byEmail.isPresent();
        log.error("해당 이메일로 보낸적이 있는지={}",present);

        String certificationNumber = CertificationNumber.getCertificationNumber(); // 인증번호
        boolean isSuccessed = emailProvider.sendCertificationMail(email, certificationNumber);

        if(!isSuccessed) return "이메일에 인증코드 전송 실패";

        if(present){
            byEmail.get().changeCertificationNumber(certificationNumber);
        }else{
            Certification certification = Certification.builder()
                    .email(email)
                    .certificationNumber(certificationNumber)
                    .build();

            certificationRepository.save(certification); // 성공시 저장.
        }

        return "이메일 인증코드 전송 성공";

    }


}
