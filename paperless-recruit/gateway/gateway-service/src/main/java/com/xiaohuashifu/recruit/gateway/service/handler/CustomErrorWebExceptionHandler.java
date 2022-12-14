package com.xiaohuashifu.recruit.gateway.service.handler;


import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.server.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：自定义全局异常处理器
 *
 * @author xhsf
 * @create 2020/11/26 12:35
 */
public class CustomErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    /**
     * 错误响应的键1
     */
    private static final String ERROR_RESPONSE_MESSAGE_KEY = "message";

    /**
     * 错误响应的键2
     */
    private static final String ERROR_RESPONSE_CODE_KEY = "code";

    public CustomErrorWebExceptionHandler(
            ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
            ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
     * 获取异常属性
     * @param request ServerRequest
     * @param includeStackTrace includeStackTrace
     * @return Map<String, Object>
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Map<String, Object> map = new HashMap<>();
        Throwable error = super.getError(request);
        map.put(ERROR_RESPONSE_MESSAGE_KEY, error.getMessage());
        map.put(ERROR_RESPONSE_CODE_KEY, ErrorCodeEnum.INVALID_PARAMETER.getCode());

        // 网关的异常
        if (error instanceof NotFoundException) {
            map.put(ERROR_RESPONSE_CODE_KEY, ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }

        return map;
    }

    /**
     * 指定响应处理方法为JSON处理的方法
     *
     * @param errorAttributes ErrorAttributes
     * @return RouterFunction<ServerResponse>
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 获取对应的HttpStatus
     *
     * @param errorAttributes Map<String, Object>
     * @return HttpStatus
     */
    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        String code = (String) errorAttributes.get(ERROR_RESPONSE_CODE_KEY);
        return ErrorCodeEnum.getHttpStatus(code).value();
    }

}
