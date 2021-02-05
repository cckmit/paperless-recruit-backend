package com.xiaohuashifu.recruit.user.api.service;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
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
     * @param userId 用户编号
     * @param app 具体的微信小程序，只支持 SCAU_RECRUIT_INTERVIEWEE_MP 和 SCAU_RECRUIT_INTERVIEWER_MP 两种类型的绑定
     * @param code 微信小程序 wx.login() 接口的返回结果
     * @return AuthOpenIdDTO
     */
    AuthOpenIdDTO bindAuthOpenIdForWeChatMp(
            @NotNull @Positive Long userId,
            @NotNull AppEnum app,
            @NotBlank @Size(max = AuthOpenIdServiceConstants.WECHAT_MP_CODE_LENGTH,
                    min = AuthOpenIdServiceConstants.WECHAT_MP_CODE_LENGTH) String code) throws ServiceException;

    /**
     * 获取 AuthOpenId
     *
     * @param id AuthOpenId的编号
     * @return AuthOpenIdDTO
     */
    AuthOpenIdDTO getAuthOpenId(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 用于微信小程序用户检查 AuthOpenId
     * 会通过 code 获取 openId
     * 可以用于快捷登录时使用
     * 该接口调用成功即可证明用户身份
     *
     * @param app 具体的微信小程序
     * @param code 微信小程序 wx.login() 接口的返回结果
     * @return AuthOpenIdDTOauthOpenId
     */
    AuthOpenIdDTO checkAuthOpenIdForWeChatMp(
            @NotNull AppEnum app,
            @NotBlank @Size(max = AuthOpenIdServiceConstants.WECHAT_MP_CODE_LENGTH,
                    min = AuthOpenIdServiceConstants.WECHAT_MP_CODE_LENGTH) String code);

    /**
     * 获取 openId
     *
     * @param userId 用户编号
     * @param app 具体的微信小程序
     * @return openId 若参数错误的情况下，返回 null
     */
    String getOpenId(@NotNull AppEnum app, @NotNull @Positive Long userId);

}
