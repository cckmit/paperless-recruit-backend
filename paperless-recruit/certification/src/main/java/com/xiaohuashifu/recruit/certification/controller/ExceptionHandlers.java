package com.xiaohuashifu.recruit.certification.controller;

import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/8 21:23
 */
@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(value = ValidationException.class)
    public Result constraintViolationExceptionHandler(ValidationException ex) {
        System.out.println(ex.getClass());
        // 拼接错误
//        StringBuilder detailMessage = new StringBuilder();
//        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
//            // 使用 , 分隔多个错误
//            if (detailMessage.length() > 0) {
//                detailMessage.append(",");
//            }
//            // 拼接内容到其中
//            detailMessage.append(constraintViolation.getMessage());
//        }
        System.out.println("---------------------异常处理---------------------------");
        return Result.fail(ErrorCode.INVALID_PARAMETER, "--------------------调用失败-------------------------");
    }
}