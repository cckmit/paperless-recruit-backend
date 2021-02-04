package com.xiaohuashifu.recruit.common.exception.unprocessable;

import com.xiaohuashifu.recruit.common.exception.ServiceException;

/**
 * 描述：无法处理异常
 *
 * @author xhsf
 * @create 2021/2/4 18:39
 */
public class UnprocessableServiceException extends ServiceException {
    public UnprocessableServiceException() {}

    public UnprocessableServiceException(String message) {
        super(message);
    }
}
