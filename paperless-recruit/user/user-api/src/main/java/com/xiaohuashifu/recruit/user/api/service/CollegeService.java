package com.xiaohuashifu.recruit.user.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.constant.CollegeConstants;
import com.xiaohuashifu.recruit.user.api.constant.MajorConstants;
import com.xiaohuashifu.recruit.user.api.dto.CollegeDTO;
import com.xiaohuashifu.recruit.user.api.dto.CollegeMajorDTO;
import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import com.xiaohuashifu.recruit.user.api.query.CollegeQuery;
import com.xiaohuashifu.recruit.user.api.query.MajorQuery;

import javax.validation.constraints.*;

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
     * @param collegeName 学院名
     * @return CollegeDTO
     */
    Result<CollegeDTO> saveCollege(
            @NotBlank(message = "The collegeName can't be blank.")
            @Size(max = CollegeConstants.MAX_COLLEGE_NAME_LENGTH,
                    message = "The length of collegeName must not be greater than "
                            + CollegeConstants.MAX_COLLEGE_NAME_LENGTH + ".")
                    String collegeName);

    /**
     * 保存专业
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              OperationConflict: 该学院名已经存在
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
     * 删除学院，删除时会删除该学院的所有专业信息
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 对应编号的学院不存在
     *
     * @param id 学院编号
     * @return 删除结果
     */
    Result<Void> deleteCollege(@NotNull(message = "The id can't be null.")
                               @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 删除专业
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 对应编号的专业不存在
     *
     * @param id 专业编号
     * @return 删除结果
     */
    Result<Void> deleteMajor(@NotNull(message = "The id can't be null.")
                             @Positive(message = "The id must be greater than 0.") Long id);

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
     * 查询学院专业
     * 查询学院时会把专业信息一起查出来
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<CollegeMajorDTO> 带分页信息的查询结果，可以返回空列表
     */
    Result<PageInfo<CollegeMajorDTO>> listCollegeMajors(
            @NotNull(message = "The query can't be null.") CollegeQuery query);

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
     * 更新学院名
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 学院不存在 | 新旧学院名相同
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
                            + CollegeConstants.MAX_COLLEGE_NAME_LENGTH + ".")
                    String newCollegeName);

    /**
     * 更新专业名
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 专业不存在 | 新旧专业名相同
     *              OperationConflict: 新专业名已经存在该学院
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
                            + MajorConstants.MAX_MAJOR_NAME_LENGTH + ".")
                    String newMajorName);

}
