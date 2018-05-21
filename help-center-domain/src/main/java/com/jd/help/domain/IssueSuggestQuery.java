package com.jd.help.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 */
public class IssueSuggestQuery implements Serializable{

    /**
     * ����ID
     */
    private Integer issueId;
    /**
     * �Ƿ������� 0 ��δ�����1���ѽ��
     */
    private Integer solveStatus;

    /**
     * �Ƿ��������0 ��δ�� ��1 �� ����
     */
    private Integer suggestStatus;

    /**
     * ����ʱ��
     */
    private String beginTime;

    /**
     * �޸�ʱ��
     */
    private String endTime;
    /**
     * ״̬ 0 ɾ����1 ��Ч
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
