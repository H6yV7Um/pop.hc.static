package com.jd.help.domain.knowledge;

import java.util.Date;

/**
 * 关联知识的展示层对象
 * 属性融合了RelationKnowledge和Knowledge
 *
 * @author wangyu1099
 * @date 2018/04/13
 */
public class KnowledgeVO {

    /**
     * 知识ID，主键
     */
    private Long id;

    /**
     * 知识标题
     */
    private String name;

    /**
     * 知识内容类型:1-常见问题 2-平台规则 3-操作指南 4-官方视频 IssueTypeEnum
     */
    private Integer contentType;

    /**
     * 知识内容类型:1-常见问题 2-平台规则 3-操作指南 4-官方视频
     */
    private String contentTypeStr;

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
     * 知识创建时间
     */
    private Date createTime;

    /**
     * 替换的知识
     */
    private Long replaceKnowledgeId;

    /**
     * 状态 1-强关联 0-系统匹配
     */
    private Integer isStrong;

    /**
     * 关联状态,页面展示使用  人工匹配/系统匹配
     */
    private String isStrongStr;

    /**
     * 0: 有效数据， 1: 删除状态的数据
     */
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getLabel2Ids() {
        return label2Ids;
    }

    public void setLabel2Ids(String label2Ids) {
        this.label2Ids = label2Ids;
    }

    public String getLabel2Names() {
        return label2Names;
    }

    public void setLabel2Names(String label2Names) {
        this.label2Names = label2Names;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getReplaceKnowledgeId() {
        return replaceKnowledgeId;
    }

    public void setReplaceKnowledgeId(Long replaceKnowledgeId) {
        this.replaceKnowledgeId = replaceKnowledgeId;
    }

    public Integer getIsStrong() {
        return isStrong;
    }

    public void setIsStrong(Integer isStrong) {
        this.isStrong = isStrong;
    }

    public String getContentTypeStr() {
        return contentTypeStr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setContentTypeStr(String contentTypeStr) {
        this.contentTypeStr = contentTypeStr;
    }

    public String getIsStrongStr() {
        return isStrongStr;
    }

    public void setIsStrongStr(String isStrongStr) {
        this.isStrongStr = isStrongStr;
    }

    @Override
    public String toString() {
        return "KnowledgeVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contentType=" + contentType +
                ", contentTypeStr='" + contentTypeStr + '\'' +
                ", label2Ids='" + label2Ids + '\'' +
                ", label2Names='" + label2Names + '\'' +
                ", url='" + url + '\'' +
                ", createTime=" + createTime +
                ", replaceKnowledgeId=" + replaceKnowledgeId +
                ", isStrong=" + isStrong +
                ", isStrongStr='" + isStrongStr + '\'' +
                ", status=" + status +
                '}';
    }
}