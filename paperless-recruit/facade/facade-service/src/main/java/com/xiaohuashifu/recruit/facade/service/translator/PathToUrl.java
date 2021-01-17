package com.xiaohuashifu.recruit.facade.service.translator;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：路径转 url
 *
 * @author xhsf
 * @create 2021/1/17 15:45
 */
@Qualifier
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface PathToUrl {
}
