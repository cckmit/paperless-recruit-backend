package com.xiaohuashifu.recruit.common.exception;

/**
 * 描述：第三方服务异常
 *
 * @author xhsf
 * @create 2021/2/5 19:09
 */
public class ThirdPartyServiceException extends ServiceException {

    public ThirdPartyServiceException() {}

    public ThirdPartyServiceException(String message) {
        super(message);
    }

    public ThirdPartyServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThirdPartyServiceException(Throwable cause) {
        super(cause);
    }

    public ThirdPartyServiceException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
