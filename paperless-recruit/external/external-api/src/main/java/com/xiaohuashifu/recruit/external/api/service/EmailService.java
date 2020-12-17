package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.po.CheckEmailAuthCodePO;
import com.xiaohuashifu.recruit.external.api.po.CreateAndSendEmailAuthCodePO;
import com.xiaohuashifu.recruit.external.api.po.SendSimpleEmailPO;
import com.xiaohuashifu.recruit.external.api.po.SendTemplateEmailPO;

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
     * @errorCode InvalidParameter: 请求参数格式错误
     *              UnknownError: 发送邮件失败 | 邮箱地址错误 | 网络延迟
     *
     * @param sendSimpleEmailPO 发送简单邮件的参数对象
     * @return 发送结果
     */
    Result<Void> sendSimpleEmail(@NotNull(message = "The sendSimpleEmailPO can't be null.")
                                         SendSimpleEmailPO sendSimpleEmailPO);

    /**
     * 发送模板邮件，使用的是 velocity 模板
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              UnknownError: 发送邮件失败 | 邮箱地址错误 | 网络延迟 | 模板不存在 | 模板参数错误等
     *
     * @param sendTemplateEmailPO 发送模板消息的参数对象
     * @return 发送结果
     */
    // TODO: 2020/12/2 这里的模板可以封装成服务，这样就可以准确判断是否有模板了
    Result<Void> sendTemplateEmail(@NotNull(message = "The sendTemplateEmailPO can't be null.")
                                           SendTemplateEmailPO sendTemplateEmailPO);

    /**
     * 发送邮箱验证码服务
     * 该服务会把邮箱验证码进行缓存
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              UnknownError: 发送邮件验证码失败 | 邮箱地址错误 | 网络延迟
     *
     * @param createAndSendEmailAuthCodePO 创建并发送邮箱验证码参数对象
     * @return Result<Void> 返回结果若 Result.isSuccess() 为 true 表示发送成功，否则发送失败
     */
    Result<Void> createAndSendEmailAuthCode(@NotNull(message = "The createAndSendEmailAuthCodePO can't be null.")
                                                    CreateAndSendEmailAuthCodePO createAndSendEmailAuthCodePO);

    /**
     * 邮箱验证码检验验证码是否有效的服务
     * 该服务检验成功后，可以清除该验证码，即一个验证码只能使用一次（EmailAuthCodeDTO.delete == true 即可）
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.AuthCode.NotExist: 找不到对应邮箱的验证码，有可能已经过期或者没有发送成功
     *              InvalidParameter.AuthCode.Incorrect: 邮箱验证码值不正确
     *
     * @param checkEmailAuthCodePO 检查邮箱验证码参数对象
     * @return Result<Void> 返回结果若 Result.isSuccess() 为 true 表示验证成功，否则验证失败
     */
    Result<Void> checkEmailAuthCode(@NotNull(message = "The checkEmailAuthCodePO can't be null.")
                                            CheckEmailAuthCodePO checkEmailAuthCodePO);
}
