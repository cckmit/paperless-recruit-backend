package com.xiaohuashifu.recruit.user.handler;

import com.google.protobuf.ServiceException;
import com.xiaohuashifu.recruit.common.result.Result;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/1 20:17
 */
@ControllerAdvice
public class ExceptionController {
    /**
     * 自定义业务异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result<?> catchServiceException(ServiceException e) {
        System.out.println("异常捕获");
        return null;
    }

    /**
     * 参数非法异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Result<?> catchIllegalArgumentException(IllegalArgumentException e) {
        System.out.println("异常捕获");
        return null;
    }

    /**
     * 入参校验异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<?> catchMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        System.out.println("异常捕获");
        return null;
    }

    /**
     * 通用Exception处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> catchException(Exception e) {
        System.out.println("异常捕获");
        return null;
    }
}
