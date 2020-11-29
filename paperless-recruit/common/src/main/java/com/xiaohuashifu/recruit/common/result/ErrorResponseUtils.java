package com.xiaohuashifu.recruit.common.result;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 描述：错误响应工具类，用于快速构建错误响应
 *
 * @author xhsf
 * @create 2020/11/29 17:24
 */
public class ErrorResponseUtils {

    /**
     * 未知错误时使用
     */
    public static final ResponseEntity<ErrorResponse> UNKNOWN_ERROR =
            instanceResponseEntity(ErrorCode.UNKNOWN_ERROR, ErrorCode.UNKNOWN_ERROR.getMessage());

    /**
     * 用于快速构建ResponseEntity<ErrorResponse>
     *     会获取错误码里面的状态码
     *
     * @param errorCode 自定义错误码
     * @param message 错误信息
     * @return ResponseEntity<ErrorResponse>
     */
    public static ResponseEntity<ErrorResponse> instanceResponseEntity(
            ErrorCode errorCode, String message) {
        return instanceResponseEntity(errorCode, message, errorCode.getHttpStatus());
    }


    /**
     * 用于快速构建ResponseEntity<ErrorResponse>
     *
     * @param errorCode 自定义错误码
     * @param message 错误信息
     * @param httpStatus HTTP 状态码
     * @return ResponseEntity<ErrorResponse>
     */
    public static ResponseEntity<ErrorResponse> instanceResponseEntity(
            ErrorCode errorCode, String message, HttpStatus httpStatus) {
        return instanceResponseEntity(errorCode.getCode(), message, httpStatus);
    }

    /**
     * 用于快速构建ResponseEntity<ErrorResponse>
     *
     * @param code 自定义错误码
     * @param message 错误信息
     * @param httpStatus HTTP 状态码
     * @return ResponseEntity<ErrorResponse>
     */
    public static ResponseEntity<ErrorResponse> instanceResponseEntity(
            String code, String message, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setMessage(message);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

}
