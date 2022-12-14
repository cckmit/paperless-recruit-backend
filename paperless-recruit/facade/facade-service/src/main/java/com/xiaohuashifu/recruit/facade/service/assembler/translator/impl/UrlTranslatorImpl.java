package com.xiaohuashifu.recruit.facade.service.assembler.translator.impl;

import com.xiaohuashifu.recruit.facade.service.assembler.translator.PathToUrl;
import com.xiaohuashifu.recruit.facade.service.assembler.translator.UrlTranslator;
import org.apache.commons.lang.StringUtils;
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
        if (StringUtils.isBlank(path)) {
            return "";
        }
        return "http://oss.xiaohuashifu.top/" + path;
    }

}
