package kr.mashup.branding.ui;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.mashup.branding.domain.UnauthorizedException;
import kr.mashup.branding.domain.application.ApplicationAlreadySubmittedException;
import kr.mashup.branding.domain.application.PrivacyPolicyNotAgreedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ModelAttribute("applicantId")
    public Long resolveApplicantId(Principal principal) {
        if (principal == null) {
            return null;
        }
        if (principal instanceof PreAuthenticatedAuthenticationToken) {
            return (Long)((PreAuthenticatedAuthenticationToken)principal).getPrincipal();
        }
        return null;
    }

    @ExceptionHandler(PrivacyPolicyNotAgreedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handlePrivacyPolicyNotAgreedException(PrivacyPolicyNotAgreedException e) {
        log.info("handlePrivacyPolicyNotAgreedException", e);
        return ApiResponse.failure("PRIVACY_POLICY_NOT_AGREED", "개인정보 처리방침에 동의해야합니다");
    }

    @ExceptionHandler(ApplicationAlreadySubmittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleApplicationAlreadySubmittedException(ApplicationAlreadySubmittedException e) {
        log.info("ApplicationAlreadySubmittedException", e);
        return ApiResponse.failure(
            "APPLICATION_ALREADY_SUBMITTED",
            "이미 제출한 지원서는 다시 수정하거나 제출할 수 없습니다. "
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<?> handleUnauthorizedException(UnauthorizedException e) {
        log.info("handleUnauthorizedException", e);
        return ApiResponse.failure("UNAUTHORIZED", "인증이 필요한 요청입니다");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleException(Exception e) {
        log.error("handleException", e);
        return ApiResponse.failure("FAILURE", "실패");
    }
}
