package com.xiaohuashifu.recruit.facade.service.controller.handler;

import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.result.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 描述：ServiceException 处理
 *
 * @author xhsf
 * @create 2021/1/17 01:21
 */
// TODO: 2021/3/16 某些ServiceException异常无法被这个类捕获
@Order(Integer.MIN_VALUE)
@RestControllerAdvice
@Slf4j
public class ServiceExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleServiceException(ServiceException e) {
        return new ErrorResponse("BadRequest", e.getMessage());
    }

}
