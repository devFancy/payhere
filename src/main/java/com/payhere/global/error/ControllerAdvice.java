package com.payhere.global.error;

import com.payhere.auth.exception.AuthorizationException;
import com.payhere.auth.exception.EmptyAuthorizationHeaderException;
import com.payhere.auth.exception.InvalidLoginException;
import com.payhere.auth.exception.InvalidTokenException;
import com.payhere.global.ApiResponse;
import com.payhere.global.error.dto.ErrorReportRequest;
import com.payhere.global.error.dto.ErrorResponse;
import com.payhere.owner.exception.InvalidCellPhoneNumberException;
import com.payhere.owner.exception.InvalidPasswordFormatException;
import com.payhere.owner.exception.NotFoundOwnerException;
import com.payhere.product.exception.InvalidProductException;
import com.payhere.product.exception.NotFoundProductException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ControllerAdvice.class);

    private static final String INVALID_DTO_FIELD_ERROR_MESSAGE_FORMAT = "%s 필드는 %s (전달된 값: %s)";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<ErrorResponse> handleMethodArgumentException(final MethodArgumentNotValidException e) {
        FieldError firstFieldError = e.getFieldErrors().get(0);
        String errorMessage = String.format(INVALID_DTO_FIELD_ERROR_MESSAGE_FORMAT, firstFieldError.getField()
                , firstFieldError.getDefaultMessage(), firstFieldError.getRejectedValue());

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return ApiResponse.badRequest(errorResponse);
    }

    @ExceptionHandler({
            InvalidProductException.class,
            InvalidCellPhoneNumberException.class,
            InvalidPasswordFormatException.class,
            InvalidLoginException.class
    })
    public ApiResponse<ErrorResponse> handleInvalidBadRequestException(final RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ApiResponse.badRequest(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<ErrorResponse> handLieInvalidRequestBodyException() {
        ErrorResponse errorResponse = new ErrorResponse("잘못된 형식의 RequestBody 입니다.");
        return ApiResponse.badRequest(errorResponse);
    }

    @ExceptionHandler({
            EmptyAuthorizationHeaderException.class,
            InvalidTokenException.class,
            AuthorizationException.class
    })
    public ApiResponse<ErrorResponse> handleUnauthorizedException(final RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ApiResponse.UnAuthorized(errorResponse);
    }

    @ExceptionHandler({
            NotFoundProductException.class,
            NotFoundOwnerException.class
    })
    public ApiResponse<ErrorResponse> handleNotFoundException(final RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ApiResponse.NotFound(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<ErrorResponse> handleUnexpectedException(final Exception e, final HttpServletRequest request) {
        ErrorReportRequest errorReportRequest = new ErrorReportRequest(request, e);
        log.error(errorReportRequest.getLogMessage(), e);

        ErrorResponse errorResponse = new ErrorResponse("서버에서 예상치 못한 에러가 발생했습니다.");
        return ApiResponse.internalServerError(errorResponse);
    }
}
