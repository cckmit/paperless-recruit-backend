package com.xiaohuashifu.recruit.common.result;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * 描述: 错误码与错误信息的映射
 *
 * @author xhsf
 * @create 2019-10-09
 */
public enum ErrorCodeEnum implements Serializable {

    /**
     * 非法参数
     */
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST,
            "InvalidParameter", "The required parameter is not valid."),

    /**
     * 非法参数，不匹配
     */
    INVALID_PARAMETER_MISMATCH(HttpStatus.BAD_REQUEST, "InvalidParameter.Mismatch", "Mismatch."),

    /**
     * 非法 Code
     */
    INVALID_PARAMETER_CODE(HttpStatus.NOT_FOUND,
            "InvalidParameter.Code", "The code is invalid."),


    /**
     * 非法参数，不支持的类型
     */
    INVALID_PARAMETER_UNSUPPORTED(HttpStatus.BAD_REQUEST,
            "InvalidParameter.Unsupported", "Unsupported parameter."),

    /**
     * 非法参数，不支持的 APP 类型
     */
    INVALID_PARAMETER_APP_UNSUPPORTED(HttpStatus.BAD_REQUEST,
            "InvalidParameter.App.Unsupported", "Unsupported app."),

    /**
     * 非法参数，值不正确
     */
    INVALID_PARAMETER_INCORRECT(HttpStatus.BAD_REQUEST,
            "InvalidParameter.Incorrect", "Incorrect parameter."),

    /**
     * 非法参数，验证码错误
     */
    INVALID_PARAMETER_AUTH_CODE_INCORRECT(HttpStatus.BAD_REQUEST,
            "InvalidParameter.AuthCode.Incorrect", "Incorrect auth code."),

    /**
     * 参数为空，即字符串，集合等为 null 或者长度为0
     */
    INVALID_PARAMETER_IS_EMPTY(HttpStatus.BAD_REQUEST,
            "InvalidParameter.IsEmpty", "The required parameter must be not empty."),

    /**
     * 参数为 null
     */
    INVALID_PARAMETER_IS_NULL(HttpStatus.BAD_REQUEST,
            "InvalidParameter.IsNull", "The required parameter must be not null."),

    /**
     * 字符串参数为 null 或者没有非空白字符
     */
    INVALID_PARAMETER_IS_BLANK(HttpStatus.BAD_REQUEST,
            "InvalidParameter.IsBlank", "The required parameter must be not blank."),

    /**
     * 参数值超过限定范围
     */
    INVALID_PARAMETER_VALUE_EXCEEDED(HttpStatus.BAD_REQUEST,
            "InvalidParameter.ValueExceeded", "The name of {Parameter} exceeded, max: {Value}."),

    /**
     * 参数值小于限定范围
     */
    INVALID_PARAMETER_VALUE_BELOW(HttpStatus.BAD_REQUEST,
            "InvalidParameter.ValueBelow", "The name of {Parameter} below, min: {Value}."),

    /**
     * 参数值范围不正确
     */
    INVALID_PARAMETER_VALUE(HttpStatus.BAD_REQUEST,
            "InvalidParameter.Value", "The parameter of {Parameter} is invalid."),

    /**
     * 参数长度不在规定范围内，String 或集合类型
     */
    INVALID_PARAMETER_SIZE(HttpStatus.BAD_REQUEST,
            "InvalidParameter.Size", "The size of {Parameter} must be from {Min} to {Max}."),

    /**
     * 非法参数，不存在
     */
    INVALID_PARAMETER_NOT_EXIST(HttpStatus.BAD_REQUEST,
            "InvalidParameter.NotExist", "The resource of this parameter does not exist."),

    /**
     * 非法参数，不存在
     */
    INVALID_PARAMETER_USER_NOT_EXIST(HttpStatus.BAD_REQUEST,
            "InvalidParameter.User.NotExist", "The user does not exist."),

    /**
     * 非法参数，不可用
     */
    INVALID_PARAMETER_NOT_AVAILABLE(HttpStatus.BAD_REQUEST,
            "InvalidParameter.NotAvailable", "The resource of this parameter is not available."),

    /**
     * 未知错误
     */
    UNKNOWN_ERROR(HttpStatus.BAD_REQUEST,
            "UnknownError", "The request processing has failed due to some unknown exception."),

    /**
     * 短时间内请求过多
     */
    THROTTLING(HttpStatus.BAD_REQUEST,
            "Throttling", "You have made too many requests within a short time."),

    /**
     * 未通过认证
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,
            "Unauthorized", "User not authorized."),

    /**
     * 没有附带请求该资源所需的 token
     */
    UNAUTHORIZED_TOKEN_IS_NULL(HttpStatus.UNAUTHORIZED,
            "Unauthorized.TokenIsNull", "The request auth token is null."),

    /**
     * 被禁止
     */
    FORBIDDEN(HttpStatus.FORBIDDEN,
            "Forbidden", "Forbidden."),

    /**
     * 用户被禁止
     */
    FORBIDDEN_USER(HttpStatus.FORBIDDEN,
            "Forbidden.User", "Forbidden."),

    /**
     * 用户在未认证的情况下操作该资源
     */
    FORBIDDEN_UNAUTHORIZED(HttpStatus.FORBIDDEN,
            "Forbidden.Unauthorized", "User not authorized to operate on the specified resource."),

    /**
     * 被禁止，因为不可用
     */
    FORBIDDEN_UNAVAILABLE(HttpStatus.FORBIDDEN,
            "Forbidden.Unavailable", "Forbidden."),

    /**
     * 被禁止，因为被停用
     */
    FORBIDDEN_DEACTIVATED(HttpStatus.FORBIDDEN,
            "Forbidden.Deactivated", "Forbidden."),


    /**
     * 未找到该参数对应的资源
     */
    INVALID_PARAMETER_NOT_FOUND(HttpStatus.NOT_FOUND,
            "InvalidParameter.NotFound", "The specified resource does not exist."),

    /**
     * 未找到验证码
     */
    INVALID_PARAMETER_AUTH_CODE_NOT_FOUND(HttpStatus.NOT_FOUND,
            "InvalidParameter.AuthCode.NotFound", "The auth code does not exist."),

    /**
     * 未找到验证码
     */
    INVALID_PARAMETER_AUTH_CODE_NOT_EXIST(HttpStatus.NOT_FOUND,
            "InvalidParameter.AuthCode.NotExist", "The auth code does not exist."),

    /**
     * 未找到 openid
     */
    INVALID_PARAMETER_OPENID_NOT_FOUND(HttpStatus.NOT_FOUND,
            "InvalidParameter.NotFound", "The specified resource does not exist."),

    /**
     * 操作冲突
     */
    OPERATION_CONFLICT(HttpStatus.CONFLICT,
            "OperationConflict", "Request was denied due to conflict with a previous request."),

    /**
     * 操作冲突，由于未做任何修改修改
     */
    OPERATION_CONFLICT_UNMODIFIED(HttpStatus.CONFLICT,
            "OperationConflict.Unmodified", "Request was denied due to unmodified."),

    /**
     * 操作冲突，状态不正确（不允许）
     */
    OPERATION_CONFLICT_STATUS(HttpStatus.CONFLICT,
            "OperationConflict.Status", "Request was denied due to invalid status."),

    /**
     * 操作冲突，获取锁失败
     */
    OPERATION_CONFLICT_LOCK(HttpStatus.CONFLICT,
            "OperationConflict.Lock", "Request was denied due to acquire lock failed."),

    /**
     * 操作冲突，超过限定额度
     */
    OPERATION_CONFLICT_OVER_LIMIT(HttpStatus.CONFLICT,
            "OperationConflict.OverLimit", "Request was denied due to over limit."),

    /**
     * 操作冲突，重复
     */
    OPERATION_CONFLICT_DUPLICATE(HttpStatus.CONFLICT,
            "OperationConflict.Duplicate", "Request was denied due to duplicate."),

    /**
     * 操作冲突，已停用
     */
    OPERATION_CONFLICT_DEACTIVATED(HttpStatus.CONFLICT,
            "OperationConflict.Deactivated", "Request was denied due to deactivated."),

    /**
     * 太多请求
     */
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "TooManyRequests", "Too many requests."),

    /**
     * 由于某些未知错误、异常或失败，请求处理失败。
     */
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "InternalError",
            "The request processing has failed due to some unknown exception, exception or failure."),

    /**
     * 服务不可用
     */
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "ServiceUnavailable",
            "The request has failed due to a temporary failure of the server."),

    /**
     * 请求超时
     */
    SERVICE_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "ServiceTimeout",
            "The request processing has failed due to timeout.");

    /**
     * HTTP 响应码，符合 HTTP 协议语义
     */
    private final HttpStatus httpStatus;

    /**
     * 错误标识
     */
    private final String code;

    /**
     * 错误消息
     */
    private final String message;

    ErrorCodeEnum(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * 通过 code 获取 HttpStatus
     *
     * @param code ErrorCodeEnum.code
     * @return 这里若找不到对应的 HttpStatus，会默认返回 HttpStatus.BAD_REQUEST
     */
    public static HttpStatus getHttpStatus(String code) {
        for (ErrorCodeEnum value : ErrorCodeEnum.values()) {
            if (Objects.equals(value.code, code)) {
                return value.httpStatus;
            }
        }
        return HttpStatus.BAD_REQUEST;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return code;
    }

}
