package com.payhere.global;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private final Meta meta;
    private final T data;

    private ApiResponse(int code, String message, T data) {
        this.meta = new Meta(code, message);
        this.data = data;
    }

    public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message, T data) {
        return new ApiResponse<>(httpStatus.value(), message, data);
    }
    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
        return of(httpStatus, httpStatus.getReasonPhrase(), data);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, data);
    }

    public static<T> ApiResponse<T> created(T data) {
        return of(HttpStatus.CREATED, data);
    }

    public static <T> ApiResponse<T> noContent() {
        return of(HttpStatus.NO_CONTENT, null);
    }

    public static <T> ApiResponse<T> badRequest(T data) {
        return of(HttpStatus.BAD_REQUEST, data);
    }

    public static <T> ApiResponse<T> UnAuthorized(T data) {
        return of(HttpStatus.UNAUTHORIZED, data);
    }

    public static <T> ApiResponse<T> NotFound(T data) {
        return of(HttpStatus.NOT_FOUND, data);
    }

    @Getter
    private static class Meta {
        private final int code;
        private final String message;

        private Meta(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
