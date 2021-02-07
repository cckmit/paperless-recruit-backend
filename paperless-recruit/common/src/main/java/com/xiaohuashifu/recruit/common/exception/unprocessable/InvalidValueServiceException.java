package com.xiaohuashifu.recruit.common.exception.unprocessable;

/**
 * 描述：非法的值异常
 *
 * @author xhsf
 * @create 2021/2/4 22:03
 */
public class InvalidValueServiceException extends UnprocessableServiceException {
    public InvalidValueServiceException() {}

    public InvalidValueServiceException(String message) {
        super(message);
    }
}