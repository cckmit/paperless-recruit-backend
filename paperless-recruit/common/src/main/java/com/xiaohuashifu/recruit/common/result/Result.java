package com.xiaohuashifu.recruit.common.result;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * 描述: 请求结果封装
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-10-09
 */
public class Result<T> implements Serializable {

    /**
     * 调用是否成功
     */
    private final Boolean success;

    /**
     * 业务数据
     */
    private final T data;

    /**
     * 错误码，在出错时才会带上
     */
    private final ErrorCodeEnum errorCode;

    /**
     * 错误简短信息
     */
    private final String message;

    private static final Result<Void> SUCCESS = new Result<>(true, null, null, null);

    private Result(Boolean success, T data, ErrorCodeEnum errorCode, String message) {
        this.success = success;
        this.data = data;
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * 成功调用时的构造方法
     *
     * @return Result
     */
    public static Result<Void> success() {
        return SUCCESS;
    }

    /**
     * 成功调用时的构造方法
     *
     * @param data 业务数据
     * @return Result<T>
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(true, data, null, null);
    }

    /**
     * 失败调用时的构造方法
     *
     * @param errorCode 错误码
     * @param message 错误简短信息
     * @return Result<T>
     */
    public static <T> Result<T> fail(ErrorCodeEnum errorCode, String message) {
        return new Result<>(false, null, errorCode, message);
    }

    /**
     * 失败调用时的构造方法
     *
     * @param errorCode 错误码
     * @param format 格式化字符串
     * @param args 参数
     * @return Result<T>
     */
    public static <T> Result<T> fail(ErrorCodeEnum errorCode, String format, Object... args) {
        return new Result<>(false, null, errorCode, MessageFormat.format(format, args));
    }

    /**
     * 失败调用时的构造方法
     *
     * @param errorCode 错误码
     * @return Result<T>
     */
    public static <T> Result<T> fail(ErrorCodeEnum errorCode) {
        return new Result<>(false, null, errorCode, null);
    }

    public Boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public ErrorCodeEnum getErrorCode() {
        return errorCode;
    }

    public Object getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", data=" + data +
                ", errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }
}
