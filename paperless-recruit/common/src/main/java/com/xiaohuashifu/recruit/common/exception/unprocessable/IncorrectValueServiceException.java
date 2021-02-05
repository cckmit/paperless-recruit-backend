package com.xiaohuashifu.recruit.common.exception.unprocessable;

/**
 * 描述：不正确的值异常
 *
 * @author xhsf
 * @create 2021/2/4 22:03
 */
public class IncorrectValueServiceException extends UnprocessableServiceException {
    public IncorrectValueServiceException() {}

    public IncorrectValueServiceException(String message) {
        super(message);
    }
}