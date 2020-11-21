package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.constant.App;
import com.xiaohuashifu.recruit.common.constant.Platform;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
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
 * 描述：微信小程序相关服务RPC实现
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/20 14:14
 */
@Service
public class WechatMpServiceImpl implements WechatMpService {

    @Reference
    private AuthOpenidService authOpenidService;

    private final WechatMpManager wechatMpManager;

    private final RestTemplate restTemplate;

    /**
     * 发送模板消息的url
     */
    @Value("${wechat.mp.subscribe-message-url}")
    private String subscribeMessageUrl;

    public WechatMpServiceImpl(WechatMpManager wechatMpManager, RestTemplate restTemplate) {
        this.wechatMpManager = wechatMpManager;
        this.restTemplate = restTemplate;
    }

    /**
     * 通过code获得openid
     *
     * @param code code
     * @param app 具体的微信小程序
     * @return openid
     */
    @Override
    public Result<String> getOpenid(String code, App app) {
        // 平台必须是微信小程序
        if (app.getPlatform() != Platform.WECHAT_MINI_PROGRAM) {
            return Result.fail(ErrorCode.INVALID_PARAMETER);
        }

        // 获取openid
        Optional<Code2SessionDTO> code2SessionDTOOptional = wechatMpManager.getCode2Session(code, app);
        if (code2SessionDTOOptional.isEmpty()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER);
        }
        return Result.success(code2SessionDTOOptional.get().getOpenid());
    }

    /**
     * 发送订阅消息
     *
     * @param app 微信小程序类型
     * @param userId 用于获取openid
     * @param subscribeMessageDTO 订阅消息
     * @return 发送结果
     */
    @Override
    public Result<Void> sendSubscribeMessage(App app, Long userId, SubscribeMessageDTO subscribeMessageDTO) {
        // 平台必须是微信小程序
        if (app.getPlatform() != Platform.WECHAT_MINI_PROGRAM) {
            return Result.fail(ErrorCode.INVALID_PARAMETER);
        }

        // 获取openId
        Result<String> getOpenidResult = authOpenidService.getOpenid(app, userId);
        if (!getOpenidResult.isSuccess()) {
            return Result.fail(getOpenidResult);
        }
        subscribeMessageDTO.setTouser(getOpenidResult.getData());

        // 获取access-token
        Optional<String> accessToken = wechatMpManager.getAccessToken(app);
        if (accessToken.isEmpty()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER);
        }

        // 发送消息
        String url = MessageFormat.format(subscribeMessageUrl, accessToken.get());
        ResponseEntity<WeChatMpResponseDTO> responseEntity =
                restTemplate.postForEntity(url, subscribeMessageDTO, WeChatMpResponseDTO.class);
        if (responseEntity.getBody() == null) {
            return Result.fail(ErrorCode.INTERNAL_ERROR);
        }
        if (!responseEntity.getBody().getErrcode().equals(0)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, responseEntity.getBody().getErrmsg());
        }
        return Result.success();
    }

}
