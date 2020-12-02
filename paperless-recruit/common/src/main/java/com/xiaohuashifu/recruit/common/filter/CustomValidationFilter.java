package com.xiaohuashifu.recruit.common.filter;

import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.ConfigUtils;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.validation.Validation;
import org.apache.dubbo.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;
import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;
import static org.apache.dubbo.common.constants.FilterConstants.VALIDATION_KEY;

/**
 * 描述：参数校验过滤器，改写自 ValidationFilter，
 *          该类捕获 ConstraintViolationException 异常，并封装成 Result 对象返回
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
@Activate(group = {CONSUMER, PROVIDER}, value = VALIDATION_KEY, order = 10000)
public class CustomValidationFilter implements Filter {

    private Validation validation;

    /**
     * Sets the validation instance for ValidationFilter
     * @param validation Validation instance injected by dubbo framework based on "validation" attribute value.
     */
    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    /**
     * Perform the validation of before invoking the actual method based on <b>validation</b> attribute value.
     * @param invoker    service
     * @param invocation invocation.
     * @return Method invocation result
     * @throws RpcException Throws RpcException if  validation failed or any other runtime exception occurred.
     */
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (validation != null && !invocation.getMethodName().startsWith("$")
                && ConfigUtils.isNotEmpty(invoker.getUrl().getMethodParameter(invocation.getMethodName(), VALIDATION_KEY))) {
            try {
                Validator validator = validation.getValidator(invoker.getUrl());
                if (validator != null) {
                    validator.validate(invocation.getMethodName(), invocation.getParameterTypes(), invocation.getArguments());
                }
            } catch (RpcException e) {
                throw e;
            }
            // 添加 catch ConstraintViolationException 用于实现自定义的参数校验异常处理逻辑
            catch (ConstraintViolationException e) {
                Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
                ConstraintViolation<?> firsConstraintViolation = constraintViolations.iterator().next();
                return AsyncRpcResult.newDefaultAsyncResult(
                        com.xiaohuashifu.recruit.common.result.Result.fail(
                                ErrorCodeEnum.INVALID_PARAMETER, firsConstraintViolation.getMessage()), invocation);
            } catch (ValidationException e) {
                return AsyncRpcResult.newDefaultAsyncResult(new ValidationException(e.getMessage()), invocation);
            } catch (Throwable t) {
                return AsyncRpcResult.newDefaultAsyncResult(t, invocation);
            }
        }
        return invoker.invoke(invocation);
    }

}