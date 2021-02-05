package com.xiaohuashifu.recruit.user.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.user.api.constant.CollegeConstants;
import com.xiaohuashifu.recruit.user.api.dto.CollegeDTO;
import com.xiaohuashifu.recruit.user.api.dto.DeactivateCollegeDTO;
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
     * @param collegeName 学院名
     * @return CollegeDTO
     */
    CollegeDTO createCollege(@NotBlank @Size(max = CollegeConstants.MAX_COLLEGE_NAME_LENGTH) String collegeName)
            throws DuplicateServiceException;

    /**
     * 获取学院
     *
     * @param id 学院编号
     * @return CollegeDTO
     */
    CollegeDTO getCollege(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 查询学院
     *
     * @param query 查询参数
     * @return QueryResult<CollegeDTO> 带分页信息的查询结果，可能返回空列表
     */
    QueryResult<CollegeDTO> listColleges(@NotNull CollegeQuery query);

    /**
     * 更新学院名
     *
     * @permission admin 权限
     *
     * @param id 学院编号
     * @param collegeName 学院名
     * @return CollegeDTO 更新后的学院
     */
    CollegeDTO updateCollegeName(
            @NotNull @Positive Long id,
            @NotBlank @Size(max = CollegeConstants.MAX_COLLEGE_NAME_LENGTH) String collegeName)
            throws DuplicateServiceException;

    /**
     * 停用学院，会停用该学院的所有专业
     *
     * @permission 需要 admin 权限
     *
     * @param id 学院编号
     * @return 停用结果，附带被停用的专业的数量
     */
    DeactivateCollegeDTO deactivateCollege(@NotNull @Positive Long id) throws ServiceException;

}
