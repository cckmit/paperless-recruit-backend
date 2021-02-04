package com.xiaohuashifu.recruit.common.exception.unprocessable;

/**
 * 描述：未修改异常
 *
 * @author xhsf
 * @create 2021/2/4 21:32
 */
public class UnmodifiedServiceException extends UnprocessableServiceException {
    public UnmodifiedServiceException() {}

    public UnmodifiedServiceException(String message) {
        super(message);
    }
}
