package com.example.reservation.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseResponseStatus {

    /**
     * 200 : 요청 성공
     */
    SUCCESS(true,HttpStatus.OK.value(), "요청에 성공하였습니다."),

    /**
     * 400 : Request, Response 오류
     */

    USERS_EMPTY_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일을 입력해주세요."),

    POST_USERS_EXISTS_EMAIL(false,HttpStatus.BAD_REQUEST.value(),"이미 가입한 이메일 입니다."),

    USERS_INVALID_EMAIL(false,HttpStatus.BAD_REQUEST.value(),"이메일 정보를 다시 확인해주세요."),
    USERS_INVALID_PASSWORD(false,HttpStatus.BAD_REQUEST.value(),"비밀번호 정보를 다시 확인해주세요."),

    USERS_EMPTY_PASSWORD(false, HttpStatus.BAD_REQUEST.value(), "비밀번호를 입력해주세요."),

    USERS_EMPTY_USER_NAME(false, HttpStatus.BAD_REQUEST.value(), "사용자 이름을 입력해주세요."),

    USERS_EMPTY_EMAIL_CODE(false, HttpStatus.BAD_REQUEST.value(), "인증 코드 번호를 입력해주세요."),
    USERS_EMPTY_GREETING(false, HttpStatus.BAD_REQUEST.value(), "인사말을 입력해주세요."),

    CERTIF_INVALID_CODE_OR_EMAIL(false,HttpStatus.BAD_REQUEST.value(), "인증번호와 이메일을 다시한번 확인해주세요."),

    CERTIF_INVALID_CODE(false,HttpStatus.BAD_REQUEST.value(), "인증번호 유효시간이 지났습니다."),

    /**
     * 500 :  Database, Server 오류
     */

    UNEXPECTED_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
