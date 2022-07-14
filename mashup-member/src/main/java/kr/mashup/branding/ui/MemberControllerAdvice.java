package kr.mashup.branding.ui;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(HttpMediaTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleHttpMediaTypeException(HttpMediaTypeException e) {
        log.info("handleHttpMediaTypeException: {}", e.getMessage(), e);
        return ApiResponse.failure(ResultCode.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.info("handleMethodArgumentTypeMismatchException: {}", e.getMessage(), e);
        return ApiResponse.failure(ResultCode.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.info("handleHttpMessageNotReadableException: {}", e.getMessage(), e);
        return ApiResponse.failure(ResultCode.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK) // TODO: Illegal 은 클라 오류일수도, 서버 오류일수도 있어서 조금 애매
    public ApiResponse<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.info("handleIllegalArgumentException: {}", e.getMessage(), e);
        return ApiResponse.failure(ResultCode.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleBadRequestException(BadRequestException e) {
        log.info("handleBadRequestException: {}", e.getMessage(), e);
        return ApiResponse.failure(e.getResultCode());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleUnauthorizedException(UnauthorizedException e) {
        log.info("handleUnauthorizedException: {}", e.getMessage(), e);
        return ApiResponse.failure(e.getResultCode());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleForbiddenException(ForbiddenException e) {
        log.info("handleForbiddenException: {}", e.getMessage(), e);
        return ApiResponse.failure(e.getResultCode());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleServiceUnavailableException(ServiceUnavailableException e) {
        log.info("handleServiceUnavailableException: {}", e.getMessage(), e);
        return ApiResponse.failure(e.getResultCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleException(Exception e) {
        log.error("handleException: {}", e.getMessage(), e);
        return ApiResponse.failure(ResultCode.INTERNAL_SERVER_ERROR);
    }
}
