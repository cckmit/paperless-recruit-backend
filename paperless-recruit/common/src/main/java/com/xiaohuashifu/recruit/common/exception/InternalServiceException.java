package com.xiaohuashifu.recruit.common.exception;

/**
 * 描述：内部异常
 *
 * @author xhsf
 * @create 2021/2/6 02:23
 */
public class InternalServiceException extends ServiceException {

    public InternalServiceException() {}

    public InternalServiceException(String message) {
        super(message);
    }

}

