package kr.mashup.branding.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.mashup.branding.domain.application.PrivacyPolicyNotAgreedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(PrivacyPolicyNotAgreedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handlePrivacyPolicyNotAgreedException(PrivacyPolicyNotAgreedException e) {
        log.info("handlePrivacyPolicyNotAgreedException", e);
        return ApiResponse.failure("PRIVACY_POLICY_NOT_AGREED", "개인정보 처리방침에 동의해야합니다");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleException(Exception e) {
        log.error("handleException", e);
        return ApiResponse.failure("FAILURE", "실패");
    }
}
