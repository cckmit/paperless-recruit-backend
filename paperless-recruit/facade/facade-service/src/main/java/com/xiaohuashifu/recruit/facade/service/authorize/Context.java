package com.xiaohuashifu.recruit.facade.service.authorize;

import com.xiaohuashifu.recruit.facade.service.exception.ForbiddenException;

/**
 * 描述：上下文
 *
 * @author xhsf
 * @create 2021/1/17 23:08
 */
public interface Context {

    /**
     * 判断是否是资源的拥有者
     *
     * @param id 资源的编号
     * @throws ForbiddenException 当不是资源的拥有者时抛出的异常
     */
    void isOwner(Long id) throws ForbiddenException;

}
