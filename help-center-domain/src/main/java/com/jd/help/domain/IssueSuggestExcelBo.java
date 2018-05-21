package com.jd.help.domain;

import com.jd.help.excel.XExcel;

import java.io.Serializable;

/**
 * Created by lining7 on 2018/1/12.
 */
public class IssueSuggestExcelBo implements Serializable {
    /**
     * 主键
     */
    @XExcel.XExcelField(order=0)
    private Integer id;

    /**
     * 问题
     */
    @XExcel.XExcelField(order=1)
    private String name;
    /**
     * 未解决原因
     */
    @XExcel.XExcelField(order=2)
    private String unSolveReason;

    /**
     * 建议
     */
    @XExcel.XExcelField(order=3)
    private String suggestContent;
    /**
     * 创建者
     */
    @XExcel.XExcelField(order=4)
    private String creator;

    /**
     * 创建时间
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
