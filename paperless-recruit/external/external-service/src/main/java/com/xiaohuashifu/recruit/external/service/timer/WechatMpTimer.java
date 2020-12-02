package com.xiaohuashifu.recruit.external.service.timer;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.constant.PlatformEnum;
import com.xiaohuashifu.recruit.external.service.manager.WechatMpManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 描述：微信小程序定时器
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/21 18:50
 */
@Configuration
@EnableScheduling
public class WechatMpTimer {

    private static final Logger logger = LoggerFactory.getLogger(WechatMpTimer.class);

    private final WechatMpManager wechatMpManager;

    public WechatMpTimer(WechatMpManager wechatMpManager) {
        this.wechatMpManager = wechatMpManager;
    }

    /**
     * 刷新微信小程序AccessToken定时任务
     * 每小时刷新一次access-token
     * 如果刷新失败则重复获取10次
     * 如果还失败则报警处理
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 3600000)
    public void refreshAccessTokenTask() {
        for (AppEnum app : AppEnum.values()) {
            if (app.getPlatform() == PlatformEnum.WECHAT_MINI_PROGRAM) {
                for (int i = 0; i < 10; i++) {
                    if (wechatMpManager.refreshAccessToken(app)) {
                        break;
                    }
                    if (i == 9) {
                        logger.warn("Refresh wechat mp access token fail. mp=" + app);
                    }
                }
            }
        }
    }

}
