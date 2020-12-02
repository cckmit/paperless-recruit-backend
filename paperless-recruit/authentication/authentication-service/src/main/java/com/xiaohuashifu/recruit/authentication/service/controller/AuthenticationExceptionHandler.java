package com.xiaohuashifu.recruit.authentication.service.controller;

import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 描述：统一拦截认证过程中发生的异常
 *
 * @author: xhsf
 * @create: 2020/11/19 20:42
 */
@RestControllerAdvice
public class AuthenticationExceptionHandler {
    @ExceptionHandler(InvalidGrantException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse invalidGrantExceptionExceptionHandler(InvalidGrantException e) {
        return new ErrorResponse(ErrorCodeEnum.INVALID_PARAMETER.getCode(), e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse badCredentialsExceptionExceptionHandler(BadCredentialsException e) {
        return new ErrorResponse(ErrorCodeEnum.INVALID_PARAMETER.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse processingExceptionHandler(RuntimeException e) {
        return new ErrorResponse(ErrorCodeEnum.INVALID_PARAMETER.getCode(), e.getMessage());
    }
}
