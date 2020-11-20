package com.xiaohuashifu.recruit.authentication.service.service;

import com.xiaohuashifu.recruit.authentication.api.dto.AuthOpenidDTO;
import com.xiaohuashifu.recruit.authentication.api.service.AuthOpenidService;
import com.xiaohuashifu.recruit.common.constant.App;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.service.WechatMpService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 描述：AuthOpenid相关服务，用于接入第三方平台的身份认证
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/20 19:29
 */
@Service
public class AuthOpenidServiceImpl implements AuthOpenidService {

    @Reference
    private WechatMpService wechatMpService;

    @Reference
    private UserService userService;

    /**
     * 用于微信小程序用户绑定AuthOpenid
     * 会通过code获取openid
     * 保存时会对openid进行加密
     *
     * @param userId 用户编号
     * @param app 具体的微信小程序
     * @param code 微信小程序wx.login()接口的返回结果
     * @return AuthOpenidDTO
     */
    @Override
    public Result<AuthOpenidDTO> bindAuthOpenidForWechatMp(Long userId, App app, String code) {
        // 检查用户是否存在

        // 检查用户是否已经绑定在这个app上

        // 获取openid
        Result<String> getOpenidResult = wechatMpService.getOpenid(code, app);
        if (!getOpenidResult.isSuccess()) {
            return Result.fail(getOpenidResult);
        }

        // 加密openid

        // 添加到数据库

        // 给用户添加微信小程序基本权限

        return null;
    }

    /**
     * 用于微信小程序用户检查AuthOpenid
     * 会通过code获取openid
     * 可以用于快捷登录时使用
     * 该接口调用成功即可证明用户身份
     *
     * @param app 具体的微信小程序
     * @param code 微信小程序wx.login()接口的返回结果
     * @return AuthOpenidDTO
     */
    @Override
    public Result<AuthOpenidDTO> checkAuthOpenid(@NotNull App app,
                                                  @NotBlank @Size(min = 32, max = 32) String code) {
        throw new UnsupportedOperationException();
    }
}
