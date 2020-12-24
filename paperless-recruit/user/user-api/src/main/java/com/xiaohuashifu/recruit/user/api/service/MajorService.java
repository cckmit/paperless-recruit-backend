package com.xiaohuashifu.recruit.user.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.constant.MajorConstants;
import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import com.xiaohuashifu.recruit.user.api.query.MajorQuery;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/24 13:04
 */
public interface MajorService {

    /**
     * 保存专业
     *
     * @permission admin 权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotExist: 学院不存在
     *              OperationConflict: 该专业名已经存在
     *
     * @param collegeId 学院编号
     * @param majorName 专业名
     * @return CollegeDTO
     */
    Result<MajorDTO> saveMajor(
            @NotNull(message = "The collegeId can't be null.")
            @Positive(message = "The collegeId must be greater than 0.") Long collegeId,
            @NotBlank(message = "The majorName can't be blank.")
            @Size(max = MajorConstants.MAX_MAJOR_NAME_LENGTH,
                    message = "The length of majorName must not be greater than "
                            + MajorConstants.MAX_MAJOR_NAME_LENGTH + ".") String majorName);

    /**
     * 获取专业
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到该编号的专业
     *
     * @param id 专业编号
     * @return MajorDTO
     */
    Result<MajorDTO> getMajor(@NotNull(message = "The id can't be null.")
                              @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 查询专业
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<MajorDTO> 带分页信息的查询结果，可能会返回空列表
     */
    Result<PageInfo<MajorDTO>> listMajors(@NotNull(message = "The query can't be null.") MajorQuery query);

    /**
     * 更新专业名
     *
     * @permission admin 权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotExist: 专业不存在
     *              OperationConflict.Unmodified: 新旧专业名相同
     *              OperationConflict: 新专业名已经存在
     *
     * @param id 专业编号
     * @param newMajorName 新专业名
     * @return MajorDTO 更新后的专业
     */
    Result<MajorDTO> updateMajorName(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newMajorName can't be blank.")
            @Size(max = MajorConstants.MAX_MAJOR_NAME_LENGTH,
                    message = "The length of newMajorName must not be greater than "
                            + MajorConstants.MAX_MAJOR_NAME_LENGTH + ".") String newMajorName);
}
