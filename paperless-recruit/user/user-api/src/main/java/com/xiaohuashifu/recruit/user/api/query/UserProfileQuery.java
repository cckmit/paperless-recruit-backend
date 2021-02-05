package com.xiaohuashifu.recruit.user.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：用户个人信息查询参数
 *
 * @author: xhsf
 * @create: 2020/10/29 23:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileQuery implements Serializable {

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
     * 用户编号
     */
    private Long userId;

    /**
     * 姓名，可右模糊
     */
    private String fullName;

    /**
     * 学号，可右模糊
     */
    private String studentNumber;

    /**
     * 学院编号
     */
    private Long collegeId;

    /**
     * 专业编号
     */
    private Long majorId;

}
