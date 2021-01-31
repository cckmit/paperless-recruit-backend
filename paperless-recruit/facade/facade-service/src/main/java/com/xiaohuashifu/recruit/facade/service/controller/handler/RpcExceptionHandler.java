package com.xiaohuashifu.recruit.facade.service.controller.handler;

import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 描述：RpcException 处理
 *
 * @author xhsf
 * @create 2021/1/17 01:21
 */
@Order(Integer.MIN_VALUE)
@RestControllerAdvice
@Slf4j
public class RpcExceptionHandler {

    @ExceptionHandler(RpcException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse handleRpcException(RpcException e) {
        log.error("Catch a rpc exception.", e);
        return new ErrorResponse(ErrorCodeEnum.SERVICE_UNAVAILABLE.getCode(),
                ErrorCodeEnum.SERVICE_UNAVAILABLE.getMessage());
    }

    @ExceptionHandler(RemotingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRemotingException(RemotingException e) {
        log.error("Catch a rpc remoting exception.", e);
        return new ErrorResponse(ErrorCodeEnum.INTERNAL_ERROR.getCode(),
                ErrorCodeEnum.INTERNAL_ERROR.getMessage());
    }

}
