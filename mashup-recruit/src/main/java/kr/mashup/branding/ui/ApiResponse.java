package kr.mashup.branding.ui;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;

import kr.mashup.branding.domain.ResultCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiResponse<T> {
    private final ResultCode code;
    private final String message;
    private final T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final PageResponse page;

    private ApiResponse(ResultCode code, String message, T data, PageResponse page) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.page = page;
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(
            ResultCode.SUCCESS,
            ResultCode.SUCCESS.getMessage(),
            null,
            null
        );
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
            ResultCode.SUCCESS,
            ResultCode.SUCCESS.getMessage(),
            data,
            null
        );
    }

    public static <T> ApiResponse<List<T>> success(List<T> data) {
        return new ApiResponse<>(
            ResultCode.SUCCESS,
            ResultCode.SUCCESS.getMessage(),
            data,
            null
        );
    }

    public static <T> ApiResponse<List<T>> success(Page<T> data) {
        return new ApiResponse<>(
            ResultCode.SUCCESS,
            ResultCode.SUCCESS.getMessage(),
            data.getContent(),
            PageResponse.from(data)
        );
    }

    public static <T> ApiResponse<T> failure(ResultCode resultCode) {
        return new ApiResponse<T>(
            resultCode,
            resultCode.getMessage(),
            null,
            null
        );
    }

    public static <T> ApiResponse<T> failure(ResultCode resultCode, String message) {
        return new ApiResponse<>(
            resultCode,
            message,
            null,
            null
        );
    }

    @Getter
    @ToString
    public static class PageResponse {
        private final int number;
        private final int size;
        private final int totalCount;

        private PageResponse(int number, int size, int totalCount) {
            this.number = number;
            this.size = size;
            this.totalCount = totalCount;
        }

        public static PageResponse from(Page<?> page) {
            return new PageResponse(
                page.getNumber(),
                page.getSize(),
                (int)page.getTotalElements()
            );
        }
    }
}
