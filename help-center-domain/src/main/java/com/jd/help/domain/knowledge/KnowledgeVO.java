package com.jd.help.domain.knowledge;

import java.util.Date;

/**
 * ����֪ʶ��չʾ�����
 * �����ں���RelationKnowledge��Knowledge
 *
 * @author wangyu1099
 * @date 2018/04/13
 */
public class KnowledgeVO {

    /**
     * ֪ʶID������
     */
    private Long id;

    /**
     * ֪ʶ����
     */
    private String name;

    /**
     * ֪ʶ��������:1-�������� 2-ƽ̨���� 3-����ָ�� 4-�ٷ���Ƶ IssueTypeEnum
     */
    private Integer contentType;

    /**
     * ֪ʶ��������:1-�������� 2-ƽ̨���� 3-����ָ�� 4-�ٷ���Ƶ
     */
    private String contentTypeStr;

    /**
     * ������ǩIDs���Կո�ָ�
     */
    private String label2Ids;

    /**
     * ������ǩ���ƣ��Կո�ָ�
     */
    private String label2Names;

    /**
     * ֪ʶurl
     */
    private String url;

    /**
     * ֪ʶ����ʱ��
     */
    private Date createTime;

    /**
     * �滻��֪ʶ
     */
    private Long replaceKnowledgeId;

    /**
     * ״̬ 1-ǿ���� 0-ϵͳƥ��
     */
    private Integer isStrong;

    /**
     * ����״̬,ҳ��չʾʹ��  �˹�ƥ��/ϵͳƥ��
     */
    private String isStrongStr;

    /**
     * 0: ��Ч���ݣ� 1: ɾ��״̬������
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