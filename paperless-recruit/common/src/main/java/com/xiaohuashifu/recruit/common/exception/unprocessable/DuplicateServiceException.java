package com.xiaohuashifu.recruit.common.exception.unprocessable;

/**
 * 描述：重复异常
 *
 * @author xhsf
 * @create 2021/2/4 22:03
 */
public class DuplicateServiceException extends UnprocessableServiceException {
    public DuplicateServiceException() {}

    public DuplicateServiceException(String message) {
        super(message);
    }
}