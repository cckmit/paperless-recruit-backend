package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.facade.service.vo.ObjectInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 描述：Oss 管理器
 *
 * @author xhsf
 * @create 2021/1/30 15:59
 */
public interface ObjectManager {

    /**
     * 预上传对象
     *
     * @param userId 用户编号
     * @param object 对象
     * @param baseObjectName 基础对象名
     * @return 对象信息
     */
    ObjectInfoVO preUploadObject(Long userId, MultipartFile object, String baseObjectName) throws IOException;

}
