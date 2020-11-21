package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.constant.App;
import com.xiaohuashifu.recruit.common.constant.Platform;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.MessageTemplateDTO;
import com.xiaohuashifu.recruit.external.api.service.WechatMpService;
import com.xiaohuashifu.recruit.external.service.manager.WechatMpManager;
import com.xiaohuashifu.recruit.external.service.pojo.dto.Code2SessionDTO;
import org.apache.dubbo.config.annotation.Service;

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

    private final WechatMpManager wechatMpManager;

    public WechatMpServiceImpl(WechatMpManager wechatMpManager) {
        this.wechatMpManager = wechatMpManager;
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

    @Override
    public Result<Void> sendTemplateMessage(MessageTemplateDTO messageTemplate) {
        return null;
    }


}
