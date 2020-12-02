package com.xiaohuashifu.recruit.user.api.service;

import com.xiaohuashifu.recruit.common.constant.App;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.AuthOpenidDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：AuthOpenid相关服务，用于接入第三方平台的身份认证
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/20 19:29
 */
public interface AuthOpenidService {

    /**
     * 用于微信小程序用户绑定AuthOpenid
     * 会通过code获取openid
     * 保存时会对openid进行加密
     *
     * @errorCode InvalidParameter: 请求参数格式错误|不支持的App类型|非法code|对应编号的用户不存在
     *              OperationConflict: 用户已经绑定在此App上
     *
     * @param userId 用户编号
     * @param app 具体的微信小程序，只支持SCAU_RECRUIT_INTERVIEWEE_MP和SCAU_RECRUIT_INTERVIEWER_MP两种类型的绑定
     * @param code 微信小程序wx.login()接口的返回结果
     * @return AuthOpenidDTO
     */
    default Result<AuthOpenidDTO> bindAuthOpenidForWechatMp(@NotNull @Positive Long userId, @NotNull App app,
                                                            @NotBlank @Size(min = 32, max = 32) String code) {
        throw new UnsupportedOperationException();
    }

    /**
     * 用于微信小程序用户检查AuthOpenid
     * 会通过code获取openid
     * 可以用于快捷登录时使用
     * 该接口调用成功即可证明用户身份
     *
     * @errorCode InvalidParameter: 请求参数格式错误|不支持的App类型|非法code
     *              InvalidParameter.NotExist: 该用户还未绑定到此App
     *
     * @param app 具体的微信小程序
     * @param code 微信小程序wx.login()接口的返回结果
     * @return AuthOpenidDTO
     */
    default Result<AuthOpenidDTO> checkAuthOpenidForWechatMp(@NotNull App app,
                                                             @NotBlank @Size(min = 32, max = 32) String code) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取openid
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到对应的openid
     *
     * @param userId 用户编号
     * @param app 具体的微信小程序
     * @return openid 若参数错误的情况下，返回null
     */
    default Result<String> getOpenid(@NotNull App app, @NotNull @Positive Long userId) {
        throw new UnsupportedOperationException();
    }

}
