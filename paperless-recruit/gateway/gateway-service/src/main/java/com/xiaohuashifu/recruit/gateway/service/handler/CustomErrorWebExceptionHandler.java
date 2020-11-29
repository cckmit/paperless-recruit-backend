package com.xiaohuashifu.recruit.gateway.service.handler;


import com.xiaohuashifu.recruit.common.result.ErrorCode;
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
        map.put("success", false);
        map.put("data", null);
        Throwable error = super.getError(request);
        map.put("message", error.getMessage());
        map.put("errorCode", ErrorCode.INVALID_PARAMETER);

        // 网关的异常
        if (error instanceof NotFoundException) {
            map.put("errorCode", ErrorCode.INVALID_PARAMETER_NOT_FOUND);
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
        ErrorCode errorCode = (ErrorCode) errorAttributes.get("errorCode");
        return errorCode.getHttpStatus().value();
    }

}
