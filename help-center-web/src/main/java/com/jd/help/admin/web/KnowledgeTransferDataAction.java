package com.jd.help.admin.web;

import com.jd.help.HelpBaseAction;
import com.jd.help.dao.KnowledgeContentDao;
import com.jd.help.dao.KnowledgeDao;
import com.jd.help.domain.knowledge.Knowledge;
import com.jd.help.domain.knowledge.KnowledgeContent;
import com.jd.help.domain.knowledge.KnowledgeEsBean;
import com.jd.help.es.dao.KnowledgeEsDao;
import com.jd.help.service.KnowledgeTransferDataService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class KnowledgeTransferDataAction extends HelpBaseAction {

    private static Logger logger = LogManager.getLogger(KnowledgeTransferDataAction.class);

    @Autowired
    private KnowledgeTransferDataService knowledgeTransferDataService;

    @Autowired
    private KnowledgeDao knowledgeDao;

    @Autowired
    private KnowledgeContentDao knowledgeContentDao;

    @Autowired
    private KnowledgeEsDao knowledgeEsDao;

    public void delAllData(){
        knowledgeTransferDataService.delAllData();
    }

    public void tx(){
        knowledgeTransferDataService.transferXueData();
    }

    public void ti(){
        knowledgeTransferDataService.transferIssueData();
    }

    public void ts(){
        knowledgeTransferDataService.transferSceneData();
    }

    public void dbpk(){
        Long id = 3425l;
        knowledgeDao.deleteByPrimaryKey(id);
    }

    public void qbp(){
        Map<String,Object> map = new HashMap<String, Object>();
        Long bizId = 1783l;
        int bizType = 1;
        map.put("bizId", bizId);
        map.put("bizTypeId", bizType);
        knowledgeDao.queryByMap(map);
    }

    //����
    public void ubpks() {
        Knowledge k = new Knowledge();
        k.setId(3426l);
        k.setName("woshi beixiugai zhihou de mingzi ");
        knowledgeDao.updateByPrimaryKeySelective(k);
    }

    //��ȷ
    public void dbki() {
        Long id = 3424l;
        knowledgeContentDao.deleteByKnowledgeId(id);
    }
    //��ȷ
    public void ubki() {
        KnowledgeContent kc = new KnowledgeContent();
        kc.setKnowledgeId(3425l);
        kc.setContent("���Ǳ��޸�֮�������?");
        knowledgeContentDao.updateByKnowledgeId(kc);
    }

    public void q(String id) {
        try {
            KnowledgeEsBean bean = knowledgeEsDao.queryById(Long.parseLong(id));
            System.out.println(bean);
        } catch (Exception e) {
            logger.error("queryById error",e);
            logger.error("queryById error", e);
        }
    }

    public void u(String id){
        KnowledgeEsBean knowledge = new KnowledgeEsBean();
        knowledge.setKnowledgeId(Long.parseLong(id));
        knowledge.setName("xltest");
        knowledge.setLabel1Ids("1 1 2 2");
        knowledge.setLabel1Names("test1 test1 test2 test2");
        knowledge.setLabel2Ids("3 4 5 6");
        knowledge.setLabel2Names("test3 test4 test5 test6");
        try {
            knowledgeEsDao.updateKnowledge(knowledge);
        } catch (Exception e) {
            logger.error("updateKnowledge error", e);
            logger.error("updateKnowledge error",e);
        }
    }


    public void di(String id){
        try {
            knowledgeEsDao.deleteById(Long.parseLong(id));
        } catch (Exception e) {
            logger.error("deleteById error",e);
        }
    }

    public void da(){
        try {
            knowledgeEsDao.deleteAll();
        } catch (Exception e) {
            logger.error("deleteById error",e);
            logger.error("deleteById error", e);
        }
    }



}
