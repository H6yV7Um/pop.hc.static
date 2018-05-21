package com.jd.help.domain;

import java.util.Date;

/**
 * 关联知识
 * A 知识(通过产品给的规则)智能关联了 B.C.D.E.F知识，运营人员手动将B知识替换成了H知识
 * H知识称之为A知识的强关联知识，强关联知识一直存在
 * C.D.E.F称之为A知识的弱关联知识，弱关联知识一段时间后自动失效
 * 被删除或者替换的知识不出现在以后A知识的推荐列表中
 *
 * @author wangyu1099
 * @date 2018/04/12
 */
public class RelationKnowledge {
    /**
     * 关联知识ID，主键，与Knowledge表中的id相等
     * 指上述H知识
     */
    private Long id;

    /**
     * 关联的知识id
     * 指上述A知识
     */
    private Long relationKnowledgeId;

    /**
     * 被替换的关联知识id
     * 指上述B知识
     * mysql默认值0
     */
    private Long replaceKnowledgeId;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 菜单id
     */
    private Integer menuId;

    /**
     * 关联知识状态
     * 0：关联中  1：删除状态
     * mysql设置默认值0
     */
    private Integer status;

    /**
     * 是否是强关联知识 1：是  0：不是
     * mysql设置默认值0
     */
    private Integer isStrong;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRelationKnowledgeId() {
        return relationKnowledgeId;
    }

    public void setRelationKnowledgeId(Long relationKnowledgeId) {
        this.relationKnowledgeId = relationKnowledgeId;
    }

    public Long getReplaceKnowledgeId() {
        return replaceKnowledgeId;
    }

    public void setReplaceKnowledgeId(Long replaceKnowledgeId) {
        this.replaceKnowledgeId = replaceKnowledgeId;
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

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsStrong() {
        return isStrong;
    }

    public void setIsStrong(Integer isStrong) {
        this.isStrong = isStrong;
    }

    @Override
    public String toString() {
        return "RelationKnowledge{" +
                "id=" + id +
                ", relationKnowledgeId=" + relationKnowledgeId +
                ", replaceKnowledgeId=" + replaceKnowledgeId +
                ", created=" + created +
                ", modified=" + modified +
                ", menuId=" + menuId +
                ", status=" + status +
                ", isStrong=" + isStrong +
                '}';
    }
}