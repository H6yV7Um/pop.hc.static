package com.jd.help.domain.knowledge;

import java.util.Date;

public class Knowledge {
    /**
     * 知识ID，主键
     */
    private Long id;

    /**
     * 知识在原系统的ID
     */
    private Long bizId;

    /**
     * 渠道类型ID：KnowledgeBizTypesEnum
     */
    private Integer bizTypeId;

    /**
     * 渠道类型名称，与biz_type_id对应
     */
    private String bizTypeName;

    /**
     * 知识标题
     */
    private String name;

    /**
     * 知识摘要
     */
    private String summary;

    /**
     * 知识关键词
     */
    private String keyword;

    /**
     * 一级标签IDs，以空格分隔
     */
    private String label1Ids;

    /**
     * 一级标签名称，以空格分隔
     */
    private String label1Names;

    /**
     * 二级标签IDs，以空格分隔
     */
    private String label2Ids;

    /**
     * 二级标签名称，以空格分隔
     */
    private String label2Names;

    /**
     * 知识url
     */
    private String url;

    /**
     * 图片url
     */
    private String picUrl;

    /**
     * 知识创建时间
     */
    private Date createTime;

    /**
     * 知识修改时间
     */
    private Date modifyTime;

    /**
     * 知识生效时间(针对规则)
     */
    private Date validTime;

    /**
     * 知识失效时间(针对帮助中心)
     */
    private Date expTime;

    /**
     * 知识内容类型:1-常见问题 2-平台规则 3-操作指南 4-官方视频 IssueTypeEnum
     */
    private Integer contentType;

    /**
     * 知识被浏览的uv
     */
    private Integer uv;

    /**
     * 知识被浏览的pv
     */
    private Integer pv;

    /**
     * 知识已解决的点击次数
     */
    private Integer solveCount;

    /**
     * 知识未解决的点击次数
     */
    private Integer unsolveCount;

    /**
     * 知识状态，0-正常，1-删除
     */
    private Integer status;

    /**
     * 记录创建时间
     */
    private Date created;

    /**
     * 记录修改时间
     */
    private Date modified;

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

    @Override
    public String toString() {
        return "Knowledge{" +
                "id=" + id +
                ", bizId=" + bizId +
                ", bizTypeId=" + bizTypeId +
                ", bizTypeName='" + bizTypeName + '\'' +
                ", name='" + name + '\'' +
                ", summary='" + summary + '\'' +
                ", keyword='" + keyword + '\'' +
                ", label2Ids='" + label2Ids + '\'' +
                ", label2Names='" + label2Names + '\'' +
                ", url='" + url + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", validTime=" + validTime +
                ", expTime=" + expTime +
                ", contentType=" + contentType +
                ", uv=" + uv +
                ", pv=" + pv +
                ", solveCount=" + solveCount +
                ", unsolveCount=" + unsolveCount +
                ", status=" + status +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}