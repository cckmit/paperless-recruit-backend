package com.xiaohuashifu.recruit.common.exception;

/**
 * 描述：消息队列异常
 *
 * @author xhsf
 * @create 2021/2/6 02:23
 */
public class MqServiceException extends ServiceException {

    public MqServiceException() {}

    public MqServiceException(String message) {
        super(message);
    }

    public MqServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MqServiceException(Throwable cause) {
        super(cause);
    }

    public MqServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

