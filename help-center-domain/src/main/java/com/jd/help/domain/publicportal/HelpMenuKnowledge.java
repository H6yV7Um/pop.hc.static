package com.jd.help.domain.publicportal;

import java.util.Date;

public class HelpMenuKnowledge {
    private Integer menuId;

    private String changeKnowledgeId;

    private String newKnowledgeId;

    private String lastUpdatePin;

    private Date lastUpdateTime;

    private String lastUpdateDesc;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getChangeKnowledgeId() {
        return changeKnowledgeId;
    }

    public void setChangeKnowledgeId(String changeKnowledgeId) {
        this.changeKnowledgeId = changeKnowledgeId == null ? null : changeKnowledgeId.trim();
    }

    public String getNewKnowledgeId() {
        return newKnowledgeId;
    }

    public void setNewKnowledgeId(String newKnowledgeId) {
        this.newKnowledgeId = newKnowledgeId == null ? null : newKnowledgeId.trim();
    }

    public String getLastUpdatePin() {
        return lastUpdatePin;
    }

    public void setLastUpdatePin(String lastUpdatePin) {
        this.lastUpdatePin = lastUpdatePin == null ? null : lastUpdatePin.trim();
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateDesc() {
        return lastUpdateDesc;
    }

    public void setLastUpdateDesc(String lastUpdateDesc) {
        this.lastUpdateDesc = lastUpdateDesc == null ? null : lastUpdateDesc.trim();
    }

}