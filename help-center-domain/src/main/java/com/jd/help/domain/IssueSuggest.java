package com.jd.help.domain;

import java.util.Date;

/**
 * Created by lipengfei5 on 2016/9/26.
 */
public class IssueSuggest {
    public IssueSuggest(){

    }
    public IssueSuggest(Integer issueId){
        this.issueId = issueId;
    }
    /**
     * ����
     */
    private Integer id;
    /**
     * ����ID
     */
    private Integer issueId;
    /**
     * ����ID
     * eg:VenderId
     */
    private String rFid;
    /**
     * �Ƿ������� 0 ��δ�����1���ѽ��
     */
    private Integer solveStatus;
    /**
     * �Ƿ��������0 ��δ�� ��1 �� ����
     */
    private Integer suggestStatus;
    /**
     * �������
     */
    private String suggestContent;
    /**
     * ������
     */
    private String creator;

    /**
     * �޸���
     */
    private String modifier;

    /**
     * ����ʱ��
     */
    private Date created;

    /**
     * �޸�ʱ��
     */
    private Date modified;
    /**
     * ״̬ 0 ɾ����1 ��Ч
     */
    private Integer status;

    private String unSolveReason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getSuggestStatus() {
        return suggestStatus;
    }

    public void setSuggestStatus(Integer suggestStatus) {
        this.suggestStatus = suggestStatus;
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

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getrFid() {
        return rFid;
    }

    public void setrFid(String rFid) {
        this.rFid = rFid;
    }

    public String getUnSolveReason() {
        return unSolveReason;
    }

    public void setUnSolveReason(String unSolveReason) {
        this.unSolveReason = unSolveReason;
    }

    @Override
    public String toString() {
        return "IssueSuggest{" +
                "id=" + id +
                ", issueId=" + issueId +
                ", rFid='" + rFid + '\'' +
                ", solveStatus=" + solveStatus +
                ", suggestStatus=" + suggestStatus +
                ", suggestContent='" + suggestContent + '\'' +
                ", creator='" + creator + '\'' +
                ", modifier='" + modifier + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", status=" + status +
                ", unSolveReason='" + unSolveReason + '\'' +
                '}';
    }
}
