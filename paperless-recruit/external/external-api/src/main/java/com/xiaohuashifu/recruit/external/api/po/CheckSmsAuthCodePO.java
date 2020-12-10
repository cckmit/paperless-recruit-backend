package com.xiaohuashifu.recruit.external.api.po;

import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;
import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import com.xiaohuashifu.recruit.external.api.constant.SmsServiceConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：检查短信验证码参数对象
 *
 * @author xhsf
 * @create 2020/12/9 20:19
 */
public class CheckSmsAuthCodePO implements Serializable {

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
     * 短信验证码
     */
    @NotBlank(message = "The authCode can't be blank.")
    @AuthCode
    private String authCode;

    /**
     * 检查成功后是否删除该键
     */
    @NotNull(message = "The delete can't be null.")
    private Boolean delete;

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
        return "CheckSmsAuthCodePO{" +
                "phone='" + phone + '\'' +
                ", subject='" + subject + '\'' +
                ", authCode='" + authCode + '\'' +
                ", delete=" + delete +
                '}';
    }

    public static final class Builder {
        private String phone;
        private String subject;
        private String authCode;
        private Boolean delete;

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
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

        public CheckSmsAuthCodePO build() {
            CheckSmsAuthCodePO checkSmsAuthCodePO = new CheckSmsAuthCodePO();
            checkSmsAuthCodePO.setPhone(phone);
            checkSmsAuthCodePO.setSubject(subject);
            checkSmsAuthCodePO.setAuthCode(authCode);
            checkSmsAuthCodePO.setDelete(delete);
            return checkSmsAuthCodePO;
        }
    }

}
