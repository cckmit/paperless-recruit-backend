package com.xiaohuashifu.recruit.facade.service.controller.handler;

import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 描述：全局异常处理
 *
 * @author xhsf
 * @create 2021/1/15 23:05
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException e) {
        log.error("Catch an unprocessed exception.", e);
        return new ErrorResponse(ErrorCodeEnum.INTERNAL_ERROR.getCode(), ErrorCodeEnum.INTERNAL_ERROR.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(Throwable e) {
        log.error("Catch an unprocessed exception.", e);
        return new ErrorResponse(ErrorCodeEnum.INTERNAL_ERROR.getCode(), ErrorCodeEnum.INTERNAL_ERROR.getMessage());
    }

}
