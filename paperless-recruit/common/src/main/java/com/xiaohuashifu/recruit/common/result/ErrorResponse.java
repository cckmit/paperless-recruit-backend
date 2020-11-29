package com.xiaohuashifu.recruit.common.result;

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
    private String code;

    /**
     * 错误信息
     */
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
