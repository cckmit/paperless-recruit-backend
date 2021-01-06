package com.xiaohuashifu.recruit.common.domain;

import javax.validation.constraints.NotNull;

/**
 * 描述：Repository 层抽象
 *
 * @author xhsf
 * @create 2021/1/7 00:59
 */
public interface Repository<T extends Aggregate<ID>, ID extends Identifier> {

    /**
     * 将一个 Aggregate 附属到一个 Repository，让它变为可追踪。
     */
    void attach(@NotNull T aggregate);

    /**
     * 解除一个 Aggregate 的追踪
     */
    void detach(@NotNull T aggregate);

    /**
     * 通过 ID 寻找 Aggregate。
     * 找到的 Aggregate 自动是可追踪的
     */
    T find(@NotNull ID id);

    /**
     * 将一个 Aggregate 从 Repository 移除
     * 操作后的 aggregate 对象自动取消追踪
     */
    void remove(@NotNull T aggregate);

    /**
     * 保存一个 Aggregate
     * 保存后自动重置追踪条件
     */
    void save(@NotNull T aggregate);
}
