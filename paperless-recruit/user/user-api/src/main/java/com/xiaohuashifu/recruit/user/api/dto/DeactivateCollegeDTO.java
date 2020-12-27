package com.xiaohuashifu.recruit.user.api.dto;

import java.io.Serializable;

/**
 * 描述：停用学院的传输对象
 *
 * @author xhsf
 * @create 2020/12/14 16:48
 */
public class DeactivateCollegeDTO implements Serializable {

    /**
     * 学院
     */
    private CollegeDTO collegeDTO;

    /**
     * 被停用的专业数量
     */
    private Integer deactivatedCount;

    public DeactivateCollegeDTO(CollegeDTO collegeDTO, Integer deactivatedCount) {
        this.collegeDTO = collegeDTO;
        this.deactivatedCount = deactivatedCount;
    }

    public CollegeDTO getCollegeDTO() {
        return collegeDTO;
    }

    public void setCollegeDTO(CollegeDTO collegeDTO) {
        this.collegeDTO = collegeDTO;
    }

    public Integer getDeactivatedCount() {
        return deactivatedCount;
    }

    public void setDeactivatedCount(Integer deactivatedCount) {
        this.deactivatedCount = deactivatedCount;
    }

    @Override
    public String toString() {
        return "DeactivateCollegeDTO{" +
                "collegeDTO=" + collegeDTO +
                ", deactivatedCount=" + deactivatedCount +
                '}';
    }
}
