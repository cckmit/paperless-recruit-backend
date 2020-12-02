package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.constant.PlatformEnum;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.SubscribeMessageDTO;
import com.xiaohuashifu.recruit.external.api.service.WechatMpService;
import com.xiaohuashifu.recruit.external.service.manager.WechatMpManager;
import com.xiaohuashifu.recruit.external.service.pojo.dto.Code2SessionDTO;
import com.xiaohuashifu.recruit.external.service.pojo.dto.WeChatMpResponseDTO;
import com.xiaohuashifu.recruit.user.api.service.AuthOpenidService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Optional;

/**
 * 描述：微信小程序相关服务 RPC 实现
 *
 * @author: xhsf
 * @create: 2020/11/20 14:14
 */
@Service
public class WechatMpServiceImpl implements WechatMpService {

    @Reference
    private AuthOpenidService authOpenidService;

    private final WechatMpManager wechatMpManager;

    private final RestTemplate restTemplate;

    /**
     * 发送模板消息的 url
     */
    @Value("${wechat.mp.subscribe-message-url}")
    private String subscribeMessageUrl;

    public WechatMpServiceImpl(WechatMpManager wechatMpManager, RestTemplate restTemplate) {
        this.wechatMpManager = wechatMpManager;
        this.restTemplate = restTemplate;
    }

    /**
     * 通过 code 获得 openid
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | App 是不支持的类型 | 非法 code
     *
     * @param code code
     * @param app 具体的微信小程序
     * @return openid
     */
    @Override
    public Result<String> getOpenid(String code, AppEnum app) {
        // 平台必须是微信小程序
        if (app.getPlatform() != PlatformEnum.WECHAT_MINI_PROGRAM) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The platform must be wechat mp.");
        }

        // 获取 openid
        Optional<Code2SessionDTO> code2SessionDTOOptional = wechatMpManager.getCode2Session(code, app);
        if (code2SessionDTOOptional.isEmpty()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The code is invalid.");
        }

        return Result.success(code2SessionDTOOptional.get().getOpenid());
    }

    /**
     * 发送订阅消息
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | App 是不支持的类型 | 用户还未绑定此 app
     *              InternalError: 服务器错误 | access-token 获取失败 | 发送订阅消息出错
     *              UnknownError: 未知错误 | 发送订阅消息时微信小程序报的错误，具体查看错误消息
     *
     * @param app 微信小程序类型
     * @param userId 用于获取 openid
     * @param subscribeMessageDTO 订阅消息
     * @return 发送结果
     */
    @Override
    public Result<Void> sendSubscribeMessage(AppEnum app, Long userId, SubscribeMessageDTO subscribeMessageDTO) {
        // 平台必须是微信小程序
        if (app.getPlatform() != PlatformEnum.WECHAT_MINI_PROGRAM) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The platform must be wechat mp.");
        }

        // 获取 openId
        Result<String> getOpenidResult = authOpenidService.getOpenid(app, userId);
        if (!getOpenidResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The user has not been bound this app.");
        }
        subscribeMessageDTO.setTouser(getOpenidResult.getData());

        // 获取 access-token
        Optional<String> accessToken = wechatMpManager.getAccessToken(app);
        if (accessToken.isEmpty()) {
            return Result.fail(ErrorCodeEnum.INTERNAL_ERROR);
        }

        // 发送消息
        String url = MessageFormat.format(subscribeMessageUrl, accessToken.get());
        ResponseEntity<WeChatMpResponseDTO> responseEntity =
                restTemplate.postForEntity(url, subscribeMessageDTO, WeChatMpResponseDTO.class);
        if (responseEntity.getBody() == null) {
            return Result.fail(ErrorCodeEnum.INTERNAL_ERROR, "Send subscribe message failed.");
        }
        if (!responseEntity.getBody().getErrcode().equals(0)) {
            return Result.fail(ErrorCodeEnum.UNKNOWN_ERROR, responseEntity.getBody().getErrmsg());
        }
        return Result.success();
    }

}
