package kr.mashup.branding.ui;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.mashup.branding.domain.UnauthorizedException;
import kr.mashup.branding.domain.notification.NotificationRequestInvalidException;
import kr.mashup.branding.domain.schedule.RecruitmentScheduleDuplicatedException;
import kr.mashup.branding.domain.schedule.RecruitmentScheduleNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class AdminControllerAdvice {

    @ModelAttribute("adminMemberId")
    public Long resolveAdminMemberId(Principal principal) {
        log.debug("principal : {}", principal);
        if (principal == null) {
            return null;
        }
        if (principal instanceof PreAuthenticatedAuthenticationToken) {
            return (Long)((PreAuthenticatedAuthenticationToken)principal).getPrincipal();
        }
        return null;
    }

    @ExceptionHandler(RecruitmentScheduleDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleRecruitmentScheduleDuplicatedException(
        RecruitmentScheduleDuplicatedException e
    ) {
        log.info("handleRecruitmentScheduleDuplicatedException", e);
        return ApiResponse.failure("RECRUITMENT_SCHEDULE_NAME_DUPLICATED", e.getMessage());
    }

    @ExceptionHandler(NotificationRequestInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleNotificationInvalidException(NotificationRequestInvalidException e) {
        log.info("handleNotificationInvalidException", e);
        return ApiResponse.failure("NOTIFICATION_REQUEST_INVALID", e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<?> handleUnauthorizedException(UnauthorizedException e) {
        log.info("handleUnauthorizedException", e);
        return ApiResponse.failure("UNAUTHORIZED", "인증이 필요한 요청입니다");
    }

    @ExceptionHandler(RecruitmentScheduleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<?> handleRecruitmentScheduleNotFoundException(
        RecruitmentScheduleNotFoundException e
    ) {
        return ApiResponse.failure("RECRUITMENT_SCHEDULE_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleException(Exception e) {
        log.error("handleException", e);
        return ApiResponse.failure("FAILURE", "실패");
    }
}
