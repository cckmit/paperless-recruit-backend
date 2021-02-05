package com.xiaohuashifu.recruit.user.api.request;

import com.xiaohuashifu.recruit.user.api.constant.MajorConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：更新专业请求
 *
 * @author xhsf
 * @create 2021/2/6 01:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMajorRequest {

    /**
     * 专业编号
     */
    @NotNull
    @Positive
    private Long id;

    /**
     * 专业名
     */
    @Size(max = MajorConstants.MAX_MAJOR_NAME_LENGTH)
    private String majorName;

    /**
     * 是否停用
     */
    @AssertTrue
    private Boolean deactivated;

}
