package com.xiaohuashifu.recruit.user.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import com.xiaohuashifu.recruit.user.api.query.MajorQuery;
import com.xiaohuashifu.recruit.user.api.request.UpdateMajorRequest;
import com.xiaohuashifu.recruit.user.api.service.CollegeService;
import com.xiaohuashifu.recruit.user.api.service.MajorService;
import com.xiaohuashifu.recruit.user.service.assembler.MajorAssembler;
import com.xiaohuashifu.recruit.user.service.dao.MajorMapper;
import com.xiaohuashifu.recruit.user.service.do0.MajorDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：专业相关操作的服务
 *
 * @author: xhsf
 * @create: 2020/11/23 21:01
 */
@Service
public class MajorServiceImpl implements MajorService {

    @Reference
    private CollegeService collegeService;

    private final MajorMapper majorMapper;

    private final MajorAssembler majorAssembler;

    public MajorServiceImpl(MajorMapper majorMapper, MajorAssembler majorAssembler) {
        this.majorMapper = majorMapper;
        this.majorAssembler = majorAssembler;
    }

    @Override
    public MajorDTO createMajor(Long collegeId, String majorName) {
        // 判断这个学院是否存在
        collegeService.getCollege(collegeId);

        // 判断这个专业名是否已经存在
        MajorDO majorDO = majorMapper.selectByMajorName(majorName);
        if (majorDO != null) {
            throw new DuplicateServiceException("The majorName already exist.");
        }

        // 保存到数据库
        MajorDO majorDOForInsert = MajorDO.builder().collegeId(collegeId).majorName(majorName).build();
        majorMapper.insert(majorDOForInsert);
        return getMajor(majorDOForInsert.getId());
    }

    @Override
    public MajorDTO getMajor(Long id) {
        MajorDO majorDO = majorMapper.selectById(id);
        if (majorDO == null) {
            throw new NotFoundServiceException("major", "id", id);
        }
        return majorAssembler.majorDOToMajorDTO(majorDO);
    }

    @Override
    public QueryResult<MajorDTO> listMajors(MajorQuery query) {
        LambdaQueryWrapper<MajorDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getCollegeId() != null, MajorDO::getCollegeId, query.getCollegeId())
                .likeRight(query.getMajorName() != null, MajorDO::getMajorName, query.getMajorName());

        Page<MajorDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        majorMapper.selectPage(page, wrapper);
        List<MajorDTO> majorDTOS = page.getRecords()
                .stream().map(majorAssembler::majorDOToMajorDTO).collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), majorDTOS);
    }

    @Override
    public MajorDTO updateMajor(UpdateMajorRequest request) {
        MajorDO majorDO = majorAssembler.updateMajorRequestToMajorDO(request);
        // 更新到数据库
        majorMapper.updateById(majorDO);
        return getMajor(request.getId());
    }

    @Override
    public Integer deactivateMajorsByCollegeId(Long collegeId) {
        MajorDO majorDOForUpdate = MajorDO.builder().deactivated(true).build();
        LambdaUpdateWrapper<MajorDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(MajorDO::getCollegeId, collegeId);
        return majorMapper.update(majorDOForUpdate, wrapper);
    }

}
