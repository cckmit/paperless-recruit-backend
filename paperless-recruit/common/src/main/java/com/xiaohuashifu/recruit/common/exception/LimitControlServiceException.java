package com.xiaohuashifu.recruit.common.exception;

/**
 * 描述：触发流控异常
 *
 * @author xhsf
 * @create 2021/2/5 19:09
 */
public class LimitControlServiceException extends ServiceException {

    public LimitControlServiceException() {}

    public LimitControlServiceException(String message) {
        super(message);
    }

}
