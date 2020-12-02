package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.ImageAuthCodeDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 描述：图形验证码服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/22 15:36
 */
public interface ImageAuthCodeService {

    @interface CreateImageAuthCode {}
    /**
     * 创建图形验证码
     * 会把验证码缓存，可用通过checkImageAuthCode检查是否通过校验
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param imageAuthCodeDTO ImageAuthCodeDTO
     * @return ImageAuthCodeDTO
     */
    default Result<ImageAuthCodeDTO> createImageAuthCode(@NotNull ImageAuthCodeDTO imageAuthCodeDTO) {
        throw new UnsupportedOperationException();
    }

    /**
     * 校验验证码
     * 会从缓存读取验证码进行校验
     * 该接口不管校验是否通过都会删除缓存里的验证码
     * 即验证码只能进行一次校验（进行一次校验后即失效）
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.AuthCode.Incorrect: 验证码错误
     *              InvalidParameter.AuthCode.NotExist: 验证码不存在，可能是因为过期
     *
     * @param id 图形验证码编号
     * @param authCode 用户输入的验证码字符串
     * @return 校验结果
     */
    default Result<Void> checkImageAuthCode(@NotBlank String id, @NotBlank String authCode) {
        throw new UnsupportedOperationException();
    }

}
