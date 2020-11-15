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
    private Object message;

    private Result(Boolean success) {
        this.success = success;
    }

    /**
     * 成功调用时的构造方法
     *
     * @return Result
     */
    public static <T> Result<T> success() {
        return new Result<>(true);
    }

    /**
     * 成功调用时的构造方法
     *
     * @param data 业务数据
     * @return Result<T>
     */
    public static <T> Result<T> success(T data) {
        final Result<T> result = new Result<>(true);
        result.setData(data);
        return result;
    }

    /**
     * 失败调用时的构造方法
     *
     * @param errorCode 错误码
     * @param message 错误简短信息
     * @return Result<T>
     */
    public static <T> Result<T> fail(ErrorCode errorCode, Object message) {
        final Result<T> result = new Result<>(false);
        result.setErrorCode(errorCode);
        result.setMessage(message);
        return result;
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
        final Result<T> result = new Result<>(false);
        result.setErrorCode(errorCode);
        result.setMessage(MessageFormat.format(format, args));
        return result;
    }

    /**
     * 失败调用时的构造方法
     *
     * @param errorCode 错误码
     * @return Result<T>
     */
    public static <T> Result<T> fail(ErrorCode errorCode) {
        final Result<T> result = new Result<>(false);
        result.setErrorCode(errorCode);
        return result;
    }

    /**
     * 失败调用时的构造方法
     *
     * @param result 结果
     * @return Result<T1, T2>
     */
    public static <T1, T2> Result<T1> fail(Result<T2> result) {
        final Result<T1> result1 = new Result<>(false);
        result1.setErrorCode(result.getErrorCode());
        result1.setMessage(result.getMessage());
        return result1;
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

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
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
