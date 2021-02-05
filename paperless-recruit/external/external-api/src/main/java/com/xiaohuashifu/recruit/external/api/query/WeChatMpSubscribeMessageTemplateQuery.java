package com.xiaohuashifu.recruit.external.api.query;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.constant.QueryConstants;
import com.xiaohuashifu.recruit.external.api.constant.WeChatMpSubscribeMessageTemplateStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：微信订阅消息模板的查询参数
 *
 * @author: xhsf
 * @create: 2020/10/29 23:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeChatMpSubscribeMessageTemplateQuery implements Serializable {

    /**
     * 页码
     */
    @NotNull
    @Positive
    private Long pageNum;

    /**
     * 页条数
     */
    @NotNull
    @Positive
    @Max(value = QueryConstants.MAX_PAGE_SIZE)
    private Long pageSize;

    /**
     * 模板编号，项目产生的
     */
    private Long id;

    /**
     * 模板编号列表，项目产生的
     */
    private List<Long> ids;

    /**
     * 模板所属微信小程序
     */
    private AppEnum app;

    /**
     * 模板编号，微信小程序平台产生的
     */
    private String templateId;

    /**
     * 标题，可模糊
     */
    private String title;

    /**
     * 类别，可模糊
     */
    private String type;

    /**
     * 描述，可模糊
     */
    private String description;

    /**
     * 模板状态
     */
    private WeChatMpSubscribeMessageTemplateStatusEnum status;

}
