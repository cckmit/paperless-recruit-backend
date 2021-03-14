package com.xiaohuashifu.recruit.organization.api.request;

import com.xiaohuashifu.recruit.organization.api.constant.DepartmentConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 描述：创建部门的请求
 *
 * @author xhsf
 * @create 2021/2/2 18:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDepartmentRequest implements Serializable {

    /**
     * 部门所属组织的编号
     */
    @NotNull
    @Positive
    private Long organizationId;

    /**
     * 部门名
     */
    @NotBlank
    @Size(max = DepartmentConstants.MAX_DEPARTMENT_NAME_LENGTH)
    private String departmentName;

    /**
     * 部门介绍
     */
    @NotBlank
    @Size(max = DepartmentConstants.MAX_DEPARTMENT_INTRODUCTION_LENGTH)
    private String introduction;

    /**
     * 部门 logo
     */
    @NotBlank
    @Size(max = DepartmentConstants.MAX_DEPARTMENT_LOGO_URL_LENGTH)
    @Pattern(regexp = "(departments/logos/)(.+)(\\.jpg|\\.jpeg|\\.png|\\.gif)")
    private String logoUrl;

    /**
     * 部门类型
     */
    @NotBlank
    @Size(max = DepartmentConstants.MAX_DEPARTMENT_TYPE_LENGTH)
    private String departmentType;

    /**
     * 部门规模
     */
    @NotBlank
    @Size(max = DepartmentConstants.MAX_DEPARTMENT_SIZE_LENGTH)
    private String size;

    /**
     * 部门标签
     */
    @Size(max = DepartmentConstants.MAX_DEPARTMENT_LABEL_NUMBER)
    private Set<String> labels;

}
