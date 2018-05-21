package com.jd.help.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lining7 on 2017/10/18.
 */
public class IssueDateQuery implements Serializable {

    private Date issueBeginTime;

    private Date issueEndTime;

    private Date suggestBeginTime;

    private Date suggestEndTime;

    public Date getIssueBeginTime() {
        return issueBeginTime;
    }

    public void setIssueBeginTime(Date issueBeginTime) {
        this.issueBeginTime = issueBeginTime;
    }

    public Date getIssueEndTime() {
        return issueEndTime;
    }

    public void setIssueEndTime(Date issueEndTime) {
        this.issueEndTime = issueEndTime;
    }

    public Date getSuggestBeginTime() {
        return suggestBeginTime;
    }

    public void setSuggestBeginTime(Date suggestBeginTime) {
        this.suggestBeginTime = suggestBeginTime;
    }

    public Date getSuggestEndTime() {
        return suggestEndTime;
    }

    public void setSuggestEndTime(Date suggestEndTime) {
        this.suggestEndTime = suggestEndTime;
    }
}
