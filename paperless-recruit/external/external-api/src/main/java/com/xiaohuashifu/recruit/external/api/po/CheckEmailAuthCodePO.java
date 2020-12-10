package com.xiaohuashifu.recruit.external.api.po;

import com.xiaohuashifu.recruit.common.validator.annotation.AuthCode;
import com.xiaohuashifu.recruit.external.api.constant.EmailServiceConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：检查邮件验证码的参数对象
 *
 * @author: xhsf
 * @create: 2020/11/19 13:42
 */
public class CheckEmailAuthCodePO implements Serializable {

    /**
     * 邮箱
     */
    @NotBlank(message = "The email can't be blank.")
    @Email(message = "The email format error.")
    private String email;

    /**
     * 主题必须是该业务唯一的，不可以产生冲突，否则不准确
     * 用来作为缓存时 key 的前缀
     * 推荐格式为{服务名}:{具体业务名}
     */
    @NotBlank(message = "The subject can't be blank.")
    @Size(max = EmailServiceConstants.MAX_EMAIL_AUTH_CODE_SUBJECT_LENGTH,
            message = "The length of subject must not be greater than "
                    + EmailServiceConstants.MAX_EMAIL_AUTH_CODE_SUBJECT_LENGTH + ".")
    private String subject;

    /**
     * 邮箱验证码
     */
    @NotBlank(message = "The authCode can't be blank.")
    @AuthCode
    private String authCode;

    /**
     * 检查成功后是否删除该键
     */
    @NotNull(message = "The delete can't be null.")
    private Boolean delete;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return "CheckEmailAuthCodePO{" +
                "email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", authCode='" + authCode + '\'' +
                ", delete=" + delete +
                '}';
    }

    public static final class Builder {
        private String email;
        private String subject;
        private String authCode;
        private Boolean delete;

        public Builder email(String email) {
            this.email = email;
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

        public CheckEmailAuthCodePO build() {
            CheckEmailAuthCodePO checkEmailAuthCodePO = new CheckEmailAuthCodePO();
            checkEmailAuthCodePO.setEmail(email);
            checkEmailAuthCodePO.setSubject(subject);
            checkEmailAuthCodePO.setAuthCode(authCode);
            checkEmailAuthCodePO.setDelete(delete);
            return checkEmailAuthCodePO;
        }
    }

}
