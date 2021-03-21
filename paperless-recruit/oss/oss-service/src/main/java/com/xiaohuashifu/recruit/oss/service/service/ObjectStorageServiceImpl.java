package com.xiaohuashifu.recruit.oss.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnmodifiedServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.oss.api.request.ListObjectInfosRequest;
import com.xiaohuashifu.recruit.oss.api.request.PreUploadObjectRequest;
import com.xiaohuashifu.recruit.oss.api.response.ObjectInfoResponse;
import com.xiaohuashifu.recruit.oss.api.service.ObjectStorageService;
import com.xiaohuashifu.recruit.oss.service.assembler.ObjectInfoAssembler;
import com.xiaohuashifu.recruit.oss.service.dao.ObjectInfoMapper;
import com.xiaohuashifu.recruit.oss.service.do0.ObjectInfoDO;
import com.xiaohuashifu.recruit.oss.service.manager.ObjectStorageManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.aop.framework.AopContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：对象存储服务
 *
 * @author xhsf
 * @create 2021/1/29 00:17
 */
@Service
@Slf4j
public class ObjectStorageServiceImpl implements ObjectStorageService {

    private final ObjectInfoAssembler objectInfoAssembler;

    private final ObjectInfoMapper objectInfoMapper;

    private final ObjectStorageManager objectStorageManager;

    /**
     * 定时清理未连接对象任务初始延迟
     */
    private static final long CLEAR_UNLINKED_OBJECTS_INITIAL_DELAY = 1000 * 60 * 60 * 24;

    /**
     * 定时清理未连接对象任务固定延迟
     */
    private static final long CLEAR_UNLINKED_OBJECTS_FIXED_DELAY = 1000 * 60 * 60 * 24;

    /**
     * 定时清理未连接对象任务每次清除数量
     */
    private static final int CLEAR_UNLINKED_OBJECTS_PAGE_SIZE = 5;

    public ObjectStorageServiceImpl(ObjectInfoAssembler objectInfoAssembler, ObjectInfoMapper objectInfoMapper,
                                    ObjectStorageManager objectStorageManager) {
        this.objectInfoAssembler = objectInfoAssembler;
        this.objectInfoMapper = objectInfoMapper;
        this.objectStorageManager = objectStorageManager;
    }

    @Override
    @Transactional
    public ObjectInfoResponse preUploadObject(PreUploadObjectRequest request) {
        // 判断对象名是否存在
        ObjectInfoDO objectInfoDO = objectInfoMapper.selectByObjectName(request.getObjectName());
        if (objectInfoDO != null) {
            throw new DuplicateServiceException("The objectName already exist.");
        }

        // 存储对象信息
        ObjectInfoDO objectInfoDOForInsert = objectInfoAssembler.preUploadObjectRequestToObjectInfoDO(request);
        objectInfoMapper.insert(objectInfoDOForInsert);

        // 存储到 oss
        objectStorageManager.putObject(request.getObjectName(), request.getObject());

        return getObjectInfo(objectInfoDOForInsert.getId());
    }

    @Override
    @Transactional
    public void deleteObject(String objectName) {
        ObjectInfoResponse objectInfoResponse = getObjectInfo(objectName);
        ((ObjectStorageServiceImpl)AopContext.currentProxy()).deleteObject(objectInfoResponse);
    }

    @Override
    @Transactional
    public void deleteObject(Long id) {
        ObjectInfoResponse objectInfoResponse = getObjectInfo(id);
        ((ObjectStorageServiceImpl)AopContext.currentProxy()).deleteObject(objectInfoResponse);
    }

    @Override
    public ObjectInfoResponse getObjectInfo(String objectName) {
        ObjectInfoDO objectInfoDO = objectInfoMapper.selectByObjectName(objectName);
        if (objectInfoDO == null) {
            throw new NotFoundServiceException("objectInfo", "objectName", objectName);
        }
        return objectInfoAssembler.objectInfoDOToObjectInfoResponse(objectInfoDO);
    }

