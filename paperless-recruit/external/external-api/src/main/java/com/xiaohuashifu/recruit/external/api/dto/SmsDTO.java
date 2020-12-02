package com.xiaohuashifu.recruit.external.api.dto;

import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import com.xiaohuashifu.recruit.common.validator.annotation.Sms;
import com.xiaohuashifu.recruit.external.api.service.SmsService;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 描述：手机短信
 *
 * @author: xhsf
 * @create: 2020/11/11 15:19
 */
public class SmsDTO implements Serializable {
    @NotBlank(groups = SmsService.SendSms.class)
    @Phone
    private String phone;

    @NotBlank(groups = SmsService.SendSms.class)
    @Sms
    private String message;

    public SmsDTO() {}

    public SmsDTO(String phone, String message) {
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
        return "SmsDTO{" +
                "phone='" + phone + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public static final class Builder {
        private String phone;
        private String message;

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public SmsDTO build() {
            SmsDTO phoneMessageDTO = new SmsDTO();
            phoneMessageDTO.setPhone(phone);
            phoneMessageDTO.setMessage(message);
            return phoneMessageDTO;
        }
    }

}
