package com.xiaohuashifu.recruit.common.exception;

import java.text.MessageFormat;

/**
 * 描述：找不到异常
 *
 * @author xhsf
 * @create 2021/2/4 18:39
 */
public class NotFoundServiceException extends ServiceException {

    /**
     * 未找到的消息模板
     */
    private static final String NOT_FOUND_MESSAGE_TEMPLATE = "The {0} does not exist. {1} = {2}.";

    public NotFoundServiceException() {}

    public NotFoundServiceException(String message) {
        super(message);
    }

    public NotFoundServiceException(String resourceName, String identifierName, Object identifierValue) {
        super(MessageFormat.format(NOT_FOUND_MESSAGE_TEMPLATE, resourceName,identifierName, identifierValue));
    }
}
