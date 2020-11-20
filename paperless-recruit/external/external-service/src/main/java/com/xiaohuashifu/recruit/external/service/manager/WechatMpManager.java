package com.xiaohuashifu.recruit.external.service.manager;

import com.xiaohuashifu.recruit.external.api.service.constant.WechatMp;
import com.xiaohuashifu.recruit.external.service.pojo.dto.Code2SessionDTO;

/**
 * 描述：微信小程序相关服务封装
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/20 15:53
 */
public interface WechatMpManager {
    /**
     * 通过code获取封装过的Code2SessionDTO
     * @param code String
     * @param wechatMp 小程序类别
     * @return Code2SessionDTO
     */
    Code2SessionDTO getCode2Session(String code, WechatMp wechatMp);
}
