package kr.mashup.branding.ui;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.mashup.branding.domain.schedule.RecruitmentScheduleDuplicatedException;
import kr.mashup.branding.domain.schedule.RecruitmentScheduleNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class AdminControllerAdvice {

    @ExceptionHandler(RecruitmentScheduleDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleRecruitmentScheduleDuplicatedException(
        RecruitmentScheduleDuplicatedException e
    ) {
        return ApiResponse.failure("RECRUITMENT_SCHEDULE_NAME_DUPLICATED", e.getMessage());
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
