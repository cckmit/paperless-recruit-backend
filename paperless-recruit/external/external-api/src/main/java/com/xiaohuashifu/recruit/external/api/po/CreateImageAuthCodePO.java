package com.xiaohuashifu.recruit.external.api.po;

import com.xiaohuashifu.recruit.external.api.constant.ImageAuthCodeServiceConstants;

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
    @NotNull(message = "The width can't be null.")
    @Positive(message = "The width must be greater than 0.")
    @Max(value = ImageAuthCodeServiceConstants.MAX_IMAGE_AUTH_CODE_WIDTH,
            message = "The width must not be greater than "
                    + ImageAuthCodeServiceConstants.MAX_IMAGE_AUTH_CODE_WIDTH + ".")
    private Integer width;

    /**
     * 图形验证码高度
     */
    @NotNull(message = "The height can't be null.")
    @Positive(message = "The height must be greater than 0.")
    @Max(value = ImageAuthCodeServiceConstants.MAX_IMAGE_AUTH_CODE_HEIGHT,
            message = "The height must not be greater than "
                    + ImageAuthCodeServiceConstants.MAX_IMAGE_AUTH_CODE_HEIGHT + ".")
    private Integer height;

    /**
     * 验证码长度（位数）
     */
    @NotNull(message = "The length can't be null.")
    @Positive(message = "The length must be greater than 0.")
    @Max(value = ImageAuthCodeServiceConstants.MAX_IMAGE_AUTH_CODE_LENGTH,
            message = "The length must not be greater than "
                    + ImageAuthCodeServiceConstants.MAX_IMAGE_AUTH_CODE_LENGTH + ".")
    private Integer length;

    /**
     * 图形验证码有效时间，单位分钟
     */
    @NotNull(message = "The expirationTime can't be null.")
    @Positive(message = "The expirationTime must be greater than 0.")
    @Max(value = ImageAuthCodeServiceConstants.MAX_IMAGE_AUTH_CODE_EXPIRATION_TIME,
            message = "The expirationTime must not be greater than "
                    + ImageAuthCodeServiceConstants.MAX_IMAGE_AUTH_CODE_EXPIRATION_TIME + ".")
    private Integer expirationTime;

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

    public Integer getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Integer expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public String toString() {
        return "CreateImageAuthCodePO{" +
                "width=" + width +
                ", height=" + height +
                ", length=" + length +
                ", expirationTime=" + expirationTime +
                '}';
    }

    public static final class Builder {
        private Integer width;
        private Integer height;
        private Integer length;
        private Integer expirationTime;

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

        public Builder expirationTime(Integer expirationTime) {
            this.expirationTime = expirationTime;
            return this;
        }

        public CreateImageAuthCodePO build() {
            CreateImageAuthCodePO createImageAuthCodePO = new CreateImageAuthCodePO();
            createImageAuthCodePO.setWidth(width);
            createImageAuthCodePO.setHeight(height);
            createImageAuthCodePO.setLength(length);
            createImageAuthCodePO.setExpirationTime(expirationTime);
            return createImageAuthCodePO;
        }
    }

}
