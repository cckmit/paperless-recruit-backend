package com.xiaohuashifu.recruit.user.controller.handler;

import com.wudagezhandui.shixun.xianyu.result.ErrorCode;
import com.wudagezhandui.shixun.xianyu.result.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

/**
 * 描述: 对参数绑定的异常进行统一处理
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-08 18:51
 */
@ControllerAdvice
@ResponseBody
public class BindingExceptionHandler {

    /**
     * 用于处理入参绑定异常ConstraintViolationException
     * @param e ConstraintViolationException
     * @return ErrorResponse
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse constraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations().iterator().next().getMessage();
        return assembleErrorMessageToErrorResponse(message);
    }

    /**
     *  用于处理入参绑定异常BindException
     * @param e BindException
     * @return ErrorResponse
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse bindExceptionHandler(BindException e) {
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return assembleErrorMessageToErrorResponse(message);
    }

    /**
     * 把错误信息装配成ErrorResponse
     * @param errorMessage String
     * @return ErrorResponse
     */
    private ErrorResponse assembleErrorMessageToErrorResponse(String errorMessage) {
        int idx = errorMessage.indexOf(":");
        //没有分隔符，表示只有错误码，直接返回
        if (idx == -1) {
            ErrorCode errorCode = ErrorCode.valueOf(errorMessage);
            return new ErrorResponse(errorCode.getError(), errorCode.getMessage());
        }
        //有分隔符，获取错误信息进行填充
        String error = errorMessage.substring(0, idx);
        String message = errorMessage.substring(idx + 1);
        ErrorCode errorCode = ErrorCode.valueOf(error);
        return new ErrorResponse(errorCode.getError(), message.trim());
    }

}