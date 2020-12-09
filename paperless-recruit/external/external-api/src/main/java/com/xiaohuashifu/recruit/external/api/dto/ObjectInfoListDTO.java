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
    private String nextMarker;
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
