package com.jd.help.center.domain;

import java.io.Serializable;
import java.util.List;

/**
 *  ��ർ������Ŀ.
 * User: xuxianjun
 * Date: 13-7-11
 * Time: ����3:46
 * To change this template use File | Settings | File Templates.
 */
public class HelpCenterCategory implements Serializable {

    /**
     * ������ĿID
     */
    private int categoryId;

    /**
     * ����ϵͳID
     */
    private int sysId;

    /**
     * ��������Ŀ��ʾ����
     */
    private String name;

    /**
     * ��������Ŀ��ʾ����
     */
    private int sortOrder;

    /**
     * �������������б�
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
