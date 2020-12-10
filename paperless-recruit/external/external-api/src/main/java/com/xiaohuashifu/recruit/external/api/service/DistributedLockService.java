package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.constant.DistributedLockServiceConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.concurrent.TimeUnit;

/**
 * 描述：分布式锁服务
 *
 * @author xhsf
 * @create 2020/12/10 18:57
 */
public interface DistributedLockService {

    /**
     * 获取分布式锁，不会自动释放锁
     *
     * @errorCode InvalidParameter: key 格式错误
     *              OperationConflict: 获取锁失败
     *
     * @param key 锁对应的唯一 key
     * @return 获取结果
     */
    Result<Void> getLock(
            @NotBlank(message = "The key can't be blank.")
            @Size(max = DistributedLockServiceConstants.MAX_KEY_LENGTH,
                    message = "The length of key must not be greater than "
                            + DistributedLockServiceConstants.MAX_KEY_LENGTH + ".") String key);

    /**
     * 获取分布式锁，锁到期自动释放
     *
     * @errorCode InvalidParameter: key 或 expirationTime 格式错误
     *              OperationConflict: 获取锁失败
     *
     * @param key 锁对应的唯一 key
     * @param expirationTime 锁自动释放时间
     * @param timeUnit 时间单位
     * @return 获取结果
     */
    Result<Void> getLock(
            @NotBlank(message = "The key can't be blank.")
            @Size(max = DistributedLockServiceConstants.MAX_KEY_LENGTH,
                    message = "The length of key must not be greater than "
                            + DistributedLockServiceConstants.MAX_KEY_LENGTH + ".") String key,
            @NotNull(message = "The expirationTime can't be null.")
            @Positive(message = "The expirationTime must be greater than 0.") Long expirationTime,
            @NotNull(message = "The timeUnit can't be null.") TimeUnit timeUnit);

    /**
     * 释放锁
     *
     * @errorCode InvalidParameter: key 格式错误
     *              InvalidParameter.NotExist: key 不存在
     *
     * @param key 锁对应的唯一 key
     * @return 释放结果
     */
    Result<Void> releaseLock(
            @NotBlank(message = "The key can't be blank.")
            @Size(max = DistributedLockServiceConstants.MAX_KEY_LENGTH,
                    message = "The length of key must not be greater than "
                            + DistributedLockServiceConstants.MAX_KEY_LENGTH + ".") String key);

}
