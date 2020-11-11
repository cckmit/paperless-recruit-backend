package com.xiaohuashifu.recruit.external.api.dto;

import java.io.Serializable;

/**
 * 描述：手机短信
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 15:19
 */
public class PhoneMessageDTO implements Serializable {
    private String phone;
    private String message;

    public PhoneMessageDTO() {
    }

    public PhoneMessageDTO(String phone, String message) {
        this.phone = phone;
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PhoneMessageDTO{" +
                "phone='" + phone + '\'' +
                ", message='" + message + '\'' +
                '}';
    }


    public static final class Builder {
        private String phone;
        private String message;

        public Builder() {
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public PhoneMessageDTO build() {
            PhoneMessageDTO phoneMessageDTO = new PhoneMessageDTO();
            phoneMessageDTO.setPhone(phone);
            phoneMessageDTO.setMessage(message);
            return phoneMessageDTO;
        }
    }
}
