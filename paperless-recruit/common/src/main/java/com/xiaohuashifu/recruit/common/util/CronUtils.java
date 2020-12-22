package com.xiaohuashifu.recruit.common.util;

import org.springframework.scheduling.support.CronSequenceGenerator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：Cron 表达式工具类
 *
 * @author xhsf
 * @create 2020/12/22 19:19
 */
public class CronUtils {

    /**
     * cron 表达式的 CronSequenceGenerator 的缓存
     */
    private static final Map<String, CronSequenceGenerator> cronSequenceGeneratorMap = new HashMap<>();

    /**
     * 获取下一个 cron 表达式的时间戳
     *
     * @param cron cron 表达式
     * @param now 当前时间
     * @return 下一个时间
     */
    public static Date next(String cron, Date now) {
        CronSequenceGenerator cronSequenceGenerator = cronSequenceGeneratorMap.getOrDefault(
                cron, cronSequenceGeneratorMap.put(cron, new CronSequenceGenerator(cron)));
        return cronSequenceGenerator.next(now);
    }

}
