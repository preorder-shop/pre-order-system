package com.example.reservation.service;

import com.example.reservation.common.CertificationNumber;
import com.example.reservation.dto.EmailCertificationReq;
import com.example.reservation.dto.PatchUserInfoReq;
import com.example.reservation.dto.SignUpReq;
import com.example.reservation.entity.Certification;
import com.example.reservation.entity.User;
import com.example.reservation.jwt.JWTUtil;
import com.example.reservation.provider.EmailProvider;
import com.example.reservation.repository.CertificationRepository;
import com.example.reservation.repository.UserRepository;
import java.time.Duration;
import java.time.LocalDateTime;
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

    private final JWTUtil jwtUtil;

    public String signup(SignUpReq signUpReq){

        // todo : 예외처리 -> 이메일 중복확인
        if(checkEmailDuplication(signUpReq.getEmail())) // 이미 가입된 이메일인지 확인
        {
            return "이미 존재하는 이메일입니다.";
        }

        Optional<Certification> byEmailAndCode = certificationRepository.findByEmailAndCode(signUpReq.getEmail(),
                signUpReq.getCode());
        if(byEmailAndCode.isEmpty()){
            return "이메일 인증을 진행해 주세요.";
        }

        // 인증 코드 유효시간 체크
        Certification certification = byEmailAndCode.get();
        LocalDateTime certificateTime = certification.getUpdated_at();
        LocalDateTime signupTime = LocalDateTime.now();
        Duration diff = Duration.between(certificateTime, signupTime);
        long diffMin = diff.toMinutes();
        if(diffMin>10){
            return "인증 코드 유효시간이 지났습니다. 다시 이메일 인증을 해주세요.";
        }

        // 회원가입 진행

        String password = signUpReq.getPassword();
        String encodePassword = encoder.encode(password); // 비번 암호화
        User user = signUpReq.toEntity(encodePassword); // entity 생성
        userRepository.save(user); // db에 push

        return "회원가입이 완료되었습니다.";
    }

    public String emailCertificate( EmailCertificationReq emailCertificationReq){

        String email = emailCertificationReq.getEmail();

        if(checkEmailDuplication(email)){ // 이미 가입된 이메일인지 확인
            return "이미 존재하는 이메일입니다.";
        }
        
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
                    .code(certificationNumber)
                    .build();

            certificationRepository.save(certification); // 성공시 저장.
        }

        return "이메일 인증코드 전송 성공";

    }

    public String patchUserInfo(PatchUserInfoReq patchUserInfoReq,String token){

        // todo : jwt꺼내서 해당 사용자 객체 가져옴.
        String email =jwtUtil.getEmail(token);
        String role = jwtUtil.getRole(token);

        Optional<User> user = userRepository.findByEmailAndRole(email, role);

        if(user.isEmpty()){
            return "해당하는 정보의 유저 없음";
        }


        if(patchUserInfoReq.getName()!=null){
            user.get().changeName(patchUserInfoReq.getName());

        }
        if(patchUserInfoReq.getGreeting()==null){
            user.get().changeGreeting(patchUserInfoReq.getGreeting());

        }

        return "유저 정보를 변경했습니다.";

    }

    private boolean checkEmailDuplication(String email){
        return userRepository.existsByEmail(email);
    }


}
