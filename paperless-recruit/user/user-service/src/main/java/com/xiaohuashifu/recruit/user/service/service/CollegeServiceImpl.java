package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.CollegeDTO;
import com.xiaohuashifu.recruit.user.api.dto.CollegeMajorDTO;
import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import com.xiaohuashifu.recruit.user.api.query.CollegeQuery;
import com.xiaohuashifu.recruit.user.api.query.MajorQuery;
import com.xiaohuashifu.recruit.user.api.service.CollegeService;
import com.xiaohuashifu.recruit.user.service.dao.CollegeMapper;
import com.xiaohuashifu.recruit.user.service.dao.MajorMapper;
import com.xiaohuashifu.recruit.user.service.do0.CollegeDO;
import com.xiaohuashifu.recruit.user.service.do0.MajorDO;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.Objects;
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
     * @permission admin 权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              OperationConflict: 该学院名已经存在
     *
     * @param collegeName 学院名
     * @return CollegeDTO
     */
    @Override
    public Result<CollegeDTO> saveCollege(String collegeName) {
        // 判断是否已经存在这个学院
        int count = collegeMapper.countByCollegeName(collegeName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "This collegeName already exist.");
        }

        // 添加到数据库
        CollegeDO collegeDO = new CollegeDO.Builder().collegeName(collegeName).build();
        collegeMapper.insertCollege(collegeDO);
        return getCollege(collegeDO.getId());
    }

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
    @Override
    public Result<MajorDTO> saveMajor(Long collegeId, String majorName) {
        // 判断这个学院是否存在
        int count = collegeMapper.count(collegeId);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The college does not exist.");
        }

        // 判断这个专业名是否已经存
        count = majorMapper.countByMajorName(majorName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The majorName already exist.");
        }

        // 保存到数据库
        MajorDO majorDO = new MajorDO.Builder().collegeId(collegeId).majorName(majorName).build();
        majorMapper.insertMajor(majorDO);
        return getMajor(majorDO.getId());
    }

    /**
     * 获取学院
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到该编号的学院
     *
     * @param id 学院编号
     * @return CollegeDTO
     */
    @Override
    public Result<CollegeDTO> getCollege(Long id) {
        CollegeDO collegeDO = collegeMapper.getCollege(id);
        if (collegeDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(collegeDO, CollegeDTO.class));
    }

    /**
     * 查询学院
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<CollegeDTO> 带分页信息的查询结果，可能返回空列表
     */
    @Override
    public Result<PageInfo<CollegeDTO>> listColleges(CollegeQuery query) {
        List<CollegeDO> collegeDOList = collegeMapper.listColleges(query);
        List<CollegeDTO> collegeDTOList = collegeDOList
                .stream()
                .map(collegeDO -> mapper.map(collegeDO, CollegeDTO.class))
                .collect(Collectors.toList());
        PageInfo<CollegeDTO> pageInfo = new PageInfo<>(collegeDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 获取学院名
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到该编号的学院名
     *
     * @param id 学院编号
     * @return String 学院名
     */
    @Override
    public Result<String> getCollegeName(Long id) {
        String collegeName = collegeMapper.getCollegeName(id);
        if (collegeName == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(collegeName);
    }

    /**
     * 查询学院专业
     * 查询学院时会把专业信息一起查出来
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<CollegeMajorDTO> 带分页信息的查询结果，可以返回空列表
     */
    @Override
    public Result<PageInfo<CollegeMajorDTO>> listCollegeMajors(CollegeQuery query) {
        List<CollegeDO> collegeDOList = collegeMapper.listColleges(query);
        List<CollegeMajorDTO> collegeMajorDTOList = collegeDOList
                .stream()
                .map(collegeDO -> mapper.map(collegeDO, CollegeMajorDTO.class))
                .collect(Collectors.toList());
        PageInfo<CollegeMajorDTO> pageInfo = new PageInfo<>(collegeMajorDTOList);
        for (CollegeMajorDTO collegeMajorDTO : collegeMajorDTOList) {
            List<MajorDO> majorDOList = majorMapper.listMajorsByCollegeId(collegeMajorDTO.getId());
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
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到该编号的专业
     *
     * @param id 专业编号
     * @return MajorDTO
     */
    @Override
    public Result<MajorDTO> getMajor(Long id) {
        MajorDO majorDO = majorMapper.getMajor(id);
        if (majorDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(majorDO, MajorDTO.class));
    }

    /**
     * 查询专业
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<MajorDTO> 带分页信息的查询结果，可能会返回空列表
     */
    @Override
    public Result<PageInfo<MajorDTO>> listMajors(MajorQuery query) {
        List<MajorDO> majorDOList = majorMapper.listMajors(query);
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
    @Override
    public Result<CollegeDTO> updateCollegeName(Long id, String newCollegeName) {
        // 查看学院是否存在
        CollegeDO collegeDO = collegeMapper.getCollege(id);
        if (collegeDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The college does not exist.");
        }

        // 查看新学院名是否和旧学院名相同
        if (Objects.equals(collegeDO.getCollegeName(), newCollegeName)) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The newCollegeName can't be the same as the oldCollegeName.");
        }

        // 查看新学院名是否存在
        int count = collegeMapper.countByCollegeName(newCollegeName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The collegeName already exist.");
        }

        // 更新到数据库
        collegeMapper.updateCollegeName(id, newCollegeName);
        return getCollege(id);
    }

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
    @Override
    public Result<MajorDTO> updateMajorName(Long id, String newMajorName) {
        // 查看专业是否存在
        MajorDO majorDO = majorMapper.getMajor(id);
        if (majorDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The major does not exist.");
        }

        // 查看新专业名是否和旧专业名相同
        if (Objects.equals(majorDO.getMajorName(), newMajorName)) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The new major name can't be the same as the old major name.");
        }

        // 查看该学院是否存在新专业名
        int count = majorMapper.countByMajorName(newMajorName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The majorName already exist.");
        }

        // 更新到数据库
        majorMapper.updateMajorName(id, newMajorName);
        return getMajor(id);
    }

}
