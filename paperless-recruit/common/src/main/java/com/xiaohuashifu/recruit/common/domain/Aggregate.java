package com.xiaohuashifu.recruit.common.domain;

/**
 * 描述：聚合根的 Marker 接口
 *
 * @author xhsf
 * @create 2021/1/7 00:58
 */
public interface Aggregate<ID extends Identifier> extends Entity<ID> {
}
