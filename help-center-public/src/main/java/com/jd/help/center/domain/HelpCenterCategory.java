package com.jd.help.center.domain;

import java.io.Serializable;
import java.util.List;

/**
 *  左侧导航分类目.
 * User: xuxianjun
 * Date: 13-7-11
 * Time: 下午3:46
 * To change this template use File | Settings | File Templates.
 */
public class HelpCenterCategory implements Serializable {

    /**
     * 帮助类目ID
     */
    private int categoryId;

    /**
     * 帮助系统ID
     */
    private int sysId;

    /**
     * 导航分类目显示名称
     */
    private String name;

    /**
     * 导航分类目显示排序
     */
    private int sortOrder;

    /**
     * 导航问题主题列表
     */
    private List<HelpCenterTopic> topicList;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSysId() {
        return sysId;
    }

    public void setSysId(int sysId) {
        this.sysId = sysId;
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

    public List<HelpCenterTopic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<HelpCenterTopic> topicList) {
        this.topicList = topicList;
    }
}
