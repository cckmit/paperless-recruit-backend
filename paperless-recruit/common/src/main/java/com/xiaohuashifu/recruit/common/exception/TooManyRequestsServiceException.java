package com.xiaohuashifu.recruit.common.exception;

/**
 * 描述：太多请求异常
 *
 * @author xhsf
 * @create 2021/2/6 02:23
 */
public class TooManyRequestsServiceException extends ServiceException {

    public TooManyRequestsServiceException() {}

    public TooManyRequestsServiceException(String message) {
        super(message);
    }

}

