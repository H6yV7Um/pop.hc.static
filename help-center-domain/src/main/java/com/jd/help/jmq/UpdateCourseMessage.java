package com.jd.help.jmq;

import java.io.Serializable;

/**
 * Created by yfxialiang on 2018/4/8.
 * 商家培训中心新增，更新，删除点播课程会发此MQ消息
 */
public class UpdateCourseMessage implements Serializable{
    private static final long serialVersionUID = 6673623546781753837L;
    /**
     * 操作类型 1-增，2-删
     * RecommendCourseOpTypeEnum，使用这个枚举类
     */
    private Integer opType;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 课程类型
     */
    private int courseType;

    public UpdateCourseMessage() {
    }

    public UpdateCourseMessage(Integer opType, Long courseId, int courseType) {
        this.opType = opType;
        this.courseId = courseId;
        this.courseType = courseType;
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }
}
