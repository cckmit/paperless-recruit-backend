package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.facade.service.request.CreateRecruitmentRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateRecruitmentRequest;
import com.xiaohuashifu.recruit.facade.service.vo.RecruitmentVO;
import com.xiaohuashifu.recruit.registration.api.query.RecruitmentQuery;

import java.util.List;

/**
 * 描述：招新管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:49
 */
public interface RecruitmentManager {

    /**
     * 创建招新
     *
     * @param organizationId 组织编号
     * @param request CreateRecruitmentRequest
     * @return RecruitmentVO
     */
    RecruitmentVO createRecruitment(Long organizationId, CreateRecruitmentRequest request);

    /**
     * 获取招新
     *
     * @param id 招新编号
     * @return RecruitmentVO
     */
    RecruitmentVO getRecruitment(Long id);

    /**
     * 列出招新
     *
     * @param query RecruitmentQuery
     * @return List<RecruitmentVO>
     */
    List<RecruitmentVO> listRecruitments(RecruitmentQuery query);

    /**
     * 更新招新
     *
     * @param recruitmentId 招新编号
     * @param request UpdateRecruitmentRequest
     * @return RecruitmentVO
     */
    RecruitmentVO updateRecruitment(Long recruitmentId, UpdateRecruitmentRequest request);

}
