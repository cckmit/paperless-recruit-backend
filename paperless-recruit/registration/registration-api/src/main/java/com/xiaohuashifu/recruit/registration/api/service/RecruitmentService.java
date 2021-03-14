package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.query.RecruitmentQuery;
import com.xiaohuashifu.recruit.registration.api.request.CreateRecruitmentRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateRecruitmentRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：招新服务
 *
 * @author xhsf
 * @create 2020/12/23 19:30
 */
public interface RecruitmentService {

    /**
     * 创建一个招新
     *
     * @param request CreateRecruitmentRequest
     * @return 创建结果
     */
    RecruitmentDTO createRecruitment(@NotNull CreateRecruitmentRequest request) throws ServiceException;

    /**
     * 获取招新
     *
     * @param id 招新编号
     * @return RecruitmentDTO
     */
    RecruitmentDTO getRecruitment(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 查询招新
     *
     * @param query RecruitmentQuery
     * @return 可能返回空列表
     */
    QueryResult<RecruitmentDTO> listRecruitments(@NotNull RecruitmentQuery query);

    /**
     * 更新招新
     *
     * @param request UpdateRecruitmentRequest
     * @return 更新结果
     */
    RecruitmentDTO updateRecruitment(@NotNull UpdateRecruitmentRequest request) throws ServiceException;

    /**
     * 增加报名表数量
     *
     * @param id 招新编号
     */
    void increaseNumberOfApplicationForms(@NotNull @Positive Long id);

}
