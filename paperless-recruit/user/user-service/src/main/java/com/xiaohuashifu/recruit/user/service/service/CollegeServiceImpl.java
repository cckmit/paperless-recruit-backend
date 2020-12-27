package com.xiaohuashifu.recruit.user.service.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.CollegeDTO;
import com.xiaohuashifu.recruit.user.api.dto.DeactivateCollegeDTO;
import com.xiaohuashifu.recruit.user.api.query.CollegeQuery;
import com.xiaohuashifu.recruit.user.api.service.CollegeService;
import com.xiaohuashifu.recruit.user.api.service.MajorService;
import com.xiaohuashifu.recruit.user.service.dao.CollegeMapper;
import com.xiaohuashifu.recruit.user.service.do0.CollegeDO;
import org.apache.dubbo.config.annotation.Reference;
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

    @Reference
    private MajorService majorService;

    public CollegeServiceImpl(CollegeMapper collegeMapper) {
        this.collegeMapper = collegeMapper;
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
        return Result.success(collegeDO2CollegeDTO(collegeDO));
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
                .map(this::collegeDO2CollegeDTO)
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
     * 停用学院，会停用该学院的所有专业
     *
     * @permission 需要 admin 权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotExist: 学院不存在
     *              OperationConflict.Deactivated: 该学院已经被停用
     *
     * @param id 学院编号
     * @return 停用结果，附带被停用的专业的数量
     */
    @Override
    public Result<DeactivateCollegeDTO> deactivateCollege(Long id) {
        // 判断学院是否存在
        CollegeDO collegeDO = collegeMapper.getCollege(id);
        if (collegeDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The college does not exist");
        }

        // 更新学院的为停用
        int count = collegeMapper.updateDeactivated(id, true);

        // 如果更新失败表示该学院已经被停用了
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DEACTIVATED,
                    "The college already deactivated.");
        }

        // 停用该学院的所有专业
        Integer deactivatedCount = majorService.deactivateMajorsByCollegeId(id).getData();

        // 停用成功
        CollegeDTO collegeDTO = getCollege(id).getData();
        DeactivateCollegeDTO deactivateCollegeDTO = new DeactivateCollegeDTO(collegeDTO, deactivatedCount);
        return Result.success(deactivateCollegeDTO);
    }

    /**
     * 检查学院状态
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter.NotExist: 学院不存在
     *              Forbidden.Deactivated: 学院被停用
     *
     * @param id 学院编号
     * @return 检查结果
     */
    @Override
    public <T> Result<T> checkCollegeStatus(Long id) {
        // 判断学院是否存在
        Boolean deactivated = collegeMapper.getDeactivated(id);
        if (deactivated == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The college does not exist.");
        }

        // 判断学院是否被停用
        if (deactivated) {
            return Result.fail(ErrorCodeEnum.FORBIDDEN_DEACTIVATED, "The college is deactivated.");
        }

        // 通过检查
        return Result.success();
    }

    /**
     * CollegeDO to CollegeDTO
     *
     * @param collegeDO CollegeDO
     * @return CollegeDTO
     */
    private CollegeDTO collegeDO2CollegeDTO(CollegeDO collegeDO) {
        return new CollegeDTO.Builder()
                .id(collegeDO.getId())
                .collegeName(collegeDO.getCollegeName())
                .deactivated(collegeDO.getDeactivated())
                .build();
    }
}
