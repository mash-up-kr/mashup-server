package kr.mashup.branding.ui

import kr.mashup.branding.domain.ResultCode.BAD_REQUEST
import kr.mashup.branding.domain.ResultCode.INTERNAL_SERVER_ERROR
import kr.mashup.branding.domain.exception.*
import kr.mashup.branding.util.LoggerDelegate
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.web.HttpMediaTypeException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.security.Principal

@RestControllerAdvice
class ApiControllerAdvice {

    private val log by LoggerDelegate()

    @ModelAttribute("applicantId")
    fun resolveApplicantId(principal: Principal?): Long? {
        if (principal == null) {
            return null
        }
        return if (principal is PreAuthenticatedAuthenticationToken) {
            principal.principal as Long
        } else null
    }

    @ExceptionHandler(HttpMediaTypeException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMediaTypeException(e: HttpMediaTypeException): ApiResponse<*> {
        log.info("handleHttpMediaTypeException: ${e.message}", e)
        return ApiResponse.failure<Any>(BAD_REQUEST, e.message)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(e: MethodArgumentTypeMismatchException): ApiResponse<*> {
        log.info("handleMethodArgumentTypeMismatchException: ${e.message}", e)
        return ApiResponse.failure<Any>(BAD_REQUEST, e.message)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ApiResponse<*> {
        log.info("handleHttpMessageNotReadableException: ${e.message}", e)
        return ApiResponse.failure<Any>(BAD_REQUEST, e.message)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ApiResponse<*> {
        log.info("handleIllegalArgumentException: ${e.message}", e)
        return ApiResponse.failure<Any>(BAD_REQUEST, e.message)
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(e: BadRequestException): ApiResponse<*> {
        log.info("handleBadRequestException: ${e.message}", e)
        return ApiResponse.failure<Any>(e.resultCode)
    }

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(e: UnauthorizedException): ApiResponse<*> {
        log.info("handleUnauthorizedException: ${e.message}", e)
        return ApiResponse.failure<Any>(e.resultCode)
    }

    @ExceptionHandler(ForbiddenException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleForbiddenException(e: ForbiddenException): ApiResponse<*> {
        log.info("handleForbiddenException: ${e.message}", e)
        return ApiResponse.failure<Any>(e.resultCode)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(e: NotFoundException): ApiResponse<*> {
        log.info("handleNotFoundException: ${e.message}", e)
        return ApiResponse.failure<Any>(e.resultCode)
    }

    @ExceptionHandler(InternalServerErrorException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleInternalServerErrorException(e: InternalServerErrorException): ApiResponse<*> {
        log.info("handleInternalServerErrorException: ${e.message}", e)
        return ApiResponse.failure<Any>(e.resultCode)
    }

    @ExceptionHandler(ServiceUnavailableException::class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    fun handleServiceUnavailableException(e: ServiceUnavailableException): ApiResponse<*> {
        log.info("handleServiceUnavailableException: ${e.message}", e)
        return ApiResponse.failure<Any>(e.resultCode)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: Exception): ApiResponse<*> {
        log.error("handleException: ${e.message}", e)
        return ApiResponse.failure<Any>(INTERNAL_SERVER_ERROR)
    }
}