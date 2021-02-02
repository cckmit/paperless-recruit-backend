package com.xiaohuashifu.recruit.organization.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 描述：部门
 *
 * @author xhsf
 * @create 2020/12/7 13:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("department")
public class DepartmentDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long organizationId;
    private String departmentName;
    private String abbreviationDepartmentName;
    private String introduction;
    private String logoUrl;
    private Integer numberOfMembers;
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<String> labels;
    @TableField("is_deactivated")
    private Boolean deactivated;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
