package com.xiaohuashifu.recruit.facade.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 描述：部门 VO
 *
 * @author xhsf
 * @create 2021/1/18 15:26
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentVO {
    /**
     * 部门编号
     */
    @ApiModelProperty(value = "部门编号", example = "123")
    private Long id;

    /**
     * 部门所属组织编号
     */
    @ApiModelProperty(value = "部门所属组织编号", example = "12")
    private Long organizationId;

    /**
     * 部门名
     */
    @ApiModelProperty(value = "部门名", example = "自然科学部")
    private String departmentName;

    /**
     * 部门名缩写
     */
    @ApiModelProperty(value = "部门名缩写", example = "自科部")
    private String abbreviationDepartmentName;

    /**
     * 部门介绍
     */
    @ApiModelProperty(value = "部门介绍", example = "1、参加科研项目和科技比赛，加强对成员的技术培训，培养和提高自身的科研力量和技术支持，拥有自身的科研成果；\n" +
            "2、加强内外交流，组织学生参观学校各学院或其他高校的实验室或工作室、开展项目分享会、科研交流会、科技成果展览会等，培养和促进学生的科研兴趣，并提供其学习、实践的机会；")
    private String introduction;

    /**
     * 部门 logo
     */
    @ApiModelProperty(value = "部门 logo",
            example = "https://oss.xiaohuashifu.top/organization/logo/dc098266-814f-450e-aa98-837810833d7b1.jpg")
    private String logoUrl;

    /**
     * 部门成员数
     */
    @ApiModelProperty(value = "部门成员数", example = "3")
    private String memberNumber;

    /**
     * 部门是否被废弃
     */
    @ApiModelProperty(value = "部门是否被废弃", example = "false")
    private Boolean deactivated;

    /**
     * 部门标签
     */
    @ApiModelProperty(value = "部门标签", example = "创业")
    private Set<String> labels;
}
