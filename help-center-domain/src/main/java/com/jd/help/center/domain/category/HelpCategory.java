package com.jd.help.center.domain.category;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-1
 * Time: 13:43:18
 * To change this template use File | Settings | File Templates.
 */
public class HelpCategory {

    private int categoryId;

    private int fid;

    private String name;

    private int status;

    private int sortOrder;

    private String notes;

    private Date created;

    private Date modified;

    private List topics;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
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

    public List getTopics() {
        return topics;
    }

    public void setTopics(List topics) {
        this.topics = topics;
    }
}
