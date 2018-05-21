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
     * 主键
     */
    private Integer id;
    /**
     * 问题ID
     */
    private Integer issueId;
    /**
     * 关联ID
     * eg:VenderId
     */
    private String rFid;
    /**
     * 是否解决问题 0 ：未解决，1：已解决
     */
    private Integer solveStatus;
    /**
     * 是否提意见：0 ：未提 ，1 ： 已提
     */
    private Integer suggestStatus;
    /**
     * 意见内容
     */
    private String suggestContent;
    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;
    /**
     * 状态 0 删除，1 有效
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
