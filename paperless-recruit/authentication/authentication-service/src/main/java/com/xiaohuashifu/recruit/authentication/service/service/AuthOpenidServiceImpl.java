package com.xiaohuashifu.recruit.authentication.service.service;

import com.xiaohuashifu.recruit.authentication.api.dto.AuthOpenidDTO;
import com.xiaohuashifu.recruit.authentication.api.service.AuthOpenidService;
import com.xiaohuashifu.recruit.common.result.Result;
import io.netty.util.CharsetUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.Md4PasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 描述：AuthOpenid相关服务，用于接入第三方平台的身份认证
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/20 19:29
 */
@Service
public class AuthOpenidServiceImpl implements AuthOpenidService {


    /**
     * 用于用户绑定AuthOpenid
     * 保存时会对openid进行加密
     *
     * @param authOpenidDTO AuthOpenidDTO 需要userId和appName和openid
     * @return AuthOpenidDTO
     */
    @Override
    public Result<AuthOpenidDTO> bindAuthOpenid(AuthOpenidDTO authOpenidDTO) {

        return null;
    }

    @Override
    public Result<AuthOpenidDTO> checkAuthOpenid(@NotNull AuthOpenidDTO authOpenidDTO) {
        return null;
    }
}
