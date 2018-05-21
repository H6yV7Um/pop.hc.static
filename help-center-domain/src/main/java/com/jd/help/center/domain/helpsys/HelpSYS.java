package com.jd.help.center.domain.helpsys;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-8-3
 * Time: 17:05:39
 * To change this template use File | Settings | File Templates.
 */
public class HelpSYS {
    private int id;
    private String name;
    private int sortOrder;
    private int status;
    private String notes;
    private Date created;
    private Date modified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
