package com.xiaohuashifu.recruit.common.exception.unprocessable;

/**
 * 描述：不匹配异常
 *
 * @author xhsf
 * @create 2021/2/4 22:03
 */
public class MisMatchServiceException extends UnprocessableServiceException {
    public MisMatchServiceException() {}

    public MisMatchServiceException(String message) {
        super(message);
    }
}