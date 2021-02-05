package com.xiaohuashifu.recruit.common.exception;

/**
 * 描述：操作冲突异常
 *
 * @author xhsf
 * @create 2021/2/5 19:09
 */
public class OperationConflictServiceException extends ServiceException {

    public OperationConflictServiceException() {}

    public OperationConflictServiceException(String message) {
        super(message);
    }

}
