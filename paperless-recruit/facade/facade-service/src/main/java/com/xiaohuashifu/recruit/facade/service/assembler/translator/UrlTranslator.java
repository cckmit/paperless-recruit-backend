package com.xiaohuashifu.recruit.facade.service.assembler.translator;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：路径转换器
 *
 * @author xhsf
 * @create 2021/1/17 15:44
 */
@Qualifier
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface UrlTranslator {
}
