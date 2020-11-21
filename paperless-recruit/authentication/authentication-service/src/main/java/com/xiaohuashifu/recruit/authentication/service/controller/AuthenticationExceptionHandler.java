package com.xiaohuashifu.recruit.authentication.service.controller;

import com.xiaohuashifu.recruit.common.result.rest.ErrorCode;
import com.xiaohuashifu.recruit.common.result.rest.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/19 20:42
 */
@RestControllerAdvice
public class AuthenticationExceptionHandler {
    @ExceptionHandler(InvalidGrantException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidGrantExceptionExceptionHandler(InvalidGrantException e) {
        return new ErrorResponse(ErrorCode.INVALID_PARAMETER.getCode(), e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badCredentialsExceptionExceptionHandler(BadCredentialsException e) {
        return new ErrorResponse(ErrorCode.INVALID_PARAMETER.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse processingExceptionHandler(RuntimeException e) {
        e.printStackTrace();
        return new ErrorResponse(ErrorCode.INVALID_PARAMETER.getCode(), e.getMessage());
    }
}
