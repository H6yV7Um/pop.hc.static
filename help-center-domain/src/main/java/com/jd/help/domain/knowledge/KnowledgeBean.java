package com.jd.help.domain.knowledge;

import java.util.Date;

/**
 * Created by yfxialiang on 2018/4/2.
 * 比Knowledge多了一个content字段
 */
public class KnowledgeBean {

    private Long id;

    private Long bizId;

    private Integer bizTypeId;

    private String bizTypeName;

    private String name;

    private String summary;

    private String keyword;

    private String label1Ids;

    private String label1Names;

    private String label2Ids;

    private String label2Names;

    private String url;

    private String picUrl;

    private Date createTime;

    private Date modifyTime;

    private Date validTime;

    private Date expTime;

    private Integer contentType;

    private Integer uv;

    private Integer pv;

    private Integer solveCount;

    private Integer unsolveCount;

    private Integer status;

    private Date created;

    private Date modified;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public Integer getBizTypeId() {
        return bizTypeId;
    }

    public void setBizTypeId(Integer bizTypeId) {
        this.bizTypeId = bizTypeId;
    }

    public String getBizTypeName() {
        return bizTypeName;
    }

    public void setBizTypeName(String bizTypeName) {
        this.bizTypeName = bizTypeName == null ? null : bizTypeName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public String getLabel2Ids() {
        return label2Ids;
    }

    public void setLabel2Ids(String label2Ids) {
        this.label2Ids = label2Ids == null ? null : label2Ids.trim();
    }

    public String getLabel2Names() {
        return label2Names;
    }

    public void setLabel2Names(String label2Names) {
        this.label2Names = label2Names == null ? null : label2Names.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public Date getExpTime() {
        return expTime;
    }

    public void setExpTime(Date expTime) {
        this.expTime = expTime;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getUv() {
        return uv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public Integer getPv() {
        return pv;
    }

    public void setPv(Integer pv) {
        this.pv = pv;
    }

    public Integer getSolveCount() {
        return solveCount;
    }

    public void setSolveCount(Integer solveCount) {
        this.solveCount = solveCount;
    }

    public Integer getUnsolveCount() {
        return unsolveCount;
    }

    public void setUnsolveCount(Integer unsolveCount) {
        this.unsolveCount = unsolveCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabel1Ids() {
        return label1Ids;
    }

    public void setLabel1Ids(String label1Ids) {
        this.label1Ids = label1Ids;
    }

    public String getLabel1Names() {
        return label1Names;
    }

    public void setLabel1Names(String label1Names) {
        this.label1Names = label1Names;
    }
}
