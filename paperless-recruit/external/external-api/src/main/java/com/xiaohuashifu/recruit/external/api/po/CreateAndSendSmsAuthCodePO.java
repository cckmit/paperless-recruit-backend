package com.xiaohuashifu.recruit.external.api.po;

import com.xiaohuashifu.recruit.common.validator.annotation.Phone;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：创建并发送短信验证码参数对象
 *
 * @author xhsf
 * @create 2020/12/9 20:11
 */
public class CreateAndSendSmsAuthCodePO implements Serializable {

    @NotBlank
    @Phone
    private String phone;

    /**
     * 主题必须是该业务唯一的，不可以产生冲突，否则不准确
     * 用来作为缓存时 key 的前缀
     * 推荐格式为{服务名}:{具体业务名}
     */
    @NotBlank
    private String subject;

    /**
     * 缓存键的过期时间，单位分钟
     * 推荐5或10分钟
     */
    @NotNull
    @Positive
    @Max(10)
    private Integer expiredTime;

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

    public Integer getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public String toString() {
        return "CreateAndSendSmsAuthCodePO{" +
                "phone='" + phone + '\'' +
                ", subject='" + subject + '\'' +
                ", expiredTime=" + expiredTime +
                '}';
    }

    public static final class Builder {
        private String phone;
        private String subject;
        private Integer expiredTime;

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder expiredTime(Integer expiredTime) {
            this.expiredTime = expiredTime;
            return this;
        }

        public CreateAndSendSmsAuthCodePO build() {
            CreateAndSendSmsAuthCodePO createAndSendSmsAuthCodePO = new CreateAndSendSmsAuthCodePO();
            createAndSendSmsAuthCodePO.setPhone(phone);
            createAndSendSmsAuthCodePO.setSubject(subject);
            createAndSendSmsAuthCodePO.setExpiredTime(expiredTime);
            return createAndSendSmsAuthCodePO;
        }
    }
}
