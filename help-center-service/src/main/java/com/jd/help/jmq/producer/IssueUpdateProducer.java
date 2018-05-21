package com.jd.help.jmq.producer;

import com.jd.help.jmq.UpdateIssueMessage;

/**
 * Created by yfxialiang on 2018/4/18.
 */
public interface IssueUpdateProducer {

    public void sendIssueMessage(UpdateIssueMessage updateIssueMessage);
}
