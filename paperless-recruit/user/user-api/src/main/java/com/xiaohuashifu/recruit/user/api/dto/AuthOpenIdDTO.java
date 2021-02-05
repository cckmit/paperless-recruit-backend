package com.xiaohuashifu.recruit.user.api.dto;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：AuthOpenId 的传输对象
 *
 * @author: xhsf
 * @create: 2020/11/20 19:40
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthOpenIdDTO implements Serializable {

    /**
     * AuthOpenId 的编号
     */
    private Long id;

    /**
     * 对应该 AuthOpenId 的用户的编号
     */
    private Long userId;

    /**
     * 该 OpenId 所属应用
     */
    private String appName;

    /**
     * OpenId
     */
    private String openId;

}
