package com.xiaohuashifu.recruit.external.service.timer;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.constant.PlatformEnum;
import com.xiaohuashifu.recruit.common.exception.InternalServiceException;
import com.xiaohuashifu.recruit.external.service.manager.WeChatMpManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 描述：微信小程序定时器
 *
 * @author: xhsf
 * @create: 2020/11/21 18:50
 */
@Configuration
@EnableScheduling
public class WeChatMpTimer {

    private static final Logger logger = LoggerFactory.getLogger(WeChatMpTimer.class);

    private final WeChatMpManager weChatMpManager;

    /**
     * 刷新 AccessToken 任务初始延迟
     */
    private static final long REFRESH_ACCESS_TOKEN_TASK_INITIAL_DELAY = 10000;

    /**
     * 刷新 AccessToken 任务固定延迟
     */
    private static final long REFRESH_ACCESS_TOKEN_TASK_FIXED_DELAY = 3600000;

    public WeChatMpTimer(WeChatMpManager weChatMpManager) {
        this.weChatMpManager = weChatMpManager;
    }

    /**
     * 刷新微信小程序 AccessToken 定时任务
     * 每小时刷新一次 AccessToken
     * 如果刷新失败则重复获取10次
     * 如果还失败则报警处理
     */
    @Scheduled(initialDelay = REFRESH_ACCESS_TOKEN_TASK_INITIAL_DELAY,
            fixedDelay = REFRESH_ACCESS_TOKEN_TASK_FIXED_DELAY)
    public void refreshAccessTokenTask() {
        for (AppEnum app : AppEnum.values()) {
            if (app.getPlatform() == PlatformEnum.WECHAT_MINI_PROGRAM) {
                for (int i = 0; i < 10; i++) {
                    try {
                        weChatMpManager.refreshAccessToken(app);
                        break;
                    } catch (InternalServiceException ignored) {
                    }
                    if (i == 9) {
                        logger.error("Refresh wechat mp access token failed. app{}", app);
                    }
                }
            }
        }
    }

}
