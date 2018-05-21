package com.jd.help.jmq.listener;

import com.jd.help.configCenter.ConfigCenterUtils;
import com.jd.help.configCenter.ConfigConstants;
import com.alibaba.fastjson.JSONObject;
import com.jd.help.domain.*;
import com.jd.help.domain.knowledge.Knowledge;
import com.jd.help.domain.knowledge.KnowledgeBean;
import com.jd.help.enums.IssueOptypesEnum;
import com.jd.help.enums.KnowledgeBizTypesEnum;
import com.jd.help.es.dao.KnowledgeEsDao;
import com.jd.help.jmq.UpdateIssueMessage;
import com.jd.help.service.IssueService;
import com.jd.help.service.KnowledgeService;
import com.jd.help.utils.KnowledgeUtils;
import com.jd.jmq.client.consumer.MessageListener;
import com.jd.jmq.common.message.Message;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by yfxialiang on 2018/4/8.
 */
public class UpdateIssueMessageListener implements MessageListener {
    private  final Log logger = LogFactory.getLog(UpdateIssueMessageListener.class);

    @Autowired
    private KnowledgeService knowledgeService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private KnowledgeEsDao knowledgeEsDao;

    @Autowired
    private KnowledgeUtils knowledgeUtils;

    @Autowired
    private ConfigCenterUtils configCenterUtils;

    @Override
    public void onMessage(List<Message> messages) throws Exception {
        CallerInfo info = Profiler.registerInfo("vender.help.service.UpdateIssueMessageListener.onMessage", false, true);
        logger.info("accept issue messages");
        //switch close:not receive messge;switch open:receive
        if(!configCenterUtils.isOpenSwitch(ConfigConstants.ISSUE_MQ_SWITCH)){
            logger.info("vender.help.service.UpdateIssueMessageListener.onMessage switch close");
            return;
        }
        if (messages == null || messages.isEmpty()) {
            Profiler.registerInfoEnd(info);
            return;
        }
        logger.info("accept issue messages.size="+messages.size());
        for (Message message : messages) {
            try {
                String s = message.getText();
                UpdateIssueMessage issueMessage = JSONObject.parseObject(s, UpdateIssueMessage.class);
                dealMessage(issueMessage);
            }catch (Exception e){
                logger.error("UpdateIssueMessageListener--onMessage--error",e);
                Profiler.functionError(info);
                throw e;
            }
        }
        Profiler.registerInfoEnd(info);
    }

    /**
     * 处理消息
     * @param message
     * @return
     */
    private boolean dealMessage(UpdateIssueMessage message) throws Exception {
        if(message == null){
            logger.error("message 不能为空");
            return false;
        }
        int opType = message.getOpType();
        Long issueId = message.getIssueId();
        logger.info("dealMessage issueId="+issueId+",,,,opType="+opType);
        if(!IssueOptypesEnum.rightCode(opType)){
            logger.error("无法识别的操作类型 message=" + JSONObject.toJSONString(message));
            return false;
        }
        if(issueId == null || issueId == 0){
            logger.error("issueId不能为空"+ JSONObject.toJSONString(message));
            return false;
        }
        try{
            if (opType == IssueOptypesEnum.ADD.getCode()){
                Issue issue = issueService.queryById(issueId.intValue());
                if(issue == null){
                    logger.info("根据issueId未查询到该issue,issueId="+issueId);
                    return false;
                }
                KnowledgeBean knowledgeBean = knowledgeUtils.issue2KnowledgeBean(issue);
                Knowledge knowledge = knowledgeService.queryByBizIdAndBizType(issueId, KnowledgeBizTypesEnum.hc.getCode());
                if(knowledge == null){
                    //证明是新加的issue
                    Long knowledgeId = knowledgeService.addKnowledge(knowledgeBean);
                    knowledgeBean.setId(knowledgeId);
                }else{
                    //证明是更新原来的issue
                    knowledgeBean.setId(knowledge.getId());
                    knowledgeService.updateKnowledgeById(knowledgeBean);
                }
                knowledgeEsDao.updateKnowledge(knowledgeUtils.knowledge2KnowledgeEsBean(knowledgeBean));

            }else if(opType == IssueOptypesEnum.DEL.getCode()){
                Knowledge knowledge = knowledgeService.queryByBizIdAndBizType(issueId, KnowledgeBizTypesEnum.hc.getCode());
                if(knowledge == null){
                    logger.info("根据bizID删除issue失败，未查询到该issue,issueId="+issueId);
                    return false;
                }
                knowledgeService.delKnowledgeById(knowledge.getId());
                knowledgeEsDao.deleteById(knowledge.getId());
            }
        }catch (Exception e){
            logger.error("dealMessage error message="+ JSONObject.toJSONString(message),e);
            throw e;
        }
        return true;
    }
}
