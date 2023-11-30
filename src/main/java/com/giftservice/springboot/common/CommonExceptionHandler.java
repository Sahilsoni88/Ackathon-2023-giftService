package com.giftservice.springboot.common;

import com.giftservice.springboot.exceptions.InsufficientBalanceException;
import kotlin.Result;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(value = { RuntimeException.class })
    public final ResponseEntity<CommonResponse> handleCommonException(final RuntimeException ex) {
        return new ResponseEntity<>(
                CommonResponse.builder()
                        .status("failure")
                        .message(ex.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = { ArithmeticException.class })
    public final ResponseEntity<CommonResponse> handleAuthenticationException(final InsufficientBalanceException ex) {
        return new ResponseEntity<>(
                CommonResponse.builder()
                        .status("failure")
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = { InsufficientBalanceException.class })
    public final ResponseEntity<CommonResponse> handleInsufficientBalanceException(final InsufficientBalanceException ex) {
        return new ResponseEntity<>(
                CommonResponse.builder()
                        .status("failure")
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }
}
