package com.jd.help.service.impl;

import com.jd.businesscollege.shop.client.service.OndemandCourseToHcRpcService;
import com.jd.businesscollege.shop.domain.BizcollOndemandCourseBean;
import com.jd.help.dao.IssueDao;
import com.jd.help.domain.*;
import com.jd.help.domain.knowledge.KnowledgeBean;
import com.jd.help.enums.CategoryTypeEnum;
import com.jd.help.es.dao.KnowledgeEsDao;
import com.jd.help.service.KnowledgeService;
import com.jd.help.service.KnowledgeTransferDataService;
import com.jd.help.utils.KnowledgeUtils;
import com.jd.karma.facade.KarmaScene2HcRpcService;
import com.jd.karma.facade.domain.transfer2Hc.Scene2Hc;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yfxialiang on 2018/3/30.
 * �����������ϱ����ݡ������������ݡ��̼�ѧϰ�������ݵ��뵽knowledge����
 */
@Service("knowledgeTransferDataService")
public class KnowledgeTransferDataServiceImpl implements KnowledgeTransferDataService {

    private static Logger logger = LoggerFactory.getLogger(KnowledgeTransferDataServiceImpl.class);

    @Autowired
    private OndemandCourseToHcRpcService ondemandCourseToHcRpcService;

    @Autowired
    private KarmaScene2HcRpcService karmaScene2HcRpcService;

    @Autowired
    private KnowledgeService knowledgeService;

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private KnowledgeEsDao knowledgeEsDao;

    @Autowired
    private KnowledgeUtils knowledgeUtils;

    /**
     * ���̼���ѵ�������ݵ���knowledge
     */
    @Override
    public void transferXueData() {
        List<BizcollOndemandCourseBean> courseList = ondemandCourseToHcRpcService.queryAllOndemandCourseBean();
        if(CollectionUtils.isEmpty(courseList)){
            return;
        }
        for(BizcollOndemandCourseBean bean : courseList){
            KnowledgeBean knowledgeBean = knowledgeUtils.course2KnowledgeBean(bean);
            if(knowledgeBean == null){
                continue;
            }
            Long knowledgeId = knowledgeService.addKnowledge(knowledgeBean);
            knowledgeBean.setId(knowledgeId);
            try {
                knowledgeEsDao.updateKnowledge(knowledgeUtils.knowledge2KnowledgeEsBean(knowledgeBean));
            } catch (Exception e) {
                logger.error("transferXueData error",e);
            }
        }
    }

    /**
     * ���������ݵ���knowledge
     */
    @Override
    public void transferSceneData() {
        long time1 = System.currentTimeMillis();
        List<Scene2Hc> sceneList = karmaScene2HcRpcService.queryAllScene();
        long time2 = System.currentTimeMillis();

        logger.info("time2-time1=" +(time2-time1));
        logger.info("sceneList.size========"+ (CollectionUtils.isEmpty(sceneList) ? 0 : sceneList.size()));

        if(CollectionUtils.isEmpty(sceneList)){
            return;
        }
        for(Scene2Hc scene : sceneList){
            if(scene.getCategoryType() != null && scene.getCategoryType() == CategoryTypeEnum.POP.getCode()){
                KnowledgeBean knowledgeBean = knowledgeUtils.scene2KnowledgeBean(scene);
                if(knowledgeBean == null){
                    continue;
                }
                Long knowledgeId = knowledgeService.addKnowledge(knowledgeBean);
                knowledgeBean.setId(knowledgeId);
                try {
                    knowledgeEsDao.updateKnowledge(knowledgeUtils.knowledge2KnowledgeEsBean(knowledgeBean));
                } catch (Exception e) {
                    logger.error("transferSceneData error",e);
                    continue;
                }
            }

        }
    }

    /**
     * �����������ϱ����ݵ���knowledge
     */
    @Override
    public void transferIssueData() {
        IssueQuery issueQuery = new IssueQuery();
        issueQuery.setStatus(1);
        List<Issue> issueList = issueDao.queryForIssueList(issueQuery, 1, 5000);
        if(CollectionUtils.isEmpty(issueList)){
            return;
        }
        for(Issue issue : issueList){
            KnowledgeBean knowledgeBean = knowledgeUtils.issue2KnowledgeBean(issue);
            if(knowledgeBean == null){
                continue;
            }
            Long knowledgeId = knowledgeService.addKnowledge(knowledgeBean);
            knowledgeBean.setId(knowledgeId);
            try {
                knowledgeEsDao.updateKnowledge(knowledgeUtils.knowledge2KnowledgeEsBean(knowledgeBean));
            } catch (Exception e) {
                logger.error("transferSceneData error",e);
            }
        }

    }

    @Override
    public void delAllData() {
        knowledgeService.delAllKnowledge();
        try {
            knowledgeEsDao.deleteAll();
        } catch (Exception e) {
            logger.info("delAllData error",e);
        }
    }

}
