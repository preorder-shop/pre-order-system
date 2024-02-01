package com.example.reservation.service;

import static com.example.reservation.response.BaseResponseStatus.*;


import com.example.reservation.common.CertificationNumber;
import com.example.reservation.common.exceptions.BaseException;
import com.example.reservation.dto.request.EmailCertificationReq;
import com.example.reservation.dto.request.PatchPasswordReq;
import com.example.reservation.dto.request.PatchUserInfoReq;
import com.example.reservation.dto.request.SignUpReq;
import com.example.reservation.dto.response.SignUpRes;
import com.example.reservation.dto.response.UserDto;
import com.example.reservation.entity.Certification;
import com.example.reservation.entity.Token;
import com.example.reservation.entity.User;
import com.example.reservation.jwt.JWTUtil;
import com.example.reservation.provider.EmailProvider;
import com.example.reservation.repository.CertificationRepository;
import com.example.reservation.repository.TokenRepository;
import com.example.reservation.repository.UserRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
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
    private final TokenRepository tokenRepository;

    private final S3Service s3Service;

    public SignUpRes createUser(SignUpReq signUpReq) {

        if (checkEmailDuplication(signUpReq.getEmail())) // 이미 가입된 이메일인지 확인
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
        if (diffMin > 10) {
            throw new BaseException(CERTIF_INVALID_CODE);
        }

        // 회원가입 진행

        User user = signUpReq.toEntity(encoder.encode(signUpReq.getPassword())); // 회원 entity 생성
        User save = userRepository.save(user);// db에 push

        return new SignUpRes(save.getId(), save.getName(), save.getEmail(), save.getGreeting());
    }


    public String emailCertificate(EmailCertificationReq emailCertificationReq) {

        String email = emailCertificationReq.getEmail();

        if (checkEmailDuplication(email)) { // 이미 가입된 이메일인지 확인
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        // 이미 해당 이메일로 인증코드를 보낸적이 있는지 확인
        Optional<Certification> byEmail = certificationRepository.findByEmail(email);
        boolean present = byEmail.isPresent();
        log.error("해당 이메일로 보낸적이 있는지={}", present);

        String certificationNumber = CertificationNumber.getCertificationNumber(); // 인증번호
        boolean isSuccessed = emailProvider.sendCertificationMail(email, certificationNumber);

        if (!isSuccessed) {
            throw new BaseException(FAIL_SEND_CODE);
        }

        if (present) {
            byEmail.get().changeCertificationNumber(certificationNumber);
        } else {
            Certification certification = Certification.builder()
                    .email(email)
                    .code(certificationNumber)
                    .build();

            certificationRepository.save(certification); // 성공시 저장.
        }

        return "해당 이메일로 인증코드를 전송했습니다.";

    }
    public UserDto getUserInfo(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(USERS_INVALID_EMAIL));

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .greeting(user.getGreeting())
                .profileImg(user.getProfile_img_url())
                .build();

    }

    public void deleteRefreshToken(String email) {

        tokenRepository.deleteByEmail(email);

    }

    public String patchUserInfo(String email,String name, String greeting  , String image_url) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(USERS_INVALID_EMAIL));


        if (name!=null && !name.isBlank()) {
            user.changeName(name);

        }
        if (greeting!=null && !greeting.isBlank()) {
            user.changeGreeting(greeting);

        }
        if(image_url!=null){

            String profile_img_url = user.getProfile_img_url();

            if(profile_img_url!=null && !profile_img_url.isBlank()){
                //todo : 삭제 안되는 원인 찾기
                s3Service.deleteImage(profile_img_url);

            }
            user.changeProfileImage(image_url);
        }


        return "유저 정보를 변경했습니다.";

    }

    public String patchPassword(PatchPasswordReq patchPasswordReq, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(USERS_INVALID_EMAIL));

        String newPassword = encoder.encode(patchPasswordReq.getPassword());

        user.changePassword(newPassword);

        deleteRefreshToken(email);

        return "비밀번호 변경을 완료했습니다.";

    }

    private boolean checkEmailDuplication(String email) {
        return userRepository.existsByEmail(email);
    }

    private User getUserByJWTToken(String token) {
        String email = jwtUtil.getEmail(token);
        String role = jwtUtil.getRole(token);

        return userRepository.findByEmailAndRole(email, role)
                .orElseThrow(() -> new BaseException(TOKEN_INVALID));

    }

    public String getUserRole(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(INVALID_LOGIN));

        return user.getRole();

    }

    public void accessTokenSave(String refreshToken, String userEmail, Date expiredDate) {

        Token token = Token.builder()
                .refreshToken(refreshToken)
                .email(userEmail)
                .expiredDate(expiredDate)
                .build();

        tokenRepository.save(token);

    }

}
