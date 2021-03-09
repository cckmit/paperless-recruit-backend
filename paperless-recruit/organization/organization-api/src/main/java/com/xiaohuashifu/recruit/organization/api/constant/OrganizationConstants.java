package com.xiaohuashifu.recruit.organization.api.constant;

import java.util.List;

/**
 * 描述：组织相关常量
 *
 * @author xhsf
 * @create 2020/12/10 16:15
 */
public class OrganizationConstants {

    /**
     * 组织名最大长度
     */
    public static final int MAX_ORGANIZATION_NAME_LENGTH = 20;

    /**
     * 组织介绍最大长度
     */
    public static final int MAX_ORGANIZATION_INTRODUCTION_LENGTH = 400;

    /**
     * logoUrl 最大长度
     */
    public static final int MAX_ORGANIZATION_LOGO_URL_LENGTH = 255;

    /**
     * 组织最大的标签数
     */
    public static final int MAX_ORGANIZATION_LABEL_NUMBER = 3;

    /**
     * 组织标签最大长度
     */
    public static final int MAX_ORGANIZATION_LABEL_LENGTH = 4;

    /**
     * 组织地址最大长度
     */
    public static final int MAX_ORGANIZATION_ADDRESS_LENGTH = 50;

    /**
     * 组织网址最大长度
     */
    public static final int MAX_ORGANIZATION_WEBSITE_LENGTH = 100;

    /**
     * 组织类型最大长度
     */
    public static final int MAX_ORGANIZATION_TYPE_LENGTH = 10;

    /**
     * 组织规模最大长度
     */
    public static final int MAX_ORGANIZATION_SIZE_LENGTH = 10;

    /**
     * 组织规模集合
     */
    public static final List<String> ORGANIZATION_SIZE_LIST =
            List.of("10人以下", "10-20人", "20-50人", "50-100人", "100-200人", "200-300人", "300-500人", "500人以上");

}
