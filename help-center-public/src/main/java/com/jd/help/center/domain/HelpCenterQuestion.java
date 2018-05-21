package com.jd.help.center.domain;

import java.io.Serializable;

/**
 * ������������
 * User: xuxianjun
 * Date: 13-7-11
 * Time: ����4:09
 * To change this template use File | Settings | File Templates.
 */
public class HelpCenterQuestion implements Serializable {


    /**
     * ����ID
     */
    private int questionId;

    /**
     *  ��������ID
     */
    private int topicId;

    /**
     * ����
     */
    private String question;

    /**
     * ������
     */
    private String answer = "";

    /**
     * ��ǰ��������������
     */
    private int sortOrder;

    /**
     * ��������URL
     */
    private String url;

    /**
     * ê��URL
     * ����URL+ê��ָ��RUL
     */
    private String anchorUrl;

    /**
     *  0:δѡ���ǩ
     *  1:up��ǩ
     *  2:new��ǩ
     *  3:hot��ǩ
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
