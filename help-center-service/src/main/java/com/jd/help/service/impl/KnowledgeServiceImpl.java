package com.jd.help.service.impl;

import com.jd.common.util.StringUtils;
import com.jd.help.dao.KnowledgeContentDao;
import com.jd.help.dao.KnowledgeDao;
import com.jd.help.domain.knowledge.Knowledge;
import com.jd.help.domain.knowledge.KnowledgeBean;
import com.jd.help.domain.knowledge.KnowledgeContent;
import com.jd.help.service.KnowledgeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yfxialiang on 2018/4/2.
 */

@Service("knowledgeService")
public class KnowledgeServiceImpl implements KnowledgeService {

    private static Logger logger = LogManager.getLogger(KnowledgeServiceImpl.class);

    @Autowired
    private KnowledgeDao knowledgeDao;

    @Autowired
    private KnowledgeContentDao knowledgeContentDao;


    @Override
    public Long addKnowledge(KnowledgeBean knowledgeBean) {
        if(knowledgeBean == null){
            return null;
        }
        Knowledge knowledge = new Knowledge();
        BeanUtils.copyProperties(knowledgeBean, knowledge);
        long insertResultId = knowledgeDao.insert(knowledge);
        if(insertResultId > 0){
            KnowledgeContent kc = new KnowledgeContent();
            kc.setContent(knowledgeBean.getContent());
            kc.setKnowledgeId(insertResultId);
            knowledgeContentDao.insert(kc);
        }
        return insertResultId;
    }

    @Override
    public boolean updateKnowledgeById(KnowledgeBean knowledgeBean) {
        if(knowledgeBean == null || knowledgeBean.getId() == null){
            logger.info("更新知识失败，知识或知识ID不能为空");
            return false;
        }
        Knowledge knowledge = new Knowledge();
        BeanUtils.copyProperties(knowledgeBean,knowledge);
        knowledgeDao.updateByPrimaryKeySelective(knowledge);
        if(StringUtils.isNotBlank(knowledgeBean.getContent())){
            KnowledgeContent kc = new KnowledgeContent();
            kc.setKnowledgeId(knowledgeBean.getId());
            kc.setContent(knowledgeBean.getContent());
            knowledgeContentDao.updateByKnowledgeId(kc);
        }
        return true;
    }

    @Override
    public Knowledge queryByBizIdAndBizType(Long bizId, int bizType) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("bizId", bizId);
        map.put("bizTypeId", bizType);
        List<Knowledge> list = knowledgeDao.queryByMap(map);
        if(CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean delKnowledgeById(Long id) {
        if(id == null){
            logger.info("删除知识失败，知识ID不能为空");
            return false;
        }
        knowledgeDao.deleteByPrimaryKey(id);
        knowledgeContentDao.deleteByKnowledgeId(id);
        return true;
    }

    /**
     * 物理删除knowledge和knowledge_content表的全部数据
     * @return
     */
    @Override
    public boolean delAllKnowledge() {
        int r1 = knowledgeDao.delAllKnowledge();
        int r2 = knowledgeContentDao.delAllKnowledgeContent();
        logger.info("delAllKnowledge r1="+r1+"    .........r2="+r2);
        return true;
    }

    @Override
    public KnowledgeBean queryKnowledgeBeanByBizIdAndBizType(Long bizId, int bizType) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("bizId", bizId);
        map.put("bizTypeId", bizType);
        List<Knowledge> list = knowledgeDao.queryByMap(map);
        if(CollectionUtils.isNotEmpty(list)){
            Knowledge knowledge = list.get(0);
            if(knowledge != null){
                KnowledgeContent content = knowledgeContentDao.queryContentByKnowledgeId(knowledge.getId());
                KnowledgeBean bean = new KnowledgeBean();
                BeanUtils.copyProperties(knowledge,bean);
                bean.setContent(content == null ? null : content.getContent());
                return bean;
            }

        }
        return null;
    }
}
