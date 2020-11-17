package com.xiaohuashifu.recruit.common.result.rest;

import java.io.Serializable;

/**
 * 描述: 错误响应对象
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
public class ErrorResponse implements Serializable {

    /**
     * 错误码
     */
    private String error;

    /**
     * 错误信息
     */
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
