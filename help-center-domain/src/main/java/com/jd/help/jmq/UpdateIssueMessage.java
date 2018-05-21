package com.jd.help.jmq;

import java.io.Serializable;

/**
 * Created by yfxialiang on 2018/4/8.
 * issue发生更新会发送这个MQ消息
 */
public class UpdateIssueMessage implements Serializable {
    private static final long serialVersionUID = -7918191635912231215L;

    //issueId
    private Long issueId;

    //操作类型，对应 IssueOpTypeEnum中的值
    private int opType;

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }
}
