package com.xiaohuashifu.recruit.common.exception;

/**
 * 描述：未知异常
 *
 * @author xhsf
 * @create 2021/2/6 02:23
 */
public class UnknownServiceException extends ServiceException {

    public UnknownServiceException() {}

    public UnknownServiceException(String message) {
        super(message);
    }

    public UnknownServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownServiceException(Throwable cause) {
        super(cause);
    }

    public UnknownServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

