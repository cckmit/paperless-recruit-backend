package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.EmailAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.dto.EmailDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 描述：发送邮件的服务
 *
 * @author: xhsf
 * @create: 2020/11/18 16:18
 */
public interface EmailService {

    @interface SendSimpleEmail{}
    /**
     * 发送简单邮件
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              UnknownError: 发送邮件失败 | 邮箱地址错误 | 网络延迟
     *
     * @param emailDTO 需要to、subject、text三个字段
     * @param attachmentMap 附件Map，可以为null
     * @return 发送结果
     */
    default Result<Void> sendSimpleEmail(@NotNull EmailDTO emailDTO, Map<String, byte[]> attachmentMap) {
        throw new UnsupportedOperationException();
    }

    @interface SendTemplateEmail{}
    /**
     * 发送模板邮件，使用的是velocity模板
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              UnknownError: 发送邮件失败 | 邮箱地址错误 | 网络延迟 | 模板不存在 | 模板参数错误等
     *
     * @param emailDTO 需要to、subject两个字段
     * @param templateName 模板名，模板需要提前创建
     * @param model 模板内的动态绑定的变量
     * @param attachmentMap 附件Map，可以为null
     * @return 发送结果
     */
    // TODO: 2020/12/2 这里的模板可以封装成服务，这样就可以准确判断是否有模板了
    default Result<Void> sendTemplateEmail(@NotNull EmailDTO emailDTO, @NotBlank String templateName,
                                             @NotEmpty Map<String, Object> model, Map<String, byte[]> attachmentMap) {
        throw new UnsupportedOperationException();
    }

    @interface CreateAndSendEmailAuthCode{}
    /**
     * 发送邮箱验证码服务
     * 该服务会把邮箱验证码进行缓存
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              UnknownError: 发送邮件验证码失败 | 邮箱地址错误 | 网络延迟
     *
     * @param emailAuthCodeDTO 邮箱验证码对象
     * @return Result<Void> 返回结果若Result.isSuccess()为true表示发送成功，否则发送失败
     */
    default Result<Void> createAndSendEmailAuthCode(@NotNull EmailAuthCodeDTO emailAuthCodeDTO) {
        throw new UnsupportedOperationException();
    }

    @interface CheckEmailAuthCode{}
    /**
     * 邮箱验证码检验验证码是否有效的服务
     * 该服务检验成功后，可以清除该验证码，即一个验证码只能使用一次（EmailAuthCodeDTO.delete == true即可）
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.AuthCode.NotExist: 找不到对应邮箱的验证码，有可能已经过期或者没有发送成功
     *              InvalidParameter.AuthCode.Incorrect: 邮箱验证码值不正确
     *
     * @param emailAuthCodeDTO 邮箱验证码对象
     * @return Result<Void> 返回结果若Result.isSuccess()为true表示验证成功，否则验证失败
     */
    default Result<Void> checkEmailAuthCode(@NotNull EmailAuthCodeDTO emailAuthCodeDTO) {
        throw new UnsupportedOperationException();
    }
}
