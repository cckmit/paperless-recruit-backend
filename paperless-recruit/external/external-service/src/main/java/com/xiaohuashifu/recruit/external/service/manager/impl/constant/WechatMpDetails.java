package com.xiaohuashifu.recruit.external.service.manager.impl.constant;

import com.xiaohuashifu.recruit.external.api.service.constant.WechatMp;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述: 微信小程序相关信息
 *
 * @author xhsf
 * @email 827032783@qq.com
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
     * 微信小程序appId
     */
    private List<String> appIds;

    /**
     * 微信小程序secret
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
     * 通过WechatMp类型获取appId
     *
     * @param wechatMp WechatMp
     * @return appId
     */
    public String getAppId(WechatMp wechatMp) {
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
     * 通过WechatMp类型获取secret
     *
     * @param wechatMp WechatMp
     * @return secret
     */
    public String getSecret(WechatMp wechatMp) {
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
