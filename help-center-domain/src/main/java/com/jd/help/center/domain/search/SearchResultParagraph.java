package com.jd.help.center.domain.search;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xuxianjun
 * Date: 13-4-24
 * Time: ÏÂÎç3:54
 * To change this template use File | Settings | File Templates.
 */
public class SearchResultParagraph implements Serializable {
    private SearchResultParagraphContent Content;
    private long category_id;
    private long id;
    private long sys_id;
    private long topic_id;


    public SearchResultParagraphContent getContent() {
        return Content;
    }

    public void setContent(SearchResultParagraphContent content) {
        Content = content;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSys_id() {
        return sys_id;
    }

    public void setSys_id(long sys_id) {
        this.sys_id = sys_id;
    }

    public long getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(long topic_id) {
        this.topic_id = topic_id;
    }
}
