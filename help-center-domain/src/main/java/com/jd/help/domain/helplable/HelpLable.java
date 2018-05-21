package com.jd.help.domain.helplable;

import java.util.Date;
import java.util.List;

public class HelpLable {
    private Integer id;

    private String name;

    private Integer level;

    private String classify;

    private String content;

    private String page;

    private Integer parentId;

    private String status;

    private String createdTime;

    private String createdPin;

    private String modifiedTime;

    private String modifiedPin;
    private List<HelpLable> childList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedPin() {
        return createdPin;
    }

    public void setCreatedPin(String createdPin) {
        this.createdPin = createdPin == null ? null : createdPin.trim();
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getModifiedPin() {
        return modifiedPin;
    }

    public void setModifiedPin(String modifiedPin) {
        this.modifiedPin = modifiedPin == null ? null : modifiedPin.trim();
    }

    public List<HelpLable> getChildList() {
        return childList;
    }

    public void setChildList(List<HelpLable> childList) {
        this.childList = childList;
    }
}