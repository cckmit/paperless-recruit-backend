package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.exception.ThirdPartyServiceException;
import com.xiaohuashifu.recruit.external.api.request.CheckEmailAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.request.CreateAndSendEmailAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.request.SendSimpleEmailRequest;
import com.xiaohuashifu.recruit.external.api.request.SendTemplateEmailRequest;

import javax.validation.constraints.NotNull;

/**
 * 描述：发送邮件的服务
 *
 * @private 内部服务
 *
 * @author: xhsf
 * @create: 2020/11/18 16:18
 */
public interface EmailService {

    /**
     * 发送简单邮件
     *
     * @param request SendSimpleEmailRequest
     */
    void sendSimpleEmail(@NotNull SendSimpleEmailRequest request) throws ThirdPartyServiceException;

    /**
     * 发送模板邮件，使用的是 velocity 模板
     *
     * @param request SendTemplateEmailRequest
     */
    // TODO: 2020/12/2 这里的模板可以封装成服务，这样就可以准确判断是否有模板了
    void sendTemplateEmail(@NotNull SendTemplateEmailRequest request) throws ThirdPartyServiceException;

    /**
     * 发送邮箱验证码服务
     * 该服务会把邮箱验证码进行缓存
     *
     * @param request CreateAndSendEmailAuthCodeRequest
     */
    void createAndSendEmailAuthCode(@NotNull CreateAndSendEmailAuthCodeRequest request)
            throws ThirdPartyServiceException;

    /**
     * 邮箱验证码检验验证码是否有效的服务
     * 该服务检验成功后，可以清除该验证码，即一个验证码只能使用一次（EmailAuthCodeDTO.delete == true 即可）
     *
     * @param request CheckEmailAuthCodeRequest
     */
    void checkEmailAuthCode(@NotNull CheckEmailAuthCodeRequest request);
}
