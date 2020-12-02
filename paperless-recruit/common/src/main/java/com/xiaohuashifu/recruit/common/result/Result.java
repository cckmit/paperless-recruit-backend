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
    private final String errorCode;

    /**
     * 错误简短信息
     */
    private final String errorMessage;

    private static final Result<Void> SUCCESS = new Result<>(true, null, null, null);

    private Result(Boolean success, T data, String errorCode, String errorMessage) {
        this.success = success;
        this.data = data;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
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
     * @param errorMessage 错误简短信息
     * @return Result<T>
     */
    public static <T> Result<T> fail(ErrorCodeEnum errorCode, String errorMessage) {
        return new Result<>(false, null, errorCode.getCode(), errorMessage);
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
        return new Result<>(false, null, errorCode.getCode(), MessageFormat.format(format, args));
    }

    /**
     * 失败调用时的构造方法
     *
     * @param errorCode 错误码
     * @return Result<T>
     */
    public static <T> Result<T> fail(ErrorCodeEnum errorCode) {
        return new Result<>(false, null, errorCode.getCode(), null);
    }

    public Boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", data=" + data +
                ", errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
