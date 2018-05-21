package com.jd.help.domain;

import java.util.Date;

/**
 * ����֪ʶ
 * A ֪ʶ(ͨ����Ʒ���Ĺ���)���ܹ����� B.C.D.E.F֪ʶ����Ӫ��Ա�ֶ���B֪ʶ�滻����H֪ʶ
 * H֪ʶ��֮ΪA֪ʶ��ǿ����֪ʶ��ǿ����֪ʶһֱ����
 * C.D.E.F��֮ΪA֪ʶ��������֪ʶ��������֪ʶһ��ʱ����Զ�ʧЧ
 * ��ɾ�������滻��֪ʶ���������Ժ�A֪ʶ���Ƽ��б���
 *
 * @author wangyu1099
 * @date 2018/04/12
 */
public class RelationKnowledge {
    /**
     * ����֪ʶID����������Knowledge���е�id���
     * ָ����H֪ʶ
     */
    private Long id;

    /**
     * ������֪ʶid
     * ָ����A֪ʶ
     */
    private Long relationKnowledgeId;

    /**
     * ���滻�Ĺ���֪ʶid
     * ָ����B֪ʶ
     * mysqlĬ��ֵ0
     */
    private Long replaceKnowledgeId;

    /**
     * ����ʱ��
     */
    private Date created;

    /**
     * �޸�ʱ��
     */
    private Date modified;

    /**
     * �˵�id
     */
    private Integer menuId;

    /**
     * ����֪ʶ״̬
     * 0��������  1��ɾ��״̬
     * mysql����Ĭ��ֵ0
     */
    private Integer status;

    /**
     * �Ƿ���ǿ����֪ʶ 1����  0������
     * mysql����Ĭ��ֵ0
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