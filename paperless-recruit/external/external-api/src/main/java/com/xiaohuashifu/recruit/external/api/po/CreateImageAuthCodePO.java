package com.xiaohuashifu.recruit.external.api.po;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：创建图形验证码参数对象
 *
 * @author xhsf
 * @create 2020/12/9 19:57
 */
public class CreateImageAuthCodePO implements Serializable {

    /**
     * 图形验证码宽度
     */
    @NotNull
    @Positive
    private Integer width;

    /**
     * 图形验证码高度
     */
    @NotNull
    @Positive
    private Integer height;

    /**
     * 验证码长度（位数）
     */
    @NotNull
    @Positive
    @Max(10)
    private Integer length;

    /**
     * 图形验证码有效时间，单位分钟
     */
    @NotNull
    @Positive
    @Max(10)
    private Integer expiredTime;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

    @Override
    public String toString() {
        return "CreateImageAuthCodePO{" +
                "width=" + width +
                ", height=" + height +
                ", length=" + length +
                ", expiredTime=" + expiredTime +
                '}';
    }

    public static final class Builder {
        private Integer width;
        private Integer height;
        private Integer length;
        private Integer expiredTime;

        public Builder width(Integer width) {
            this.width = width;
            return this;
        }

        public Builder height(Integer height) {
            this.height = height;
            return this;
        }

        public Builder length(Integer length) {
            this.length = length;
            return this;
        }

        public Builder expiredTime(Integer expiredTime) {
            this.expiredTime = expiredTime;
            return this;
        }

        public CreateImageAuthCodePO build() {
            CreateImageAuthCodePO createImageAuthCodePO = new CreateImageAuthCodePO();
            createImageAuthCodePO.setWidth(width);
            createImageAuthCodePO.setHeight(height);
            createImageAuthCodePO.setLength(length);
            createImageAuthCodePO.setExpiredTime(expiredTime);
            return createImageAuthCodePO;
        }
    }
}
