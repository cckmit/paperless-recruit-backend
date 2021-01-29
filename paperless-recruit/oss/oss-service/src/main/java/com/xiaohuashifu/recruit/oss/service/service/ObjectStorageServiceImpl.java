package com.xiaohuashifu.recruit.oss.service.service;

import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

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

    private final TransactionDefinition transactionDefinition;

    private final PlatformTransactionManager transactionManager;

    /**
     * 定时清理未连接对象任务初始延迟
     */
    private static final long CLEAR_UNLINKED_OBJECTS_INITIAL_DELAY = 5000;

    /**
     * 定时清理未连接对象任务固定延迟
     */
    private static final long CLEAR_UNLINKED_OBJECTS_FIXED_DELAY = 5000;

    /**
     * 定时清理未连接对象任务每次清除数量
     */
    private static final int CLEAR_UNLINKED_OBJECTS_PAGE_SIZE = 5;

    public ObjectStorageServiceImpl(ObjectInfoAssembler objectInfoAssembler, ObjectInfoMapper objectInfoMapper,
                                    ObjectStorageManager objectStorageManager,
                                    TransactionDefinition transactionDefinition,
                                    PlatformTransactionManager transactionManager) {
        this.objectInfoAssembler = objectInfoAssembler;
        this.objectInfoMapper = objectInfoMapper;
        this.objectStorageManager = objectStorageManager;
        this.transactionDefinition = transactionDefinition;
        this.transactionManager = transactionManager;
    }

    @Override
    public Result<ObjectInfoResponse> preUploadObject(PreUploadObjectRequest request) {
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        try {
            // 判断对象名是否存在
            ObjectInfoDO objectInfoDO = objectInfoMapper.selectByObjectName(request.getObjectName());
            if (objectInfoDO != null) {
                return Result.fail(ErrorCodeEnum.UNPROCESSABLE_ENTITY_EXIST,
                        "The objectName already exist.");
            }

            // 存储对象信息
            ObjectInfoDO newObjectInfoDO = objectInfoAssembler.preUploadObjectRequestToObjectInfoDO(request);
            objectInfoMapper.insert(newObjectInfoDO);

            // 存储到 oss
            objectStorageManager.putObject(request.getObjectName(), request.getObject());

            // 提交事务
            transactionManager.commit(transactionStatus);
            return getObjectInfo(newObjectInfoDO.getId());
        } catch (RuntimeException e) {
            log.error("Pre upload object error. request=" + request, e);
            transactionManager.rollback(transactionStatus);
            return Result.fail(ErrorCodeEnum.INTERNAL_ERROR);
        }
    }

    @Override
    public Result<Void> deleteObject(String objectName) {
        return deleteObject(objectInfoMapper.selectByObjectName(objectName));
    }

    @Override
    public Result<Void> deleteObject(Long id) {
        return deleteObject(objectInfoMapper.selectById(id));
    }

    @Override
    public Result<ObjectInfoResponse> getObjectInfo(String objectName) {
        ObjectInfoDO objectInfoDO = objectInfoMapper.selectByObjectName(objectName);
        if (objectInfoDO == null) {
            return Result.fail(ErrorCodeEnum.NOT_FOUND);
        }
        return Result.success(objectInfoAssembler.objectInfoDOToObjectInfoResponse(objectInfoDO));
    }

    @Override
    public Result<ObjectInfoResponse> getObjectInfo(Long id) {
        ObjectInfoDO objectInfoDO = objectInfoMapper.selectById(id);
        if (objectInfoDO == null) {
            return Result.fail(ErrorCodeEnum.NOT_FOUND);
        }
        return Result.success(objectInfoAssembler.objectInfoDOToObjectInfoResponse(objectInfoDO));
    }

    @Override
    public Result<QueryResult<ObjectInfoResponse>> listObjectInfos(ListObjectInfosRequest request) {
        QueryResult<ObjectInfoDO> objectInfoDOQueryResult = objectInfoMapper.selectList(request);
        List<ObjectInfoResponse> objectInfoResponses = objectInfoDOQueryResult.getResult().stream()
                .map(objectInfoAssembler::objectInfoDOToObjectInfoResponse)
                .collect(Collectors.toList());
        QueryResult<ObjectInfoResponse> objectInfoResponseQueryResult =
                new QueryResult<>(objectInfoDOQueryResult.getTotalCount(), objectInfoResponses);
        return Result.success(objectInfoResponseQueryResult);
    }

    @Override
    public Result<ObjectInfoResponse> linkObject(String objectName) {
        return linkObject(objectInfoMapper.selectByObjectName(objectName));
    }

    @Override
    public Result<ObjectInfoResponse> linkObject(Long id) {
        return linkObject(objectInfoMapper.selectById(id));
    }

    /**
     * 链接对象
     *
     * @errorCode UnprocessableEntity.NotExist 所要链接的对象不存在
     *              OperationConflict.Linked 对象已经链接
     *              OperationConflict.Deleted 对象已经删除
     *              InternalError 链接对象失败
     *
     * @param objectInfoDO 对象信息，从数据库里查询得到
     * @return 链接后的对象
     */
    private Result<ObjectInfoResponse> linkObject(ObjectInfoDO objectInfoDO) {
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        try {
            // 判断对象是否存在
            if (objectInfoDO == null) {
                return Result.fail(ErrorCodeEnum.UNPROCESSABLE_ENTITY_NOT_EXIST,
                        "The object does not exist.");
            }

            // 判断对象是否已经删除
            if (objectInfoDO.getDeleted()) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DELETED,
                        "The object already deleted.");
            }

            // 判断对象是否已经链接
            if (objectInfoDO.getLinked()) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_LINKED,
                        "The object already linked.");
            }

            // 链接对象
            ObjectInfoDO objectInfoDOForUpdate = ObjectInfoDO.builder().id(objectInfoDO.getId()).linked(true).build();
            objectInfoMapper.updateById(objectInfoDOForUpdate);

            // 提交事务
            transactionManager.commit(transactionStatus);
            return getObjectInfo(objectInfoDO.getId());
        } catch (RuntimeException e) {
            log.error("Linked object error. objectInfoDO=" + objectInfoDO, e);
            transactionManager.rollback(transactionStatus);
            return Result.fail(ErrorCodeEnum.INTERNAL_ERROR);
        }
    }

    /**
     * 删除对象
     *
     * @errorCode UnprocessableEntity.NotExist 所要删除的对象不存在
     *              OperationConflict.Deleted 对象已经删除
     *              InternalError 删除对象失败
     *
     * @param objectInfoDO 对象信息，从数据库里查询得到
     * @return 删除结果
     */
    private Result<Void> deleteObject(ObjectInfoDO objectInfoDO) {
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        try {
            // 判断对象是否存在
            if (objectInfoDO == null) {
                return Result.fail(ErrorCodeEnum.UNPROCESSABLE_ENTITY_NOT_EXIST,
                        "The object does not exist.");
            }

            // 判断对象是否已经删除
            if (objectInfoDO.getDeleted()) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DELETED,
                        "The object already deleted.");
            }

            // 更新对象 deleted 字段为 true
            ObjectInfoDO objectInfoDOForUpdate = ObjectInfoDO.builder().id(objectInfoDO.getId()).deleted(true).build();
            objectInfoMapper.updateById(objectInfoDOForUpdate);

            // 删除 oss 的对象
            objectStorageManager.deleteObject(objectInfoDO.getObjectName());

            // 提交事务
            transactionManager.commit(transactionStatus);
            return Result.success();
        } catch (RuntimeException e) {
            log.error("Delete object error. objectInfoDO=" + objectInfoDO, e);
            transactionManager.rollback(transactionStatus);
            return Result.fail(ErrorCodeEnum.INTERNAL_ERROR);
        }
    }

    /**
     * 清除未链接的对象
     */
    @Scheduled(initialDelay = CLEAR_UNLINKED_OBJECTS_INITIAL_DELAY, fixedDelay = CLEAR_UNLINKED_OBJECTS_FIXED_DELAY)
    public void clearUnlinkedObjects() {
        List<ObjectInfoDO> objectInfoDOS = objectInfoMapper.selectNeedClearObject(CLEAR_UNLINKED_OBJECTS_PAGE_SIZE);
        for (ObjectInfoDO objectInfoDO : objectInfoDOS) {
            deleteObject(objectInfoDO);
        }
    }

}
