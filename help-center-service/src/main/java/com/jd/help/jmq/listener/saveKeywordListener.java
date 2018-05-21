package com.jd.help.jmq.listener;

import com.jd.help.dao.KeywordDao;
import com.jd.help.domain.Keyword;
import com.jd.jmq.client.consumer.MessageListener;
import com.jd.jmq.common.message.Message;
import com.alibaba.fastjson.JSON;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haoming1
 * @Description:
 * @Date Created in 18:10 2018/1/8
 * @Modified By:
 */

public class saveKeywordListener implements MessageListener {

    private final Log log = LogFactory.getLog(saveKeywordListener.class);

    @Resource
    private KeywordDao KeywordDao;
    @Override
    public void onMessage(List<Message> messages) throws Exception {
        CallerInfo info = null;
        try {
            info = Profiler.registerInfo("vender.help.customer.saveKeywordListener.keywordInsert", false,true);
            for (int i = 0; i <messages.size() ; i++) {
                String text = messages.get(i).getText();
                Keyword  keyword =JSON.parseObject(text,Keyword.class);
                KeywordDao.insert(keyword);
            }
        }catch (Exception e){
            log.error("vender.help.customer.saveKeywordListener.keywordInsert",e);
            log.error("¡¾saveKeywordListener.onMessage¡¿ jsonÏûÏ¢×ª»»Ê§°Ü :"+e.getMessage());
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
    }
}
