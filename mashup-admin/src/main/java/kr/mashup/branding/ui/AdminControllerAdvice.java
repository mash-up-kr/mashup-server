package kr.mashup.branding.ui;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.mashup.branding.domain.UnauthorizedException;
import kr.mashup.branding.domain.adminmember.AdminMember;
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
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            if (((UsernamePasswordAuthenticationToken)principal).getPrincipal() instanceof User) {
                return Long.valueOf(
                    ((User)(((UsernamePasswordAuthenticationToken)principal).getPrincipal())).getUsername());
            }
            AdminMember adminMember = (AdminMember)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
            return adminMember.getAdminMemberId();
        }
        return null;
    }

    @ExceptionHandler(RecruitmentScheduleDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleRecruitmentScheduleDuplicatedException(
        RecruitmentScheduleDuplicatedException e
    ) {
        return ApiResponse.failure("RECRUITMENT_SCHEDULE_NAME_DUPLICATED", e.getMessage());
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
