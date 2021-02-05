package com.xiaohuashifu.recruit.user.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.user.api.dto.CollegeDTO;
import com.xiaohuashifu.recruit.user.api.dto.DeactivateCollegeDTO;
import com.xiaohuashifu.recruit.user.api.query.CollegeQuery;
import com.xiaohuashifu.recruit.user.api.service.CollegeService;
import com.xiaohuashifu.recruit.user.api.service.MajorService;
import com.xiaohuashifu.recruit.user.service.assembler.CollegeAssembler;
import com.xiaohuashifu.recruit.user.service.dao.CollegeMapper;
import com.xiaohuashifu.recruit.user.service.do0.CollegeDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：学院专业相关操作的服务
 *
 * @author: xhsf
 * @create: 2020/11/23 21:01
 */
@Service
public class CollegeServiceImpl implements CollegeService {

    private final CollegeMapper collegeMapper;

    private final CollegeAssembler collegeAssembler;

    @Reference
    private MajorService majorService;

    public CollegeServiceImpl(CollegeMapper collegeMapper, CollegeAssembler collegeAssembler) {
        this.collegeMapper = collegeMapper;
        this.collegeAssembler = collegeAssembler;
    }

    @Override
    public CollegeDTO createCollege(String collegeName) {
        // 判断是否已经存在这个学院
        CollegeDO collegeDO = collegeMapper.selectByCollegeName(collegeName);
        if (collegeDO != null) {
            throw new DuplicateServiceException("This collegeName already exist.");
        }

        // 添加到数据库
        CollegeDO collegeDOForInsert = CollegeDO.builder().collegeName(collegeName).build();
        collegeMapper.insert(collegeDOForInsert);
        return getCollege(collegeDOForInsert.getId());
    }

    @Override
    public CollegeDTO getCollege(Long id) {
        CollegeDO collegeDO = collegeMapper.selectById(id);
        if (collegeDO == null) {
            throw new NotFoundServiceException("college", "id", id);
        }
        return collegeAssembler.collegeDOToCollegeDTO(collegeDO);
    }

    @Override
    public QueryResult<CollegeDTO> listColleges(CollegeQuery query) {
        LambdaQueryWrapper<CollegeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(query.getCollegeName() != null, CollegeDO::getCollegeName, query.getCollegeName());

        Page<CollegeDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        collegeMapper.selectPage(page, wrapper);
        List<CollegeDTO> collegeDTOS = page.getRecords()
                .stream().map(collegeAssembler::collegeDOToCollegeDTO).collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), collegeDTOS);
    }

    @Override
    public CollegeDTO updateCollegeName(Long id, String collegeName) {
        // 查看新学院名是否存在
        CollegeDO collegeDO = collegeMapper.selectByCollegeName(collegeName);
        if (collegeDO != null) {
            throw new DuplicateServiceException("The collegeName already exist.");
        }

        // 更新到数据库
        CollegeDO collegeDOForUpdate = CollegeDO.builder().id(id).collegeName(collegeName).build();
        collegeMapper.updateById(collegeDOForUpdate);
        return getCollege(id);
    }

    @Override
    @Transactional
    public DeactivateCollegeDTO deactivateCollege(Long id) {
        // 判断学院是否存在
        getCollege(id);

        // 更新学院的为停用
        CollegeDO collegeDOForUpdate = CollegeDO.builder().id(id).deactivated(true).build();
        collegeMapper.updateById(collegeDOForUpdate);

        // 停用该学院的所有专业
        Integer deactivatedCount = majorService.deactivateMajorsByCollegeId(id);

        // 停用成功
        return new DeactivateCollegeDTO(getCollege(id), deactivatedCount);
    }

}
