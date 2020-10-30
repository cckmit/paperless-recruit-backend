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
    private Boolean success;

    /**
     * 业务数据
     */
    private T data;

    /**
     * 错误码，在出错时才会带上
     */
    private ErrorCode errorCode;

    /**
     * 错误简短信息
     */
    private String message;

    private Result(Boolean success) {
        this.success = success;
    }

    private Result(Boolean success, T data) {
        this(success);
        this.data = data;
    }

    private Result(Boolean success, ErrorCode errorCode) {
        this(success);
        this.errorCode = errorCode;
    }

    private Result(Boolean success, String message) {
        this(success);
        this.message = message;
    }

    private Result(Boolean success, ErrorCode errorCode, String message) {
        this(success);
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * 成功调用时的构造方法
     *
     * @return Result
     */
    public static <T> Result<T> success() {
        return new Result<>(true, "OK");
    }

    /**
     * 成功调用时的构造方法
     *
     * @param data 业务数据
     * @return Result<T>
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(true, data);
    }

    /**
     * 成功调用时的构造方法
     *
     * @param format 格式化字符串
     * @param args 参数
     * @return Result<T>
     */
    public static <T> Result<T> success(String format, Object... args) {
        return new Result<>(true, MessageFormat.format(format, args));
    }


    /**
     * 失败调用时的构造方法
     *
     * @param errorCode 错误码
     * @param message 错误简短信息
     * @return Result<T>
     */
    public static <T> Result<T> fail(ErrorCode errorCode, String message) {
        return new Result<>(false, errorCode, message);
    }

    /**
     * 失败调用时的构造方法
     *
     * @param errorCode 错误码
     * @param format 格式化字符串
     * @param args 参数
     * @return Result<T>
     */
    public static <T> Result<T> fail(ErrorCode errorCode, String format, Object... args) {
        return new Result<>(false, errorCode, MessageFormat.format(format, args));
    }

    /**
     * 失败调用时的构造方法
     *
     * @param errorCode 错误码
     * @return Result<T>
     */
    public static <T> Result<T> fail(ErrorCode errorCode) {
        return new Result<>(false, errorCode);
    }

    /**
     * 失败调用时的构造方法
     *
     * @param result 结果
     * @return Result<T1, T2>
     */
    public static <T1, T2> Result<T1> fail(Result<T2> result) {
        return new Result<>(false, result.getErrorCode(), result.getMessage());
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
