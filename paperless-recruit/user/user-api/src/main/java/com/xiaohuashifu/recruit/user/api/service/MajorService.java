package com.xiaohuashifu.recruit.user.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.user.api.constant.MajorConstants;
import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import com.xiaohuashifu.recruit.user.api.query.MajorQuery;
import com.xiaohuashifu.recruit.user.api.request.UpdateMajorRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：专业服务
 *
 * @author xhsf
 * @create 2020/12/24 13:04
 */
public interface MajorService {

    /**
     * 创建专业
     *
     * @permission admin 权限
     *
     * @param collegeId 学院编号
     * @param majorName 专业名
     * @return CollegeDTO
     */
    MajorDTO createMajor(
            @NotNull @Positive Long collegeId,
            @NotBlank @Size(max = MajorConstants.MAX_MAJOR_NAME_LENGTH) String majorName) throws ServiceException;

    /**
     * 获取专业
     *
     * @param id 专业编号
     * @return MajorDTO
     */
    MajorDTO getMajor(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 查询专业
     *
     * @param query 查询参数
     * @return QueryResult<MajorDTO> 带分页信息的查询结果，可能会返回空列表
     */
    QueryResult<MajorDTO> listMajors(@NotNull MajorQuery query);

    /**
     * 更新专业
     *
     * @permission admin 权限
     *
     * @param request UpdateMajorRequest
     * @return MajorDTO 更新后的专业
     */
    MajorDTO updateMajor(@NotNull UpdateMajorRequest request);

    /**
     * 停用一个学院的所有专业
     *
     * @private 内部方法
     *
     * @param collegeId 学院编号
     * @return 被停用的专业数量
     */
    Integer deactivateMajorsByCollegeId(@NotNull @Positive Long collegeId);

}
