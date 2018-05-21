package com.jd.help.center.domain.topic;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-12-3
 * Time: 11:42:45
 * To change this template use File | Settings | File Templates.
 */
public class HelpTopicVO {

    private int topicId;

    private int categoryId;

    private String name;

    private int sortOrder;

    /**
     * 0:ͣ��
     * 1:����
     */
    private int status;

    //�Ƿ���ʾê��  0����ʾ   1������ʾ
    private int anchorStatus;

    private String notes;

    private String features;

    //������url��ַ
    private String url;

    /**
     *  0:δѡ���ǩ
     *  1:up��ǩ
     *  2:new��ǩ
     *  3:hot��ǩ
     */
    private int logoType;

    private Date created;

    private Date modified;

    public HelpTopicVO(){
        this.anchorStatus=0;
    }

    public HelpTopicVO(HelpTopic topic){
        this.categoryId=topic.getCategoryId();
        this.created=topic.getCreated();
        this.features=topic.getFeatures();
        this.modified=topic.getModified();
        this.name=topic.getName();
        this.notes=topic.getNotes();
        this.sortOrder=topic.getSortOrder();
        this.topicId=topic.getTopicId();
        this.status=topic.getStatus();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getLogoType() {
        return logoType;
    }

    public void setLogoType(int logoType) {
        this.logoType = logoType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAnchorStatus() {
        return anchorStatus;
    }

    public void setAnchorStatus(int anchorStatus) {
        this.anchorStatus = anchorStatus;
    }
}
