package com.jd.help.center.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 左侧导般主题
 * User: xuxianjun
 * Date: 13-7-11
 * Time: 下午3:57
 * To change this template use File | Settings | File Templates.
 */
public class HelpCenterTopic implements Serializable {

    /**
     * 主题ID
     */
    private int topicId;

    /**
     * 类目ID
     */
    private int categoryId;

    /**
     * 主题名称
     */
    private String name;

    /**
     * 当前类目下，主题排序
     */
    private int sortOrder;

    /**
     * 访问连接
     */
    private String url;

    /**
     * 主题问题列表
     */
    private List<HelpCenterQuestion> questionList;

    /**
     *  0:未选择标签
     *  1:up标签
     *  2:new标签
     *  3:hot标签
     */
    private int logoType;


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<HelpCenterQuestion> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<HelpCenterQuestion> questionList) {
        this.questionList = questionList;
    }

    public int getLogoType() {
        return logoType;
    }

    public void setLogoType(int logoType) {
        this.logoType = logoType;
    }
}
