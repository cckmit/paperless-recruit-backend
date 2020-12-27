package com.xiaohuashifu.recruit.user.service.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.CollegeDTO;
import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import com.xiaohuashifu.recruit.user.api.query.MajorQuery;
import com.xiaohuashifu.recruit.user.api.service.CollegeService;
import com.xiaohuashifu.recruit.user.api.service.MajorService;
import com.xiaohuashifu.recruit.user.service.dao.MajorMapper;
import com.xiaohuashifu.recruit.user.service.do0.MajorDO;
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
public class MajorServiceImpl implements MajorService {

    @Reference
    private CollegeService collegeService;

    private final MajorMapper majorMapper;

    public MajorServiceImpl(MajorMapper majorMapper) {
        this.majorMapper = majorMapper;
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
        Result<CollegeDTO> getCollegeResult = collegeService.getCollege(collegeId);
        if (getCollegeResult.isFailure()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The college does not exist.");
        }

        // 判断这个专业名是否已经存在
        int count = majorMapper.countByMajorName(majorName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The majorName already exist.");
        }

        // 保存到数据库
        MajorDO majorDO = new MajorDO.Builder().collegeId(collegeId).majorName(majorName).build();
        majorMapper.insertMajor(majorDO);
        return getMajor(majorDO.getId());
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
        return Result.success(majorDO2MajorDTO(majorDO));
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
                .map(this::majorDO2MajorDTO)
                .collect(Collectors.toList());
        PageInfo<MajorDTO> pageInfo = new PageInfo<>(majorDTOList);
        return Result.success(pageInfo);
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

    /**
     * 停用专业
     *
     * @permission 需要 admin 权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotExist: 专业不存在
     *              OperationConflict.Deactivated: 该专业已经被停用
     *
     * @param id 专业编号
     * @return 停用结果
     */
    @Override
    public Result<MajorDTO> deactivateMajor(Long id) {
        // 判断专业是否存在
        MajorDO majorDO = majorMapper.getMajor(id);
        if (majorDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The major does not exist");
        }

        // 更新专业的为停用
        int count = majorMapper.updateDeactivated(id, true);

        // 如果更新失败表示该专业已经被停用了
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DEACTIVATED,
                    "The major already deactivated.");
        }

        // 停用成功
        return getMajor(id);
    }

    /**
     * 停用一个学院的所有专业
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param collegeId 学院编号
     * @return 被停用的专业数量
     */
    @Override
    public Result<Integer> deactivateMajorsByCollegeId(Long collegeId) {
        int count = majorMapper.updateDeactivatedByCollegeId(collegeId, true);
        return Result.success(count);
    }

    /**
     * 检查专业状态
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter.NotExist: 专业不存在
     *              Forbidden.Deactivated: 专业被停用
     *
     * @param id 专业编号
     * @return 检查结果
     */
    @Override
    public <T> Result<T> checkMajorStatus(Long id) {
        // 判断专业是否存在
        Boolean deactivated = majorMapper.getDeactivated(id);
        if (deactivated == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The major does not exist.");
        }

        // 判断专业是否被停用
        if (deactivated) {
            return Result.fail(ErrorCodeEnum.FORBIDDEN_DEACTIVATED, "The major is deactivated.");
        }

        // 通过检查
        return Result.success();
    }

    /**
     * MajorDO to MajorDTO
     *
     * @param majorDO MajorDO
     * @return MajorDTO
     */
    private MajorDTO majorDO2MajorDTO(MajorDO majorDO) {
        return new MajorDTO.Builder()
                .id(majorDO.getId())
                .collegeId(majorDO.getCollegeId())
                .majorName(majorDO.getMajorName())
                .deactivated(majorDO.getDeactivated())
                .build();
    }

}
