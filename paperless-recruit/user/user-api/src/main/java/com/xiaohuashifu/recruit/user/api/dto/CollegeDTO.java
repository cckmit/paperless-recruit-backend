package com.xiaohuashifu.recruit.user.api.dto;

import java.io.Serializable;

/**
 * 描述：学院传输对象
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public class CollegeDTO implements Serializable {

    /**
     * 学院编号
     */
    private Long id;

    /**
     * 学院名
     */
    private String collegeName;

    public CollegeDTO(Long id, String collegeName) {
        this.id = id;
        this.collegeName = collegeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    @Override
    public String toString() {
        return "CollegeDTO{" +
                "id=" + id +
                ", collegeName='" + collegeName + '\'' +
                '}';
    }

}
