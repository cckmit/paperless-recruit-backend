package com.xiaohuashifu.recruit.facade.service.controller.handler;

import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 描述：参数绑定异常处理
 *
 * @author xhsf
 * @create 2021/1/15 18:23
 */
@Order(Integer.MIN_VALUE)
@RestControllerAdvice
public class ParameterBindingExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException() {
        return new ErrorResponse(ErrorCodeEnum.INVALID_PARAMETER.getCode(), "Invalid parameter.");
    }

}
