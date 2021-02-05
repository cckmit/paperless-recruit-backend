package com.xiaohuashifu.recruit.external.api.request;

import com.xiaohuashifu.recruit.external.api.constant.ImageAuthCodeServiceConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：创建图形验证码请求
 *
 * @author xhsf
 * @create 2020/12/9 19:57
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateImageAuthCodeRequest implements Serializable {

    /**
     * 图形验证码宽度
     */
    @NotNull
    @Positive
    @Max(value = ImageAuthCodeServiceConstants.MAX_IMAGE_AUTH_CODE_WIDTH)
    private Integer width;

    /**
     * 图形验证码高度
     */
    @NotNull
    @Positive
    @Max(value = ImageAuthCodeServiceConstants.MAX_IMAGE_AUTH_CODE_HEIGHT)
    private Integer height;

    /**
     * 验证码长度（位数）
     */
    @NotNull
    @Positive
    @Max(value = ImageAuthCodeServiceConstants.MAX_IMAGE_AUTH_CODE_LENGTH)
    private Integer length;

    /**
     * 图形验证码有效时间，单位分钟
     */
    @NotNull
    @Positive
    @Max(value = ImageAuthCodeServiceConstants.MAX_IMAGE_AUTH_CODE_EXPIRATION_TIME)
    private Integer expirationTime;

}
