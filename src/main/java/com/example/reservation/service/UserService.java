package com.example.reservation.service;

import static com.example.reservation.common.response.BaseResponseStatus.*;


import com.example.reservation.common.CertificationNumber;
import com.example.reservation.common.exceptions.BaseException;
import com.example.reservation.dto.CustomUserDetails;
import com.example.reservation.dto.EmailCertificationReq;
import com.example.reservation.dto.LoginReq;
import com.example.reservation.dto.PatchPasswordReq;
import com.example.reservation.dto.PatchUserInfoReq;
import com.example.reservation.dto.SignUpReq;
import com.example.reservation.dto.SignUpRes;
import com.example.reservation.entity.Certification;
import com.example.reservation.entity.User;
import com.example.reservation.jwt.JWTUtil;
import com.example.reservation.provider.EmailProvider;
import com.example.reservation.repository.CertificationRepository;
import com.example.reservation.repository.UserRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    private final AuthenticationManager authenticationManager;

    public SignUpRes createUser(SignUpReq signUpReq){

        if(checkEmailDuplication(signUpReq.getEmail())) // 이미 가입된 이메일인지 확인
        {
           throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        Certification certification = certificationRepository.findByEmailAndCode(signUpReq.getEmail(),
                        signUpReq.getCode())
                .orElseThrow(() -> new BaseException(CERTIF_INVALID_CODE_OR_EMAIL));


        // todo : 추후 Redis Db 로 변경 예정
        // 인증 코드 유효시간 체크
        LocalDateTime certificateTime = certification.getUpdated_at();
        LocalDateTime signupTime = LocalDateTime.now();
        Duration diff = Duration.between(certificateTime, signupTime);
        long diffMin = diff.toMinutes();
        if(diffMin>10){
            throw new BaseException(CERTIF_INVALID_CODE);
        }


        // 회원가입 진행

        User user = signUpReq.toEntity(encoder.encode(signUpReq.getPassword())); // 회원 entity 생성
        User save = userRepository.save(user);// db에 push

        return new SignUpRes(save.getId(),save.getName(), save.getEmail(), save.getGreeting());
    }
    public String login(LoginReq loginReq){

        User user = userRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(() -> new BaseException(USERS_INVALID_EMAIL));


        boolean matches = encoder.matches(loginReq.getPassword(), user.getPassword());
        if(!matches) throw new BaseException(USERS_INVALID_PASSWORD);


        // token 발급
//        Authentication authenticate;
//
//        try{
//             authenticate = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getRole())
//            );
//        }catch (Exception e){
//            throw new BaseException();
//        }
//
//        // 헤더에
//        CustomUserDetails customUserDetails = (CustomUserDetails) authenticate.getPrincipal();
//
//        String username = customUserDetails.getUsername();
//
//        Collection<? extends GrantedAuthority> authorities = authenticate.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority auth = iterator.next();
//
//        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(user.getName(), user.getRole(), 60*60*1000L);

        // JWT 는 header 에 담아서 return
        return token;


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

        //  jwt 꺼내서 해당 사용자 객체 가져옴.
        Optional<User> user = getUserByJWTToken(token);

        if(user.isEmpty()){
            return "해당하는 정보의 유저 없음";
        }


        if(patchUserInfoReq.getName()!=null){
            user.get().changeName(patchUserInfoReq.getName());

        }
        if(patchUserInfoReq.getGreeting()!=null){
            user.get().changeGreeting(patchUserInfoReq.getGreeting());

        }

        return "유저 정보를 변경했습니다.";

    }
    public String patchPassword(PatchPasswordReq patchPasswordReq,String token){
        Optional<User> user = getUserByJWTToken(token);

        String newPassword = encoder.encode(patchPasswordReq.getPassword());

        if(user.isEmpty()){
            return "해당 유저 정보 없음";
        }

        user.get().changePassword(newPassword);
        return "비밀번호 변경을 완료했습니다.";



    }

    private boolean checkEmailDuplication(String email){
        return userRepository.existsByEmail(email);
    }

    private Optional<User> getUserByJWTToken(String token){
        String email =jwtUtil.getEmail(token);
        String role = jwtUtil.getRole(token);

       return userRepository.findByEmailAndRole(email, role);

    }


}
