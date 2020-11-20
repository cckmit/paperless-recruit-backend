package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.MessageTemplateDTO;
import com.xiaohuashifu.recruit.external.api.service.WechatMpService;
import com.xiaohuashifu.recruit.external.api.service.constant.WechatMp;
import com.xiaohuashifu.recruit.external.service.manager.WechatMpManager;
import com.xiaohuashifu.recruit.external.service.pojo.dto.Code2SessionDTO;
import org.apache.dubbo.config.annotation.Service;

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
     * @param wechatMp 具体的微信小程序
     * @return openid
     */
    @Override
    public Result<String> getOpenid(String code, WechatMp wechatMp) {
        Code2SessionDTO code2SessionDTO = wechatMpManager.getCode2Session(code, wechatMp);
        if (code2SessionDTO.getOpenid() == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER);
        }
        return Result.success(code2SessionDTO.getOpenid());
    }

    @Override
    public Result<Void> sendTemplateMessage(MessageTemplateDTO messageTemplate) {
        return null;
    }


}
