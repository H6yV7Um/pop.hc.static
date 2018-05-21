package com.jd.help.center.domain.topic;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-1
 * Time: 13:47:19
 * To change this template use File | Settings | File Templates.
 */
public class HelpTopic {

    private int topicId;

    private int categoryId;

    private String name;

    private int sortOrder;

    private int status;

    private String notes;

    private String features;

    private Date created;

    private Date modified;

    public HelpTopic(){
    }

    public HelpTopic(HelpTopicVO helpTopicVO){
        this.setStatus(helpTopicVO.getStatus());
        this.setCategoryId(helpTopicVO.getCategoryId());
        this.setCreated(helpTopicVO.getCreated());
        this.setModified(helpTopicVO.getModified());
        this.setName(helpTopicVO.getName());
        this.setNotes(helpTopicVO.getNotes());
        this.setSortOrder(helpTopicVO.getSortOrder());
        this.setTopicId(helpTopicVO.getTopicId());
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }
}
