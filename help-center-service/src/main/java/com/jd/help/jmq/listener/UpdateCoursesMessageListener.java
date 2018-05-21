package com.jd.help.jmq.listener;

import com.jd.businesscollege.shop.client.response.ServiceResponse;
import com.jd.businesscollege.shop.client.service.OndemandCourseRpcService;
import com.jd.businesscollege.shop.domain.BizcollOndemandCourseBean;
import com.jd.help.configCenter.ConfigCenterUtils;
import com.jd.help.configCenter.ConfigConstants;
import com.jd.help.domain.knowledge.Knowledge;
import com.alibaba.fastjson.JSONObject;
import com.jd.help.domain.knowledge.KnowledgeBean;
import com.jd.help.enums.KnowledgeBizTypesEnum;
import com.jd.help.enums.OndemandCourseOpTypeEnum;
import com.jd.help.es.dao.KnowledgeEsDao;
import com.jd.help.jmq.UpdateCourseMessage;
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
public class UpdateCoursesMessageListener implements MessageListener {
    private  final Log logger = LogFactory.getLog(UpdateCoursesMessageListener.class);

    @Autowired
    private OndemandCourseRpcService ondemandCourseRpcService;

    @Autowired
    private KnowledgeService knowledgeService;

    @Autowired
    private KnowledgeEsDao knowledgeEsDao;

    @Autowired
    private KnowledgeUtils knowledgeUtils;

    @Autowired
    private ConfigCenterUtils configCenterUtils;

    @Override
    public void onMessage(List<Message> messages) throws Exception {
        CallerInfo info = Profiler.registerInfo("vender.help.service.UpdateCoursesMessageListener.onMessage", false, true);
        logger.info("accept course messages");
        //switch close:not receive messge;switch open:receive
        if(!configCenterUtils.isOpenSwitch(ConfigConstants.COURSE_MQ_SWITCH)){
            logger.info("vender.help.service.UpdateCoursesMessageListener.onMessage switch close");
            return;
        }
        if (messages == null || messages.isEmpty()) {
            Profiler.registerInfoEnd(info);
            return;
        }
        logger.info("accept messages.size="+messages.size());
        for (Message message : messages) {
            try {
                String s = message.getText();
                UpdateCourseMessage courseMessage = JSONObject.parseObject(s, UpdateCourseMessage.class);
                dealMessage(courseMessage);
            }catch (Exception e){
                logger.error("UpdateCoursesMessageListener--onMessage--error",e);
                Profiler.functionError(info);
                throw e;
            }
        }
        Profiler.registerInfoEnd(info);
    }

    /**
     * @param message
     * @return
     */
    private boolean dealMessage(UpdateCourseMessage message) throws Exception {


        if(message == null){
            logger.info("message is null");
            return false;
        }
        int opType = message.getOpType();
        Long courseId = message.getCourseId();
        logger.info("dealMessage courseId="+courseId+",,,,opType="+opType);
        if(!OndemandCourseOpTypeEnum.rightCode(opType)){
            logger.info("opType is wrong, message=" + JSONObject.toJSONString(message));
            return false;
        }
        if(courseId == null || courseId == 0){
            logger.info("courseId is null"+ JSONObject.toJSONString(message));
            return false;
        }
        try{
            if (opType == OndemandCourseOpTypeEnum.ADD.getCode()){
                ServiceResponse<BizcollOndemandCourseBean> response = ondemandCourseRpcService.queryOndemandCourseById(courseId+"");
                logger.info("courseId = "+courseId+",,ondemandCourseRpcService.queryOndemandCourseById response="+JSONObject.toJSONString(response));
                if(response == null || !response.isSuccess() || response.getResult() == null){
                    logger.info("can not query course by courseId=" + courseId);
                    return false;
                }
                BizcollOndemandCourseBean courseBean = response.getResult();
                KnowledgeBean knowledgeBean = knowledgeUtils.course2KnowledgeBean(courseBean);
                Knowledge knowledge = knowledgeService.queryByBizIdAndBizType(courseId,KnowledgeBizTypesEnum.xue.getCode());
                if(knowledge == null){
                    //create knowledge
                    Long knowledgeId = knowledgeService.addKnowledge(knowledgeBean);
                    knowledgeBean.setId(knowledgeId);
                }else{
                    //update knowledge
                    knowledgeBean.setId(knowledge.getId());
                    knowledgeService.updateKnowledgeById(knowledgeBean);
                }
                knowledgeEsDao.updateKnowledge(knowledgeUtils.knowledge2KnowledgeEsBean(knowledgeBean));
            }
            //todo 先不接course的count MQ消息，等帮助中心和规则中心都有pv数据之后再打开
            /*else if(opType == OndemandCourseOpTypeEnum.COUNT.getCode()){
                KnowledgeBean knowledgeBean = knowledgeService.queryKnowledgeBeanByBizIdAndBizType(courseId, KnowledgeBizTypesEnum.xue.getCode());
                if(knowledgeBean == null){
                    logger.info("can not update course knowledge count,because can not query course by courseId="+courseId);
                }else{
                    //update knowledge
                    knowledgeBean.setPv(knowledgeBean.getPv() == null ? 1 : knowledgeBean.getPv() + 1);
                    knowledgeService.updateKnowledgeById(knowledgeBean);
                    knowledgeEsDao.updateKnowledge(knowledgeUtils.knowledge2KnowledgeEsBean(knowledgeBean));
                }
            }*/else if(opType == OndemandCourseOpTypeEnum.DEL.getCode()){
                Knowledge knowledge = knowledgeService.queryByBizIdAndBizType(courseId,KnowledgeBizTypesEnum.xue.getCode());
                if(knowledge == null){
                    logger.info("can not query knowledge by courseId="+courseId);
                    return false;
                }
                knowledgeService.delKnowledgeById(knowledge.getId());
                knowledgeEsDao.deleteById(knowledge.getId());
            }
        } catch (Exception e){
            logger.error("dealMessage error message="+ JSONObject.toJSONString(message),e);
            throw e;
        }
        return true;
    }
}
