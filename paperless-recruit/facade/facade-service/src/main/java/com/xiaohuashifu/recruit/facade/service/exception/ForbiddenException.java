package com.xiaohuashifu.recruit.facade.service.exception;

/**
 * 描述：禁止操作
 *
 * @author xhsf
 * @create 2021/1/9 20:50
 */
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }

}
