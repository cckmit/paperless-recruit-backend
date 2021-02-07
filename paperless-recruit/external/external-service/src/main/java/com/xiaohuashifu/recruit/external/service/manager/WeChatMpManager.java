package com.xiaohuashifu.recruit.external.service.manager;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.exception.InternalServiceException;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.external.api.request.SendWeChatMpSubscribeMessageRequest;
import com.xiaohuashifu.recruit.external.service.pojo.dto.WeChatMpSessionDTO;
import com.xiaohuashifu.recruit.external.service.pojo.dto.WeChatMpUserInfoDTO;

/**
 * 描述：微信小程序相关服务封装
 *
 * @author: xhsf
 * @create: 2020/11/20 15:53
 */
public interface WeChatMpManager {

    /**
     * 通过 code 获取封装过的 WeChatMpSessionDTO
     *
     * @param code String
     * @param app 微信小程序类别
     * @return WeChatMpSessionDTO
     */
    WeChatMpSessionDTO getSessionByCode(String code, AppEnum app) throws InternalServiceException;

    /**
     * 获取 access-token
     *
     * @param app 具体的微信小程序类型
     * @return access-token
     */
    String getAccessToken(AppEnum app) throws NotFoundServiceException;

    /**
     * 解密 encryptedData 获取用户信息
     *
     * @param encryptedData wx.getUserInfo() 返回值
     * @param iv wx.getUserInfo() 返回值
     * @param code wx.login() 的返回值
     * @param app 具体的微信小程序类型
     * @return WeChatMpUserInfoDTO
     */
    WeChatMpUserInfoDTO getUserInfo(String encryptedData, String iv, String code, AppEnum app)
            throws InternalServiceException;

    /**
     * 发送模板消息
     *
     * @param app 具体的微信小程序类型
     * @param openId 目标用户 openId
     * @param request SendWeChatMpSubscribeMessageRequest
     */
    void sendSubscribeMessage(AppEnum app, String openId, SendWeChatMpSubscribeMessageRequest request)
            throws InternalServiceException;

    /**
     * 获取新的 access-token
     * 并添加到 redis
     * 并设置过期时间
     *
     * @param app 具体的微信小程序类型
     * @return AccessToken
     */
    String refreshAccessToken(AppEnum app) throws InternalServiceException;

}
