package com.jd.help.center.domain.question;

import com.jd.help.domain.HttpsUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-1
 * Time: 13:46:16
 * To change this template use File | Settings | File Templates.
 */
public class HelpQuestion implements Serializable{

    private int questionId;

    private int topicId;

    private String question;

    private String answer;

    private int sortOrder;

    private int status;

    private String notes;

    private String creator;

    private String modifier;

    /**
     * 发布时间
     */
    private Date postTime;

    private Date created;

    private Date modified;

    private String anchorUrl;

    /**
     * features扩展字段
     * ” ^ “分割   logoType:1^xxx:2
     */
    private String features;

    /**
     * 移除content里 href的http:
     */
    public void removeHttp() {
        if (this.notes != null) {
            this.notes = HttpsUtil.removeHttp(this.notes);
        }
        if (this.answer != null) {
            this.answer = HttpsUtil.removeHttp(this.answer);
        }
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
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

    public String getAnchorUrl() {
        return anchorUrl;
    }

    public void setAnchorUrl(String anchorUrl) {
        this.anchorUrl = anchorUrl;
    }

    public Date getPostTime() { return postTime; }

    public void setPostTime(Date postTime) { this.postTime = postTime; }

    public String getFeatures() { return features;}

    public void setFeatures(String features) {this.features = features;}
}
