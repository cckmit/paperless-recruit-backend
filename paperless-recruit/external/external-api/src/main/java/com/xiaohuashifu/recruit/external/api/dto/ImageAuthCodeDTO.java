package com.xiaohuashifu.recruit.external.api.dto;

import java.io.Serializable;

/**
 * 描述：图形验证码传输对象
 *
 * @author: xhsf
 * @create: 2020/11/22 15:44
 */
public class ImageAuthCodeDTO implements Serializable {
    /**
     * 唯一标识一个图形验证码
     * 格式为：随机字符串+序列编号
     */
    private String id;

    /**
     * 图形验证码的 Base64 编码字符串
     */
    private String authCode;

    public ImageAuthCodeDTO(String id, String authCode) {
        this.id = id;
        this.authCode = authCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Override
    public String toString() {
        return "ImageAuthCodeDTO{" +
                "id='" + id + '\'' +
                ", authCode='" + authCode + '\'' +
                '}';
    }

}
