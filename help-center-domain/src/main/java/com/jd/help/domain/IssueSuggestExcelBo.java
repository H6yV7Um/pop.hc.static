package com.jd.help.domain;

import com.jd.help.excel.XExcel;

import java.io.Serializable;

/**
 * Created by lining7 on 2018/1/12.
 */
public class IssueSuggestExcelBo implements Serializable {
    /**
     * ����
     */
    @XExcel.XExcelField(order=0)
    private Integer id;

    /**
     * ����
     */
    @XExcel.XExcelField(order=1)
    private String name;
    /**
     * δ���ԭ��
     */
    @XExcel.XExcelField(order=2)
    private String unSolveReason;

    /**
     * ����
     */
    @XExcel.XExcelField(order=3)
    private String suggestContent;
    /**
     * ������
     */
    @XExcel.XExcelField(order=4)
    private String creator;

    /**
     * ����ʱ��
     */
    @XExcel.XExcelField(order=5)
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnSolveReason() {
        return unSolveReason;
    }

    public void setUnSolveReason(String unSolveReason) {
        this.unSolveReason = unSolveReason;
    }

    public String getSuggestContent() {
        return suggestContent;
    }

    public void setSuggestContent(String suggestContent) {
        this.suggestContent = suggestContent;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
