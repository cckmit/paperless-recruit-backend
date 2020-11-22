package com.xiaohuashifu.recruit.external.api.dto;

import com.xiaohuashifu.recruit.external.api.service.ImageAuthCodeService;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：图形验证码传输类型
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/22 15:44
 */
public class ImageAuthCodeDTO implements Serializable {
    /**
     * 唯一标识一个图形验证码
     * 格式为：随机字符串+序列编号
     */
    private String id;

    /**
     * 图形验证码的Base64编码字符串
     */
    private String authCode;

    /**
     * 图形验证码宽度
     */
    @NotNull(groups = ImageAuthCodeService.CreateImageAuthCode.class)
    @Positive
    private Integer width;

    /**
     * 图形验证码高度
     */
    @NotNull(groups = ImageAuthCodeService.CreateImageAuthCode.class)
    @Positive
    private Integer height;

    /**
     * 验证码长度（位数）
     */
    @NotNull(groups = ImageAuthCodeService.CreateImageAuthCode.class)
    @Positive
    @Max(10)
    private Integer length;

    /**
     * 图形验证码有效时间，单位分钟
     */
    @NotNull(groups = ImageAuthCodeService.CreateImageAuthCode.class)
    @Positive
    @Max(10)
    private Integer expiredTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

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
        return "ImageAuthCodeDTO{" +
                "id='" + id + '\'' +
                ", authCode='" + authCode + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", length=" + length +
                ", expiredTime=" + expiredTime +
                '}';
    }


    public static final class Builder {
        private String id;
        private String authCode;
        private Integer width;
        private Integer height;
        private Integer length;
        private Integer expiredTime;

        public Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder authCode(String authCode) {
            this.authCode = authCode;
            return this;
        }

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

        public ImageAuthCodeDTO build() {
            ImageAuthCodeDTO imageAuthCodeDTO = new ImageAuthCodeDTO();
            imageAuthCodeDTO.setId(id);
            imageAuthCodeDTO.setAuthCode(authCode);
            imageAuthCodeDTO.setWidth(width);
            imageAuthCodeDTO.setHeight(height);
            imageAuthCodeDTO.setLength(length);
            imageAuthCodeDTO.setExpiredTime(expiredTime);
            return imageAuthCodeDTO;
        }
    }
}
