package com.xiaohuashifu.recruit.common.exception.unprocessable;

/**
 * 描述：不可用异常
 *
 * @author xhsf
 * @create 2021/2/4 18:39
 */
public class UnavailableServiceException extends UnprocessableServiceException {
    public UnavailableServiceException() {}

    public UnavailableServiceException(String message) {
        super(message);
    }
}
