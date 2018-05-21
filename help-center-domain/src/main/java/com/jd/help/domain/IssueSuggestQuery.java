package com.jd.help.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 */
public class IssueSuggestQuery implements Serializable{

    /**
     * 问题ID
     */
    private Integer issueId;
    /**
     * 是否解决问题 0 ：未解决，1：已解决
     */
    private Integer solveStatus;

    /**
     * 是否提意见：0 ：未提 ，1 ： 已提
     */
    private Integer suggestStatus;

    /**
     * 创建时间
     */
    private String beginTime;

    /**
     * 修改时间
     */
    private String endTime;
    /**
     * 状态 0 删除，1 有效
     */
    private Integer status;

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public Integer getSolveStatus() {
        return solveStatus;
    }

    public void setSolveStatus(Integer solveStatus) {
        this.solveStatus = solveStatus;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSuggestStatus() {
        return suggestStatus;
    }

    public void setSuggestStatus(Integer suggestStatus) {
        this.suggestStatus = suggestStatus;
    }
}
