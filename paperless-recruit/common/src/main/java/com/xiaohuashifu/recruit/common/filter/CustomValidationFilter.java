package com.xiaohuashifu.recruit.common.filter;

import com.xiaohuashifu.recruit.common.result.ErrorCode;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.ConfigUtils;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.validation.Validation;
import org.apache.dubbo.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.dubbo.common.constants.CommonConstants.CONSUMER;
import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;
import static org.apache.dubbo.common.constants.FilterConstants.VALIDATION_KEY;

/**
 * 描述：参数校验过滤器，改写自ValidationFilter，
 *          该类捕获ConstraintViolationException异常，并封装成Result对象返回
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 15:05
 */
@Activate(group = {CONSUMER, PROVIDER}, value = VALIDATION_KEY, order = 10000)
public class CustomValidationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CustomValidationFilter.class);

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
            // 添加catch ConstraintViolationException用于实现自定义的参数校验异常处理逻辑
            catch (ConstraintViolationException e) {
                Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
                logger.info("校验错误详情：{}", constraintViolations);
                // 把Set<ConstraintViolation<?>>转换成List<CustomConstraintViolation>
                final List<CustomConstraintViolation> collect = constraintViolations.stream()
                        .map(CustomConstraintViolation::buildCustomConstraintViolation)
                        .collect(Collectors.toList());
                return AsyncRpcResult.newDefaultAsyncResult(
                        com.xiaohuashifu.recruit.common.result.Result.fail(
                                ErrorCode.INVALID_PARAMETER, collect), invocation);
            } catch (ValidationException e) {
                return AsyncRpcResult.newDefaultAsyncResult(new ValidationException(e.getMessage()), invocation);
            } catch (Throwable t) {
                return AsyncRpcResult.newDefaultAsyncResult(t, invocation);
            }
        }
        return invoker.invoke(invocation);
    }

}