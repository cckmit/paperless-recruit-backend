package com.xiaohuashifu.recruit.external.service.manager.impl.constant;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述: 微信小程序相关信息
 *
 * @author xhsf
 * @create 2019-08-13 19:23
 */
@Component
@ConfigurationProperties("wechat.mp.details")
public class WechatMpDetails {

    /**
     * 微信小程序名字
     */
    private List<String> names;

    /**
     * 微信小程序 appId
     */
    private List<String> appIds;

    /**
     * 微信小程序 secret
     */
    private List<String> secrets;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getAppIds() {
        return appIds;
    }

    /**
     * 通过 WechatMp 类型获取 appId
     *
     * @param wechatMp 具体的微信小程序
     * @return appId
     */
    public String getAppId(AppEnum wechatMp) {
        int index = names.indexOf(wechatMp.name());
        if (index == -1) {
            return null;
        }
        return appIds.get(index);
    }

    public void setAppIds(List<String> appIds) {
        this.appIds = appIds;
    }

    public List<String> getSecrets() {
        return secrets;
    }

    /**
     * 通过 WechatMp 类型获取 secret
     *
     * @param wechatMp 具体的微信小程序
     * @return secret
     */
    public String getSecret(AppEnum wechatMp) {
        int index = names.indexOf(wechatMp.name());
        if (index == -1) {
            return null;
        }
        return secrets.get(index);
    }

    public void setSecrets(List<String> secrets) {
        this.secrets = secrets;
    }
}
