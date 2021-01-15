package com.xiaohuashifu.recruit.facade.service.controller.v1.handler;

import com.xiaohuashifu.recruit.facade.service.exception.ResponseEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 描述：ResponseEntity 异常处理
 *
 * @author xhsf
 * @create 2021/1/15 19:55
 */
@RestControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(ResponseEntityException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(ResponseEntityException e) {
        return e.getResponseEntity();
    }

}
