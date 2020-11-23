package com.xiaohuashifu.recruit.user.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
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
 * @email: 827032783@qq.com
 * @create: 2020/11/23 17:02
 */
public interface CollegeService {

    /**
     * 保存学院
     *
     * @param collegeName 学院名
     * @return CollegeDTO
     */
    default Result<CollegeDTO> saveCollege(@NotBlank @Size(min = 1, max = 50) String collegeName) {
        throw new UnsupportedOperationException();
    }

    /**
     * 保存专业
     *
     * @param collegeId 学院编号
     * @param majorName 专业名
     * @return CollegeDTO
     */
    default Result<MajorDTO> saveMajor(
            @NotNull @Positive Long collegeId,
            @NotBlank @Size(min = 1, max = 50) String majorName) {
        throw new UnsupportedOperationException();
    }

    /**
     * 删除学院，删除时会删除该学院的所有专业信息
     *
     * @param id 学院编号
     * @return 删除结果
     */
    default Result<Void> deleteCollege(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 删除专业
     *
     * @param id 专业编号
     * @return 删除结果
     */
    default Result<Void> deleteMajor(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取学院
     *
     * @param id 学院编号
     * @return CollegeDTO
     */
    default Result<CollegeDTO> getCollege(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 查询学院
     *
     * @param query 查询参数
     * @return PageInfo<CollegeDTO> 带分页信息的查询结果
     */
    default Result<PageInfo<CollegeDTO>> getCollege(@NotNull CollegeQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * 查询学院专业
     * 查询学院时会把专业信息一起查出来
     *
     * @param query 查询参数
     * @return PageInfo<CollegeMajorDTO> 带分页信息的查询结果
     */
    default Result<PageInfo<CollegeMajorDTO>> getCollegeMajor(@NotNull CollegeQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取专业
     *
     * @param id 专业编号
     * @return MajorDTO
     */
    default Result<MajorDTO> getMajor(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 查询专业
     *
     * @param query 查询参数
     * @return PageInfo<MajorDTO> 带分页信息的查询结果
     */
    default Result<PageInfo<MajorDTO>> getMajor(@NotNull MajorQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新学院名
     *
     * @param id 学院编号
     * @param newCollegeName 新学院名
     * @return CollegeDTO 更新后的学院
     */
    default Result<CollegeDTO> updateCollegeName(@NotNull @Positive Long id,
                                         @NotBlank @Size(min = 1, max = 50) String newCollegeName) {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新专业名
     *
     * @param id 专业编号
     * @param newMajorName 新专业名
     * @return MajorDTO 更新后的专业
     */
    default Result<MajorDTO> updateMajorName(@NotNull @Positive Long id,
                                         @NotBlank @Size(min = 1, max = 50) String newMajorName) {
        throw new UnsupportedOperationException();
    }

}
