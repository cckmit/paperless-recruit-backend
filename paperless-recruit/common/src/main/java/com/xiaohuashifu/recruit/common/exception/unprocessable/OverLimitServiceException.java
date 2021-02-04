package com.xiaohuashifu.recruit.common.exception.unprocessable;

/**
 * 描述：超过限制异常
 *
 * @author xhsf
 * @create 2021/2/4 21:32
 */
public class OverLimitServiceException extends UnprocessableServiceException {
    public OverLimitServiceException() {}

    public OverLimitServiceException(String message) {
        super(message);
    }
}
