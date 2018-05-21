package com.jd.help.jmq.producer.impl;

import com.jd.help.jmq.UpdateIssueMessage;
import com.jd.help.jmq.producer.IssueUpdateProducer;
import com.jd.jmq.client.producer.Producer;
import com.jd.jmq.common.exception.JMQException;
import com.jd.jmq.common.message.Message;
import com.jd.jmq.fastjson.JSON;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yfxialiang on 2018/4/18.
 */
@Service("issueUpdateProducer")
public class IssueUpdateProducerImpl implements IssueUpdateProducer {

    private static Logger logger = LogManager.getLogger(IssueUpdateProducerImpl.class);

    @Resource(name = "saveKeyword")
    private Producer producer;


    @Override
    public void sendIssueMessage(UpdateIssueMessage updateIssueMessage) {
        String jsonStr = JSON.toJSONString(updateIssueMessage);
        Message message = new Message();
        message.setTopic("issue_update_message");
        message.setText(jsonStr);
        message.setBusinessId(updateIssueMessage.getIssueId() + "_" + updateIssueMessage.getOpType());
        try {
            producer.send(message);
        } catch (JMQException e) {
            logger.error("¡¾IssueUpdateProducerImpl.sendIssueMessage¡¿ ·¢ËÍjmqÏûÏ¢Ê§°Ü :"+e.getMessage());
        }
    }
}
