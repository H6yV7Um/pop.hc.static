package com.jd.help.service;

import com.jd.help.domain.knowledge.Knowledge;
import com.jd.help.domain.knowledge.KnowledgeBean;

/**
 * Created by yfxialiang on 2018/4/2.
 */
public interface KnowledgeService {

    /**
     * 添加知识，返回knowledgeID
     */
    public Long addKnowledge(KnowledgeBean knowledgeBean);

    /**
     * 根据知识ID更新知识
     * @param knowledgeBean
     * @return
     */
    public boolean updateKnowledgeById(KnowledgeBean knowledgeBean);

    /**
     * 根据bizID和bizType查询知识
     * @param bizId
     * @param bizType
     * @return
     */
    public Knowledge queryByBizIdAndBizType(Long bizId,int bizType);

    /**
     * 根据knowledge表主键删除
     * @param id
     */
    public boolean delKnowledgeById(Long id);

    /**
     * 物理删除knowledge和knowledge_content表的全部数据
     * @return
     */
    public boolean delAllKnowledge();

    /**
     * 根据bizID和bizType查询知识Bean
     * @param bizId
     * @param bizType
     * @return
     */
    public KnowledgeBean queryKnowledgeBeanByBizIdAndBizType(Long bizId,int bizType);

}
