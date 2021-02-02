package com.xiaohuashifu.recruit.organization.api.request;

import com.xiaohuashifu.recruit.organization.api.constant.DepartmentConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

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
    @Size(min = DepartmentConstants.MIN_DEPARTMENT_NAME_LENGTH,
            max = DepartmentConstants.MAX_DEPARTMENT_NAME_LENGTH)
    private String departmentName;

    /**
     * 部门名缩写
     */
    @NotBlank
    @Size(min = DepartmentConstants.MIN_ABBREVIATION_DEPARTMENT_NAME_LENGTH,
            max = DepartmentConstants.MAX_ABBREVIATION_DEPARTMENT_NAME_LENGTH)
    private String abbreviationDepartmentName;

}
