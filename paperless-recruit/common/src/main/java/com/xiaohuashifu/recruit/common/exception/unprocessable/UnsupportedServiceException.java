package com.xiaohuashifu.recruit.common.exception.unprocessable;

/**
 * 描述：不支持异常
 *
 * @author xhsf
 * @create 2021/2/6 02:07
 */
public class UnsupportedServiceException extends UnprocessableServiceException {
    public UnsupportedServiceException() {}

    public UnsupportedServiceException(String message) {
        super(message);
    }
}