    @Override
    public ObjectInfoResponse getObjectInfo(Long id) {
        ObjectInfoDO objectInfoDO = objectInfoMapper.selectById(id);
        if (objectInfoDO == null) {
            throw new NotFoundServiceException("objectInfo", "id", id);
        }
        return objectInfoAssembler.objectInfoDOToObjectInfoResponse(objectInfoDO);
    }

    @Override
    public QueryResult<ObjectInfoResponse> listObjectInfos(ListObjectInfosRequest request) {
        LambdaQueryWrapper<ObjectInfoDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(request.getBaseObjectName() != null, ObjectInfoDO::getObjectName,
                request.getBaseObjectName());
        Page<ObjectInfoDO> page = new Page<>(request.getPageNum(), request.getPageSize(), true);
        objectInfoMapper.selectPage(page, wrapper);

        List<ObjectInfoResponse> objectInfoResponses = page.getRecords().stream()
                .map(objectInfoAssembler::objectInfoDOToObjectInfoResponse)
                .collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), objectInfoResponses);
    }

    @Override
    @Transactional
    public ObjectInfoResponse linkObject(String objectName) {
        ObjectInfoResponse objectInfoResponse = getObjectInfo(objectName);
        return ((ObjectStorageServiceImpl)AopContext.currentProxy()).linkObject(objectInfoResponse);
    }

    @Override
    @Transactional
    public ObjectInfoResponse linkObject(Long id) {
        ObjectInfoResponse objectInfoResponse = getObjectInfo(id);
        return ((ObjectStorageServiceImpl)AopContext.currentProxy()).linkObject(objectInfoResponse);
    }

    /**
     * 链接对象
     *
     * @param objectInfoResponse ObjectInfoResponse
     * @return 链接后的对象
     */
    @Transactional
    protected ObjectInfoResponse linkObject(ObjectInfoResponse objectInfoResponse) throws UnmodifiedServiceException {
        // 链接对象
        ObjectInfoDO objectInfoDOForUpdate = ObjectInfoDO.builder().linked(true).build();
        LambdaUpdateWrapper<ObjectInfoDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ObjectInfoDO::getDeleted, false)
                .eq(ObjectInfoDO::getId, objectInfoResponse.getId());
        int count = objectInfoMapper.update(objectInfoDOForUpdate, wrapper);
        if (count < 1) {
            throw new UnmodifiedServiceException("The object already deleted.");
        }

        return getObjectInfo(objectInfoResponse.getId());
    }

    /**
     * 删除对象
     *
     * @param objectInfoResponse ObjectInfoResponse
     */
    @Transactional
    protected void deleteObject(ObjectInfoResponse objectInfoResponse) throws SecurityException {
        // 更新对象 deleted 字段为 true
        ObjectInfoDO objectInfoDOForUpdate = ObjectInfoDO.builder().deleted(true).build();
        LambdaUpdateWrapper<ObjectInfoDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ObjectInfoDO::getDeleted, false)
                .eq(ObjectInfoDO::getId, objectInfoResponse.getId());
        int count = objectInfoMapper.update(objectInfoDOForUpdate, wrapper);
        if (count < 1) {
            throw new UnmodifiedServiceException("The object already deleted.");
        }

        // 删除 oss 的对象
        objectStorageManager.deleteObject(objectInfoResponse.getObjectName());
    }

    /**
     * 清除未链接的对象
     */
    @Scheduled(initialDelay = CLEAR_UNLINKED_OBJECTS_INITIAL_DELAY, fixedDelay = CLEAR_UNLINKED_OBJECTS_FIXED_DELAY)
    public void clearUnlinkedObjects() {
        List<ObjectInfoDO> objectInfoDOS = objectInfoMapper.selectNeedClearObject(CLEAR_UNLINKED_OBJECTS_PAGE_SIZE);
        for (ObjectInfoDO objectInfoDO : objectInfoDOS) {
            deleteObject(objectInfoAssembler.objectInfoDOToObjectInfoResponse(objectInfoDO));
        }
    }

}
