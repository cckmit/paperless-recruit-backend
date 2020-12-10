package com.xiaohuashifu.recruit.user.api.service;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.constant.AuthOpenIdServiceConstants;
import com.xiaohuashifu.recruit.user.api.dto.AuthOpenIdDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：AuthOpenId 相关服务，用于接入第三方平台的身份认证
 *
 * @author: xhsf
 * @create: 2020/11/20 19:29
 */
public interface AuthOpenIdService {

    /**
     * 用于微信小程序用户绑定 AuthOpenId
     * 会通过 code 获取 openId
     * 保存时会对 openId 进行加密
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 不支持的 App 类型 | 非法 code | 对应编号的用户不存在
     *              OperationConflict: 用户已经绑定在此 App 上
     *
     * @param userId 用户编号
     * @param app 具体的微信小程序，只支持 SCAU_RECRUIT_INTERVIEWEE_MP 和 SCAU_RECRUIT_INTERVIEWER_MP 两种类型的绑定
     * @param code 微信小程序 wx.login() 接口的返回结果
     * @return AuthOpenIdDTO
     */
    Result<AuthOpenIdDTO> bindAuthOpenIdForWeChatMp(
            @NotNull(message = "The userId can't be null.")
            @Positive(message = "The userId must be greater than 0.") Long userId,
            @NotNull(message = "The app can't be null.") AppEnum app,
            @NotBlank(message = "The code can't be blank.")
            @Size(max = AuthOpenIdServiceConstants.WECHAT_MP_CODE_LENGTH,
                    min = AuthOpenIdServiceConstants.WECHAT_MP_CODE_LENGTH,
                    message = "The length of code must be equal to "
                            + AuthOpenIdServiceConstants.WECHAT_MP_CODE_LENGTH + ".") String code);

    /**
     * 用于微信小程序用户检查 AuthOpenId
     * 会通过 code 获取 openId
     * 可以用于快捷登录时使用
     * 该接口调用成功即可证明用户身份
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 不支持的 App 类型 | 非法 code
     *              InvalidParameter.NotExist: 该用户还未绑定到此 App
     *
     * @param app 具体的微信小程序
     * @param code 微信小程序 wx.login() 接口的返回结果
     * @return AuthOpenIdDTOauthOpenId
     */
    Result<AuthOpenIdDTO> checkAuthOpenIdForWeChatMp(
            @NotNull(message = "The app can't be null.") AppEnum app,
            @NotBlank(message = "The code can't be blank.")
            @Size(max = AuthOpenIdServiceConstants.WECHAT_MP_CODE_LENGTH,
                    min = AuthOpenIdServiceConstants.WECHAT_MP_CODE_LENGTH,
                    message = "The length of code must be equal to "
                            + AuthOpenIdServiceConstants.WECHAT_MP_CODE_LENGTH + ".") String code);

    /**
     * 获取 openId
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到对应的 openId
     *
     * @param userId 用户编号
     * @param app 具体的微信小程序
     * @return openId 若参数错误的情况下，返回 null
     */
    Result<String> getOpenId(@NotNull(message = "The app can't be null.") AppEnum app,
                             @NotNull(message = "The userId can't be null.")
                             @Positive(message = "The userId must be greater than 0.")  Long userId);

}
