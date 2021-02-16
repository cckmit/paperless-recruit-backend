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

}

