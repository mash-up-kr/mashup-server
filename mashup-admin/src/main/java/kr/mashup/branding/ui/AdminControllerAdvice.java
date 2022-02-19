package kr.mashup.branding.ui;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.exception.ForbiddenException;
import kr.mashup.branding.domain.exception.InternalServerErrorException;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.exception.ServiceUnavailableException;
import kr.mashup.branding.domain.exception.UnauthorizedException;
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

    @ExceptionHandler(HttpMediaTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleHttpMediaTypeException(HttpMediaTypeException e) {
        log.info("handleHttpMediaTypeException: {}", e.getMessage(), e);
        return ApiResponse.failure("BAD_REQUEST", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleBadRequest(MethodArgumentTypeMismatchException e) {
        log.info("handleMethodArgumentTypeMismatchException: {}", e.getMessage(), e);
        return ApiResponse.failure("BAD_REQUEST", e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleBadRequestException(BadRequestException e) {
        log.info("handleBadRequestException: {}", e.getMessage(), e);
        return ApiResponse.failure(e.getResultCode());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<?> handleUnauthorizedException(UnauthorizedException e) {
        log.info("handleUnauthorizedException: {}", e.getMessage(), e);
        return ApiResponse.failure(e.getResultCode());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<?> handleForbiddenException(ForbiddenException e) {
        log.info("handleForbiddenException: {}", e.getMessage(), e);
        return ApiResponse.failure(e.getResultCode());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<?> handleNotFoundException(NotFoundException e) {
        log.info("handleNotFoundException: {}", e.getMessage(), e);
        return ApiResponse.failure(e.getResultCode());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleInternalServerErrorException(InternalServerErrorException e) {
        log.info("handleInternalServerErrorException: {}", e.getMessage(), e);
        return ApiResponse.failure(e.getResultCode());
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ApiResponse<?> handleServiceUnavailableException(ServiceUnavailableException e) {
        log.info("handleServiceUnavailableException: {}", e.getMessage(), e);
        return ApiResponse.failure(e.getResultCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleException(Exception e) {
        log.error("handleException", e);
        return ApiResponse.failure(ResultCode.INTERNAL_SERVER_ERROR);
    }
}
