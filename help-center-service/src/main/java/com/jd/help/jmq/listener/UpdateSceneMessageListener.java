package com.jd.help.jmq.listener;

import com.alibaba.fastjson.JSONObject;
import com.jd.help.configCenter.ConfigCenterUtils;
import com.jd.help.configCenter.ConfigConstants;
import com.jd.help.domain.knowledge.Knowledge;
import com.jd.help.domain.knowledge.KnowledgeBean;
import com.jd.help.enums.KnowledgeBizTypesEnum;
import com.jd.help.enums.SceneOpTypeEnum;
import com.jd.help.es.dao.KnowledgeEsDao;
import com.jd.help.jmq.UpdateSceneMessage;
import com.jd.help.service.KnowledgeService;
import com.jd.help.utils.KnowledgeUtils;
import com.jd.jmq.client.consumer.MessageListener;
import com.jd.jmq.common.message.Message;
import com.jd.karma.facade.KarmaScene2HcRpcService;
import com.jd.karma.facade.domain.ResultFacade;
import com.jd.karma.facade.domain.transfer2Hc.Scene2Hc;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by yfxialiang on 2018/4/13.
 */
public class UpdateSceneMessageListener implements MessageListener {
    private  final Log logger = LogFactory.getLog(UpdateSceneMessageListener.class);

    @Autowired
    private KarmaScene2HcRpcService karmaScene2HcRpcService;

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
        CallerInfo info = Profiler.registerInfo("vender.help.service.UpdateSceneMessageListener.onMessage", false, true);
        logger.info("accept scene messages");
        //switch close:not receive messge;switch open:receive
        if(!configCenterUtils.isOpenSwitch(ConfigConstants.SCENE_MQ_SWITCH)){
            logger.info("vender.help.service.UpdateSceneMessageListener.onMessage switch close");
            return;
        }
        if (messages == null || messages.isEmpty()) {
            Profiler.registerInfoEnd(info);
            return;
        }
        logger.info("accept scene messages.size"+messages.size());
        for (Message message : messages) {
            try {
                String s = message.getText();
                UpdateSceneMessage sceneMessage = JSONObject.parseObject(s, UpdateSceneMessage.class);
                dealMessage(sceneMessage);
            }catch (Exception e){
                logger.error("UpdateSceneMessageListener--onMessage--error",e);
                Profiler.functionError(info);
                throw e;
            }
        }
        Profiler.registerInfoEnd(info);
    }

    /**
     * ������Ϣ
     * @param message
     * @return
     */
    private boolean dealMessage(UpdateSceneMessage message) throws Exception {
        if(message == null){
            logger.error("message ����Ϊ��");
            return false;
        }
        int opType = message.getOpType();
        Long sceneId = message.getSceneId();
        logger.info("dealMessage sceneId="+sceneId+",,,,opType="+opType);
        if(!SceneOpTypeEnum.rightCode(opType)){
            logger.info("�޷�ʶ��Ĳ������� message=" + JSONObject.toJSONString(message));
            return false;
        }
        if(sceneId == null || sceneId == 0){
            logger.info("sceneId����Ϊ��"+ JSONObject.toJSONString(message));
            return false;
        }
        try{
            if (opType == SceneOpTypeEnum.ADDDB.getCode()){
                ResultFacade resultFacade= karmaScene2HcRpcService.querySceneById(sceneId);
                if(resultFacade == null || !resultFacade.isSuccess()){
                    logger.info("����sceneId��ѯscene���ؿ�,sceneId=" + sceneId);
                    return false;
                }
                if(resultFacade == null || !resultFacade.isSuccess()){
                    logger.info("����sceneIdδ��ѯ��scene��������Ϣ��" + resultFacade.getErrorMessage() + ",     sceneId=" + sceneId);
                    return false;
                }

                Knowledge knowledge = knowledgeService.queryByBizIdAndBizType(sceneId, KnowledgeBizTypesEnum.karma.getCode());
                Scene2Hc scene2Hc = (Scene2Hc)resultFacade.getResultDto();
                KnowledgeBean knowledgeBean = knowledgeUtils.scene2KnowledgeBean(scene2Hc);
                if(knowledge == null){
                    //֤�����¼ӵ�scene
                    Long knowledgeId = knowledgeService.addKnowledge(knowledgeBean);
                    knowledgeBean.setId(knowledgeId);
                }else{
                    //֤���Ǹ���ԭ����scene
                    knowledgeBean.setId(knowledge.getId());
                    knowledgeService.updateKnowledgeById(knowledgeBean);
                }

            }else if(opType == SceneOpTypeEnum.DELDB.getCode()){
                Knowledge knowledge = knowledgeService.queryByBizIdAndBizType(sceneId, KnowledgeBizTypesEnum.karma.getCode());
                if(knowledge == null){
                    logger.info("����bizIDɾ��sceneʧ�ܣ�δ��ѯ����scene,sceneId="+sceneId);
                    return false;
                }
                knowledgeService.delKnowledgeById(knowledge.getId());
            }else if (opType == SceneOpTypeEnum.ADDDBES.getCode()){
                ResultFacade resultFacade= karmaScene2HcRpcService.querySceneById(sceneId);
                if(resultFacade == null || !resultFacade.isSuccess()){
                    logger.error("����sceneId��ѯscene���ؿ�,sceneId=" + sceneId);
                    return false;
                }
                if(resultFacade == null || !resultFacade.isSuccess()){
                    logger.info("����sceneId��ѯscene�������󣬴�����Ϣ��" + resultFacade.getErrorMessage() + ",     sceneId=" + sceneId);
                    return false;
                }
                Scene2Hc scene2Hc = (Scene2Hc)resultFacade.getResultDto();
                KnowledgeBean knowledgeBean = knowledgeUtils.scene2KnowledgeBean(scene2Hc);
                Knowledge knowledge = knowledgeService.queryByBizIdAndBizType(sceneId, KnowledgeBizTypesEnum.karma.getCode());
                if(knowledge == null){
                    //֤�����¼ӵ�scene
                    Long knowledgeId = knowledgeService.addKnowledge(knowledgeBean);
                    knowledgeBean.setId(knowledgeId);
                }else{
                    //֤���Ǹ���ԭ����scene
                    knowledgeBean.setId(knowledge.getId());
                    knowledgeService.updateKnowledgeById(knowledgeBean);
                }
                knowledgeEsDao.updateKnowledge(knowledgeUtils.knowledge2KnowledgeEsBean(knowledgeBean));
            }else if(opType == SceneOpTypeEnum.DELDBES.getCode()){
                Knowledge knowledge = knowledgeService.queryByBizIdAndBizType(sceneId, KnowledgeBizTypesEnum.karma.getCode());
                if(knowledge == null){
                    logger.info("����bizIDɾ��sceneʧ�ܣ�δ��ѯ����scene,sceneId="+sceneId);
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
