package com.xiaohuashifu.recruit.user.handler;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.ConfigUtils;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.validation.Validation;
import org.apache.dubbo.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@Activate
public class CustomValidationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CustomValidationFilter.class);

    private Validation validation;

    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("---");
        System.out.println(validation);
        return invoker.invoke(invocation);
//        if (validation != null && !invocation.getMethodName().startsWith("$")) {
//            try {
//                Validator validator = validation.getValidator(invoker.getUrl());
//                if (validator != null) {
//                    validator.validate(invocation.getMethodName(), invocation.getParameterTypes(), invocation.getArguments());
//                }
//            } catch (RpcException e) {
//                throw e;
//            } catch (ConstraintViolationException e) {
//                Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
//                logger.info("校验错误详情：{}", constraintViolations);
//                if (constraintViolations.size() > 0) {
//                    // 不用关心所有的校验失败情况，关心一个就够了
//                    System.out.println("----------------");
//                    return null;
//                }
//
//            } catch (Throwable t) {
//                return null;
//            }
//        }
//        return invoker.invoke(invocation);
    }

}