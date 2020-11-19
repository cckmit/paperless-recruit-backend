package com.xiaohuashifu.recruit.external.api.dto;

import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;
import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import com.xiaohuashifu.recruit.external.api.service.SmsService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：短信验证码，用于发送短信验证码时带的信息
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/19 13:42
 */
public class SmsAuthCodeDTO implements Serializable {
    @NotBlank
    @Phone
    private String phone;

    /**
     * 主题必须是该业务唯一的，不可以产生冲突，否则不准确
     * 用来作为缓存时key的前缀
     * 推荐格式为{服务名}:{具体业务名}
     */
    @NotBlank
    private String subject;

    /**
     * 缓存键的过期时间，单位秒
     * 推荐5或10分钟，即expiredTime=300或expiredTime=600
     * 在调用SmsService.createAndSendSmsAuthCode()时需要带上
     */
    @NotNull(groups = SmsService.CreateAndSendSmsAuthCode.class)
    @Positive
    private Long expiredTime;

    /**
     * 短信验证码
     * 在调用SmsService.checkSmsAuthCode()时需要带上
     */
    @NotBlank(groups = SmsService.CheckSmsAuthCode.class)
    @AuthCode
    private String authCode;

    /**
     * 检查成功后是否删除该键
     * 在调用SmsService.checkSmsAuthCode()时需要带上
     */
    @NotNull(groups = SmsService.CheckSmsAuthCode.class)
    private Boolean delete;


    public SmsAuthCodeDTO() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "SmsAuthCodeDTO{" +
                "phone='" + phone + '\'' +
                ", subject='" + subject + '\'' +
                ", expiredTime=" + expiredTime +
                ", authCode='" + authCode + '\'' +
                ", delete=" + delete +
                '}';
    }


    public static final class Builder {
        private String phone;
        private String subject;
        private Long expiredTime;
        private String authCode;
        private Boolean delete;

        public Builder() {
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder expiredTime(Long expiredTime) {
            this.expiredTime = expiredTime;
            return this;
        }

        public Builder authCode(String authCode) {
            this.authCode = authCode;
            return this;
        }

        public Builder delete(Boolean delete) {
            this.delete = delete;
            return this;
        }

        public SmsAuthCodeDTO build() {
            SmsAuthCodeDTO smsAuthCodeDTO = new SmsAuthCodeDTO();
            smsAuthCodeDTO.setPhone(phone);
            smsAuthCodeDTO.setSubject(subject);
            smsAuthCodeDTO.setExpiredTime(expiredTime);
            smsAuthCodeDTO.setAuthCode(authCode);
            smsAuthCodeDTO.setDelete(delete);
            return smsAuthCodeDTO;
        }
    }
}
