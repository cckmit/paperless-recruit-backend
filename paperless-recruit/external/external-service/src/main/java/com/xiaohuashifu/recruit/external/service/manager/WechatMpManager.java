package com.xiaohuashifu.recruit.external.service.manager;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.external.service.pojo.dto.Code2SessionDTO;

import java.util.Optional;

/**
 * 描述：微信小程序相关服务封装
 *
 * @author: xhsf
 * @create: 2020/11/20 15:53
 */
public interface WechatMpManager {
    /**
     * 通过 code 获取封装过的 Code2SessionDTO
     *
     * @param code String
     * @param app 微信小程序类别
     * @return Code2SessionDTO
     */
    Optional<Code2SessionDTO> getCode2Session(String code, AppEnum app);

    /**
     * 获取 access-token
     *
     * @param app 具体的微信小程序类型
     * @return access-token
     */
    Optional<String> getAccessToken(AppEnum app);

    /**
     * 获取新的 access-token
     * 并添加到 redis
     * 并设置过期时间
     *
     * @param app 具体的微信小程序类型
     * @return 刷新是否成功
     */
    boolean refreshAccessToken(AppEnum app);

}
