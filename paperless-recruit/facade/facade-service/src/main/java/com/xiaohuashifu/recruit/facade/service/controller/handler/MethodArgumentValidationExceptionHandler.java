package com.xiaohuashifu.recruit.facade.service.controller.handler;

import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 描述：方法参数校验异常处理
 *
 * @author xhsf
 * @create 2021/1/31 22:08
 */
@Order(Integer.MIN_VALUE)
@RestControllerAdvice
public class MethodArgumentValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        return new ErrorResponse(ErrorCodeEnum.UNPROCESSABLE_ENTITY.getCode(),
                fieldError.getField() + ":" + fieldError.getDefaultMessage());
    }

}
