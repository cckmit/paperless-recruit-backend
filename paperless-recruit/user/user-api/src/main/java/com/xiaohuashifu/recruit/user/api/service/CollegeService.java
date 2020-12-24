package com.xiaohuashifu.recruit.user.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.constant.CollegeConstants;
import com.xiaohuashifu.recruit.user.api.dto.CollegeDTO;
import com.xiaohuashifu.recruit.user.api.query.CollegeQuery;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：学院专业相关操作的服务
 *
 * @author: xhsf
 * @create: 2020/11/23 17:02
 */
public interface CollegeService {

    /**
     * 保存学院
     *
     * @permission admin 权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              OperationConflict: 该学院名已经存在
     *
     * @param collegeName 学院名
     * @return CollegeDTO
     */
    Result<CollegeDTO> saveCollege(
            @NotBlank(message = "The collegeName can't be blank.")
            @Size(max = CollegeConstants.MAX_COLLEGE_NAME_LENGTH,
                    message = "The length of collegeName must not be greater than "
                            + CollegeConstants.MAX_COLLEGE_NAME_LENGTH + ".") String collegeName);

    /**
     * 获取学院
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到该编号的学院
     *
     * @param id 学院编号
     * @return CollegeDTO
     */
    Result<CollegeDTO> getCollege(@NotNull(message = "The id can't be null.")
                                  @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 查询学院
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<CollegeDTO> 带分页信息的查询结果，可能返回空列表
     */
    Result<PageInfo<CollegeDTO>> listColleges(@NotNull(message = "The query can't be null.") CollegeQuery query);

    /**
     * 获取学院名
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到该编号的学院名
     *
     * @param id 学院编号
     * @return String 学院名
     */
    Result<String> getCollegeName(@NotNull(message = "The id can't be null.")
                                  @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 更新学院名
     *
     * @permission admin 权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotExist: 学院不存在
     *              OperationConflict.Unmodified: 新旧学院名相同
     *              OperationConflict: 新学院名已经存在
     *
     * @param id 学院编号
     * @param newCollegeName 新学院名
     * @return CollegeDTO 更新后的学院
     */
    Result<CollegeDTO> updateCollegeName(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newCollegeName can't be blank.")
            @Size(max = CollegeConstants.MAX_COLLEGE_NAME_LENGTH,
                    message = "The length of newCollegeName must not be greater than "
                            + CollegeConstants.MAX_COLLEGE_NAME_LENGTH + ".") String newCollegeName);

}
