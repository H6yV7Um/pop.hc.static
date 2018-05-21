package com.jd.help.jmq.producer.impl;

import com.jd.help.domain.Keyword;
import com.jd.help.jmq.producer.saveKeywordProducer;
import com.jd.jmq.client.producer.Producer;
import com.jd.jmq.common.exception.JMQException;
import com.jd.jmq.common.message.Message;
import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author haoming1
 * @Description:
 * @Date Created in 15:16 2018/1/8
 * @Modified By:
 */
@Service("saveKeywordProducer")
public class saveKeywordProducerImpl implements saveKeywordProducer {

    private static final Log log = LogFactory.getLog(saveKeywordProducerImpl.class);

    @Resource(name = "saveKeyword")
    private Producer producer;

    @Override
    public void saveKeyword(Keyword keywordDto) {
        String jsonStr = JSON.toJSONString(keywordDto);
        Message message = new Message();
        message.setTopic("saveKeyword");
        message.setText(jsonStr);
        try {
            producer.send(message);
        } catch (JMQException e) {
            log.error("°æsaveKeywordProducerImpl.saveKeyword°ø ∑¢ÀÕjmqœ˚œ¢ ß∞‹ :"+e.getMessage());
        }


    }
}
