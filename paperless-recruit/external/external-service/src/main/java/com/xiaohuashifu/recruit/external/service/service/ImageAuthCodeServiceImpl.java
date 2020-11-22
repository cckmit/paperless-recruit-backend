package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.ImageAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.service.ImageAuthCodeService;
import org.apache.dubbo.config.annotation.Service;

/**
 * 描述：图形验证码服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/22 16:02
 */
@Service
public class ImageAuthCodeServiceImpl implements ImageAuthCodeService {

    /**
     * 创建图形验证码
     * 会把验证码缓存，可用通过checkImageAuthCode检查是否通过校验
     *
     * @param imageAuthCodeDTO ImageAuthCodeDTO
     * @return ImageAuthCodeDTO
     */
    @Override
    public Result<ImageAuthCodeDTO> createImageAuthCode(ImageAuthCodeDTO imageAuthCodeDTO) {
        throw new UnsupportedOperationException();
    }

}
