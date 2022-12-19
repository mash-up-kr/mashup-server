package kr.mashup.branding.ui

import com.fasterxml.jackson.annotation.JsonInclude
import kr.mashup.branding.domain.ResultCode
import org.springframework.data.domain.Page

data class ApiResponse<T>(
    private val code: ResultCode,
    private val message: String,
    private val data: T,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private val page: PageResponse?
) {

    companion object {
        @JvmStatic
        fun <T> success(): ApiResponse<T?> =
            ApiResponse(
                ResultCode.SUCCESS,
                ResultCode.SUCCESS.message,
                null,
                null
            )

        @JvmStatic
        fun <T> success(data: T): ApiResponse<T> =
            ApiResponse(
                ResultCode.SUCCESS,
                ResultCode.SUCCESS.message,
                data,
                null
            )

        @JvmStatic
        fun <T> success(data: List<T>): ApiResponse<List<T>> =
            ApiResponse(
                ResultCode.SUCCESS,
                ResultCode.SUCCESS.message,
                data,
                null
            )

        @JvmStatic
        fun <T> success(data: Page<T>): ApiResponse<List<T>> =
            ApiResponse(
                ResultCode.SUCCESS,
                ResultCode.SUCCESS.message,
                data.content,
                PageResponse.from(data)
            )

        @JvmStatic
        fun <T> failure(resultCode: ResultCode): ApiResponse<T?> =
            ApiResponse(
                resultCode,
                resultCode.message,
                null,
                null
            )

        @JvmStatic
        fun <T> failure(
            resultCode: ResultCode,
            message: String?
        ): ApiResponse<T?> =
            ApiResponse(
                resultCode,
                message ?: "Not exist message",
                null,
                null
            )
    }
}

data class PageResponse(
    private val number: Int,
    private val size: Int,
    private val totalCount: Int
) {
    companion object {
        fun from(page: Page<*>): PageResponse =
            PageResponse(
                page.number,
                page.size,
                page.totalElements.toInt()
            )
    }
}

