package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.PhoneMessageDTO;
import com.xiaohuashifu.recruit.external.api.service.PhoneMessageService;
import org.apache.dubbo.config.annotation.Service;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 15:22
 */
@Service
public class PhoneMessageServiceImpl implements PhoneMessageService {
    @Override
    public Result<Object> sendPhoneMessage(PhoneMessageDTO phoneMessageDTO) {
        return Result.success("Send message success.");
    }
}
