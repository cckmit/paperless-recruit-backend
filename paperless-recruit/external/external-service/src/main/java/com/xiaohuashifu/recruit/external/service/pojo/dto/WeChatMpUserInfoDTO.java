package com.xiaohuashifu.recruit.external.service.pojo.dto;

import com.xiaohuashifu.recruit.common.constant.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：解密微信小程序的 encryptedData 后得到的数据
 *
 * @author xhsf
 * @create 2020/12/4 20:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeChatMpUserInfoDTO {
    private String openId;
    private String nickName;
    private GenderEnum gender;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String unionId;
    private WeChatMpWatermarkDTO watermark;
}
