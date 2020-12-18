package com.xiaohuashifu.recruit.external.api.service;

import java.util.concurrent.TimeUnit;

/**
 * 描述：限频服务，使用 Redis 实现
 *
 * @private 内部服务
 *
 * @author xhsf
 * @create 2020/12/18 15:29
 */
public interface FrequencyLimitService {

    /**
     * 查询一个键是否被允许操作
     *
     * 如短信验证码服务使用 isAllowed("15333333333", 10, 1, TimeUnit.DAYS); 表示一天只能发送10次短信验证码
     * 如短信验证码服务使用 isAllowed("15333333333", 1, 1, TimeUnit.MINUTES); 表示一分钟只能发送1次短信验证码
     *
     * @param key 需要限频的键，key的格式最好是 {服务名}:{具体业务名}:{唯一标识符}，如 sms:sms-auth-code:15333333333
     * @param frequency 频率
     * @param time 限频时间
     * @param unit 时间单位
     * @return 是否允许
     */
    Boolean isAllowed(String key, Long frequency, Long time, TimeUnit unit);

}
