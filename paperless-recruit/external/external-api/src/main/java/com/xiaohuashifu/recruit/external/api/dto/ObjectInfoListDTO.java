package com.xiaohuashifu.recruit.external.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：列举对象数据传输对象
 *
 * @author xhsf
 * @create 2020/12/7 20:19
 */
public class ObjectInfoListDTO implements Serializable {

    /**
     * 下一个轮查询的标识，也就是从哪个对象名开始，是完整路径
     * 若为 null 表示已经没有更多的对象了
     */
    private String nextMarker;

    /**
     * 对象信息列表
     */
    private List<ObjectInfoDTO> objectInfos;

    public ObjectInfoListDTO(String nextMarker, List<ObjectInfoDTO> objectInfos) {
        this.nextMarker = nextMarker;
        this.objectInfos = objectInfos;
    }

    public String getNextMarker() {
        return nextMarker;
    }

    public void setNextMarker(String nextMarker) {
        this.nextMarker = nextMarker;
    }

    public List<ObjectInfoDTO> getObjectInfos() {
        return objectInfos;
    }

    public void setObjectInfos(List<ObjectInfoDTO> objectInfos) {
        this.objectInfos = objectInfos;
    }

    @Override
    public String toString() {
        return "ListObjectsResultDTO{" +
                "nextMarker='" + nextMarker + '\'' +
                ", objectInfos=" + objectInfos +
                '}';
    }

}
