package com.xiaohuashifu.recruit.common.exception.unprocessable;

/**
 * 描述：非法状态异常
 *
 * @author xhsf
 * @create 2021/2/4 22:03
 */
public class InvalidStatusServiceException extends UnprocessableServiceException {
    public InvalidStatusServiceException() {}

    public InvalidStatusServiceException(String message) {
        super(message);
    }
}