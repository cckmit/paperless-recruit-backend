package com.xiaohuashifu.recruit.common.domain;

/**
 * 描述：ID 类型的能力接口
 *
 * @author xhsf
 * @create 2021/1/7 00:55
 */
public interface Identifiable<ID extends Identifier> {
    ID getId();
}
