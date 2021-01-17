package com.xiaohuashifu.recruit.facade.service.translator.impl;

import com.xiaohuashifu.recruit.facade.service.translator.PathToUrl;
import com.xiaohuashifu.recruit.facade.service.translator.UrlTranslator;
import org.springframework.stereotype.Component;

/**
 * 描述：路径转换器实现类
 *
 * @author xhsf
 * @create 2021/1/17 15:48
 */
@Component
@UrlTranslator
public class UrlTranslatorImpl {
    @PathToUrl
    public String pathToUrl(String path) {
        return "https://oss.xiaohuashifu.top/" + path;
    }
}
