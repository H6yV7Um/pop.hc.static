package com.jd.help.jmq;

import java.io.Serializable;

/**
 * Created by yfxialiang on 2018/4/8.
 * �̼���ѵ�������������£�ɾ���㲥�γ̻ᷢ��MQ��Ϣ
 */
public class UpdateCourseMessage implements Serializable{
    private static final long serialVersionUID = 6673623546781753837L;
    /**
     * �������� 1-����2-ɾ
     * RecommendCourseOpTypeEnum��ʹ�����ö����
     */
    private Integer opType;

    /**
     * �γ�ID
     */
    private Long courseId;

    /**
     * �γ�����
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
