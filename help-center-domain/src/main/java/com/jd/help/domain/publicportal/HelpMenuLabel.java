package com.jd.help.domain.publicportal;

import java.util.Date;
import java.util.List;

public class HelpMenuLabel {
    private Integer menuId;

    private String menuDesc;

    private String labelIds;

    private String labelNames;

    private String label1Ids;

    private String label1Names;

    private List<String> labelNameList;

    private String createdPin;

    private Date createdTime;

    private String modifiedPin;

    private Date modifiedTime;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc == null ? null : menuDesc.trim();
    }

    public String getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(String labelIds) {
        this.labelIds = labelIds == null ? null : labelIds.trim();
    }

    public String getLabelNames() {
        return labelNames;
    }

    public void setLabelNames(String labelNames) {
        this.labelNames = labelNames == null ? null : labelNames.trim();
    }

    public String getCreatedPin() {
        return createdPin;
    }

    public void setCreatedPin(String createdPin) {
        this.createdPin = createdPin == null ? null : createdPin.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedPin() {
        return modifiedPin;
    }

    public void setModifiedPin(String modifiedPin) {
        this.modifiedPin = modifiedPin == null ? null : modifiedPin.trim();
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public List<String> getLabelNameList() {
        return labelNameList;
    }

    public void setLabelNameList(List<String> labelNameList) {
        this.labelNameList = labelNameList;
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
}