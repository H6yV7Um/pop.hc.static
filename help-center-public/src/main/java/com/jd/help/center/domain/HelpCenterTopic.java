package com.jd.help.center.domain;

import java.io.Serializable;
import java.util.List;

/**
 * ��ർ������
 * User: xuxianjun
 * Date: 13-7-11
 * Time: ����3:57
 * To change this template use File | Settings | File Templates.
 */
public class HelpCenterTopic implements Serializable {

    /**
     * ����ID
     */
    private int topicId;

    /**
     * ��ĿID
     */
    private int categoryId;

    /**
     * ��������
     */
    private String name;

    /**
     * ��ǰ��Ŀ�£���������
     */
    private int sortOrder;

    /**
     * ��������
     */
    private String url;

    /**
     * ���������б�
     */
    private List<HelpCenterQuestion> questionList;

    /**
     *  0:δѡ���ǩ
     *  1:up��ǩ
     *  2:new��ǩ
     *  3:hot��ǩ
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
