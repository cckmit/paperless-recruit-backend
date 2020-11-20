package com.xiaohuashifu.recruit.authentication.api.service;

import com.xiaohuashifu.recruit.authentication.api.dto.AuthOpenidDTO;
import com.xiaohuashifu.recruit.common.result.Result;

import javax.validation.constraints.NotNull;

/**
 * 描述：AuthOpenid相关服务，用于接入第三方平台的身份认证
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/20 19:29
 */
public interface AuthOpenidService {
    @interface BindAuthOpenid {}
    /**
     * 用于用户绑定AuthOpenid
     * 保存时会对openid进行加密
     *
     * @param authOpenidDTO AuthOpenidDTO 需要userId和appName和openid
     * @return AuthOpenidDTO
     */
    default Result<AuthOpenidDTO> bindAuthOpenid(@NotNull AuthOpenidDTO authOpenidDTO) {
        throw new UnsupportedOperationException();
    }

    @interface CheckAuthOpenid {}
    /**
     * 用于用户检查AuthOpenid
     * 可以用于快捷登录时使用
     * 该接口调用成功即可证明用户身份
     *
     * @param authOpenidDTO AuthOpenidDTO 需要appName和openid
     * @return AuthOpenidDTO
     */
    default Result<AuthOpenidDTO> checkAuthOpenid(@NotNull AuthOpenidDTO authOpenidDTO) {
        throw new UnsupportedOperationException();
    }

}
