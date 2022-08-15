package kr.mashup.branding.ui;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(HttpMediaTypeException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleHttpMediaTypeException(HttpMediaTypeException e) {
        log.info("handleHttpMediaTypeException: {}", e.getMessage(), e);
        return ApiResponse.failure(ResultCode.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.info("handleMethodArgumentTypeMismatchException: {}", e.getMessage(), e);
        return ApiResponse.failure(ResultCode.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("handleMethodArgumentNotValidException: {}", e.getMessage(), e);
        return ApiResponse.failure(ResultCode.BAD_REQUEST, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.info("handleHttpMessageNotReadableException: {}", e.getMessage(), e);
        return ApiResponse.failure(ResultCode.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleInternalServerErrorException(InternalServerErrorException e) {
        log.info("handleInternalServerErrorException: {}", e.getMessage(), e);
        return ApiResponse.failure(e.getResultCode());
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleServiceUnavailableException(ServiceUnavailableException e) {
        log.info("handleServiceUnavailableException: {}", e.getMessage(), e);
        return ApiResponse.failure(e.getResultCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleException(Exception e) {
        log.error("handleException: {}", e.getMessage(), e);
        return ApiResponse.failure(ResultCode.INTERNAL_SERVER_ERROR);
    }
}
