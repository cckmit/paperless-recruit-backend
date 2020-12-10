package com.xiaohuashifu.recruit.external.api.po;

import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import com.xiaohuashifu.recruit.external.api.constant.SmsServiceConstants;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 描述：创建并发送短信验证码参数对象
 *
 * @author xhsf
 * @create 2020/12/9 20:11
 */
public class CreateAndSendSmsAuthCodePO implements Serializable {

    @NotBlank(message = "The phone can't be blank.")
    @Phone
    private String phone;

    /**
     * 主题必须是该业务唯一的，不可以产生冲突，否则不准确
     * 用来作为缓存时 key 的前缀
     * 推荐格式为{服务名}:{具体业务名}
     */
    @NotBlank(message = "The subject can't be blank.")
    @Size(max = SmsServiceConstants.MAX_SMS_AUTH_CODE_SUBJECT_LENGTH,
            message = "The length of subject must not be greater than "
                    + SmsServiceConstants.MAX_SMS_AUTH_CODE_SUBJECT_LENGTH + ".")
    private String subject;

    /**
     * 缓存键的过期时间，单位分钟
     * 推荐5或10分钟
     */
    @NotNull(message = "The expirationTime can't be null.")
    @Positive(message = "The expirationTime must be greater than 0.")
    @Max(value = SmsServiceConstants.MAX_SMS_AUTH_CODE_EXPIRATION_TIME,
            message = "The expirationTime must not be greater than "
                    + SmsServiceConstants.MAX_SMS_AUTH_CODE_EXPIRATION_TIME + ".")
    private Integer expirationTime;

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

    public Integer getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Integer expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public String toString() {
        return "CreateAndSendSmsAuthCodePO{" +
                "phone='" + phone + '\'' +
                ", subject='" + subject + '\'' +
                ", expirationTime=" + expirationTime +
                '}';
    }

    public static final class Builder {
        private String phone;
        private String subject;
        private Integer expirationTime;

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder expirationTime(Integer expirationTime) {
            this.expirationTime = expirationTime;
            return this;
        }

        public CreateAndSendSmsAuthCodePO build() {
            CreateAndSendSmsAuthCodePO createAndSendSmsAuthCodePO = new CreateAndSendSmsAuthCodePO();
            createAndSendSmsAuthCodePO.setPhone(phone);
            createAndSendSmsAuthCodePO.setSubject(subject);
            createAndSendSmsAuthCodePO.setExpirationTime(expirationTime);
            return createAndSendSmsAuthCodePO;
        }
    }

}
