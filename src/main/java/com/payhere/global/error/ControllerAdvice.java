package com.payhere.global.error;

import com.payhere.auth.exception.AuthorizationException;
import com.payhere.auth.exception.EmptyAuthorizationHeaderException;
import com.payhere.auth.exception.InvalidLoginException;
import com.payhere.auth.exception.InvalidTokenException;
import com.payhere.global.ApiResponse;
import com.payhere.global.error.dto.ErrorResponse;
import com.payhere.owner.exception.InvalidCellPhoneNumberException;
import com.payhere.owner.exception.InvalidPasswordFormatException;
import com.payhere.owner.exception.NotFoundOwnerException;
import com.payhere.product.exception.InvalidProductException;
import com.payhere.product.exception.NotFoundProductException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

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
}
