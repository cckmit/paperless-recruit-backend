package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.CollegeDTO;
import com.xiaohuashifu.recruit.user.api.dto.CollegeMajorDTO;
import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import com.xiaohuashifu.recruit.user.api.query.CollegeQuery;
import com.xiaohuashifu.recruit.user.api.query.MajorQuery;
import com.xiaohuashifu.recruit.user.api.service.CollegeService;
import com.xiaohuashifu.recruit.user.service.dao.CollegeMapper;
import com.xiaohuashifu.recruit.user.service.dao.MajorMapper;
import com.xiaohuashifu.recruit.user.service.pojo.do0.CollegeDO;
import com.xiaohuashifu.recruit.user.service.pojo.do0.MajorDO;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：学院专业相关操作的服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/23 21:01
 */
@Service
public class CollegeServiceImpl implements CollegeService {

    private final CollegeMapper collegeMapper;
    private final MajorMapper majorMapper;
    private final Mapper mapper;

    public CollegeServiceImpl(CollegeMapper collegeMapper, MajorMapper majorMapper, Mapper mapper) {
        this.collegeMapper = collegeMapper;
        this.majorMapper = majorMapper;
        this.mapper = mapper;
    }

    /**
     * 保存学院
     *
     * @param collegeName 学院名
     * @return CollegeDTO
     */
    @Override
    public Result<CollegeDTO> saveCollege(String collegeName) {
        // 判断是否已经存在这个学院
        int count = collegeMapper.countByCollegeName(collegeName);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This college name already exists.");
        }

