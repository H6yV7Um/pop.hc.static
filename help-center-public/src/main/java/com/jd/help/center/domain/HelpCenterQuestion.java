package com.jd.help.center.domain;

import java.io.Serializable;

/**
 * 帮助中心问题
 * User: xuxianjun
 * Date: 13-7-11
 * Time: 下午4:09
 * To change this template use File | Settings | File Templates.
 */
public class HelpCenterQuestion implements Serializable {


    /**
     * 问题ID
     */
    private int questionId;

    /**
     *  导航主题ID
     */
    private int topicId;

    /**
     * 问题
     */
    private String question;

    /**
     * 问题解答
     */
    private String answer = "";

    /**
     * 当前主题下问题排序
     */
    private int sortOrder;

    /**
     * 单个问题URL
     */
    private String url;

    /**
     * 锚点URL
     * 主题URL+锚点指向RUL
     */
    private String anchorUrl;

    /**
     *  0:未选择标签
     *  1:up标签
     *  2:new标签
     *  3:hot标签
     */
    private int logoType;

    public HelpCenterQuestion() {
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public String getAnchorUrl() {
        return anchorUrl;
    }

    public void setAnchorUrl(String anchorUrl) {
        this.anchorUrl = anchorUrl;
    }

    public int getLogoType() {
        return logoType;
    }

    public void setLogoType(int logoType) {
        this.logoType = logoType;
    }
}
