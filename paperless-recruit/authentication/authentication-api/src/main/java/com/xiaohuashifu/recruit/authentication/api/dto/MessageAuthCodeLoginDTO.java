package com.xiaohuashifu.recruit.authentication.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：手机号码+短信验证码登录的DTO
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 15:45
 */
public class MessageAuthCodeLoginDTO implements Serializable {

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 短信验证码
     */
    private String authCode;

    /**
     * 短信的内容
     */
    private String message;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    public MessageAuthCodeLoginDTO() {
    }

    public MessageAuthCodeLoginDTO(String phone, String authCode, String message, LocalDateTime expireTime) {
        this.phone = phone;
        this.authCode = authCode;
        this.message = message;
        this.expireTime = expireTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "PhoneAuthCodeLoginDTO{" +
                "phone='" + phone + '\'' +
                ", authCode='" + authCode + '\'' +
                ", message='" + message + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }


    public static final class Builder {
        private String phone;
        private String authCode;
        private String message;
        private LocalDateTime expireTime;

        public Builder() {
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder authCode(String authCode) {
            this.authCode = authCode;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder expireTime(LocalDateTime expireTime) {
            this.expireTime = expireTime;
            return this;
        }

        public MessageAuthCodeLoginDTO build() {
            MessageAuthCodeLoginDTO phoneAuthCodeLoginDTO = new MessageAuthCodeLoginDTO();
            phoneAuthCodeLoginDTO.setPhone(phone);
            phoneAuthCodeLoginDTO.setAuthCode(authCode);
            phoneAuthCodeLoginDTO.setMessage(message);
            phoneAuthCodeLoginDTO.setExpireTime(expireTime);
            return phoneAuthCodeLoginDTO;
        }
    }
}