        // 添加到数据库
        CollegeDO collegeDO = new CollegeDO.Builder().collegeName(collegeName).build();
        collegeMapper.saveCollege(collegeDO);
        return getCollege(collegeDO.getId());
    }

    /**
     * 保存专业
     *
     * @param collegeId 学院编号
     * @param majorName 专业名
     * @return CollegeDTO
     */
    @Override
    public Result<MajorDTO> saveMajor(Long collegeId, String majorName) {
        // 判断这个学院是否存在
        int count = collegeMapper.count(collegeId);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This college does not exists.");
        }

        // 判断这个学院是否已经存在这个专业
        count = majorMapper.countByCollegeIdAndMajorName(collegeId, majorName);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER,
                    "This major name already exists in the college.");
        }

        // 保存到数据库
        MajorDO majorDO = new MajorDO.Builder().collegeId(collegeId).majorName(majorName).build();
        majorMapper.saveMajor(majorDO);
        return getMajor(majorDO.getId());
    }

    /**
     * 删除学院，删除时会删除该学院的所有专业信息
     *
     * @param id 学院编号
     * @return 删除结果
     */
    @Override
    public Result<Void> deleteCollege(Long id) {
        // 查看学院是否存在
        CollegeDO collegeDO = collegeMapper.getCollege(id);
        if (collegeDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The college does not exists.");
        }

        // 查看学院是否还有专业
        int count = majorMapper.countByCollegeId(id);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The college must have no major");
        }

        // 删除学院
        collegeMapper.delete(id);
        return Result.success();
    }

    /**
     * 删除专业
     *
     * @param id 专业编号
     * @return 删除结果
     */
    @Override
    public Result<Void> deleteMajor(Long id) {
        // 查看专业是否存在
        MajorDO majorDO = majorMapper.getMajor(id);
        if (majorDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The major does not exists.");
        }

        // 删除专业
        majorMapper.delete(id);
        return Result.success();
    }

    /**
     * 获取学院
     *
     * @param id 学院编号
     * @return CollegeDTO
     */
    @Override
    public Result<CollegeDTO> getCollege(Long id) {
        CollegeDO collegeDO = collegeMapper.getCollege(id);
        if (collegeDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(collegeDO, CollegeDTO.class));
    }

    /**
     * 查询学院
     *
     * @param query 查询参数
     * @return PageInfo<CollegeDTO> 带分页信息的查询结果
     */
    @Override
    public Result<PageInfo<CollegeDTO>> getCollege(CollegeQuery query) {
        List<CollegeDO> collegeDOList = collegeMapper.getCollegeByQuery(query);
        List<CollegeDTO> collegeDTOList = collegeDOList
                .stream()
                .map(collegeDO -> mapper.map(collegeDO, CollegeDTO.class))
                .collect(Collectors.toList());
        PageInfo<CollegeDTO> pageInfo = new PageInfo<>(collegeDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 查询学院专业
     * 查询学院时会把专业信息一起查出来
     *
     * @param query 查询参数
     * @return PageInfo<CollegeMajorDTO> 带分页信息的查询结果
     */
    @Override
    public Result<PageInfo<CollegeMajorDTO>> getCollegeMajor(CollegeQuery query) {
        List<CollegeDO> collegeDOList = collegeMapper.getCollegeByQuery(query);
        List<CollegeMajorDTO> collegeMajorDTOList = collegeDOList
                .stream()
                .map(collegeDO -> mapper.map(collegeDO, CollegeMajorDTO.class))
                .collect(Collectors.toList());
        PageInfo<CollegeMajorDTO> pageInfo = new PageInfo<>(collegeMajorDTOList);
        for (CollegeMajorDTO collegeMajorDTO : collegeMajorDTOList) {
            List<MajorDO> majorDOList = majorMapper.getMajorByCollegeId(collegeMajorDTO.getId());
            List<MajorDTO> majorDTOList = majorDOList
                    .stream()
                    .map(majorDO -> mapper.map(majorDO, MajorDTO.class))
                    .collect(Collectors.toList());
            collegeMajorDTO.setMajorList(majorDTOList);
        }
        return Result.success(pageInfo);
    }

    /**
     * 获取专业
     *
     * @param id 专业编号
     * @return MajorDTO
     */
    @Override
    public Result<MajorDTO> getMajor(Long id) {
        MajorDO majorDO = majorMapper.getMajor(id);
        if (majorDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(majorDO, MajorDTO.class));
    }

    /**
     * 查询专业
     *
     * @param query 查询参数
     * @return PageInfo<MajorDTO> 带分页信息的查询结果
     */
    @Override
    public Result<PageInfo<MajorDTO>> getMajor(MajorQuery query) {
        List<MajorDO> majorDOList = majorMapper.getMajorByQuery(query);
        List<MajorDTO> majorDTOList = majorDOList
                .stream()
                .map(majorDO -> mapper.map(majorDO, MajorDTO.class))
                .collect(Collectors.toList());
        PageInfo<MajorDTO> pageInfo = new PageInfo<>(majorDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 更新学院名
     *
     * @param id 学院编号
     * @param newCollegeName 新学院名
     * @return CollegeDTO 更新后的学院
     */
    @Override
    public Result<CollegeDTO> updateCollegeName(Long id, String newCollegeName) {
        // 查看学院是否存在
        CollegeDO collegeDO = collegeMapper.getCollege(id);
        if (collegeDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The college does not exists.");
        }

        // 查看新学院名是否和旧学院名相同
        if (collegeDO.getCollegeName().equals(newCollegeName)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER,
                    "The new college name can't be the same as the old college name.");
        }

        // 查看新学院名是否存在
        int count = collegeMapper.countByCollegeName(newCollegeName);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This college name already exists.");
        }

        // 更新到数据库
        collegeMapper.updateCollegeName(id, newCollegeName);
        return getCollege(id);
    }

    /**
     * 更新专业名
     *
     * @param id 专业编号
     * @param newMajorName 新专业名
     * @return MajorDTO 更新后的专业
     */
    @Override
    public Result<MajorDTO> updateMajorName(Long id, String newMajorName) {
        // 查看专业是否存在
        MajorDO majorDO = majorMapper.getMajor(id);
        if (majorDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The major does not exists.");
        }

        // 查看新专业名是否和旧专业名相同
        if (majorDO.getMajorName().equals(newMajorName)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER,
                    "The new major name can't be the same as the old major name.");
        }

        // 查看该学院是否存在新专业名
        int count = majorMapper.countByCollegeIdAndMajorName(majorDO.getCollegeId(), newMajorName);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER,
                    "This major name already exists in the college.");
        }

        // 更新到数据库
        majorMapper.updateMajorName(id, newMajorName);
        return getMajor(id);
    }
}
