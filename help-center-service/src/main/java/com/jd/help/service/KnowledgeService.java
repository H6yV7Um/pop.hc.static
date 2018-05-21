package com.jd.help.service;

import com.jd.help.domain.knowledge.Knowledge;
import com.jd.help.domain.knowledge.KnowledgeBean;

/**
 * Created by yfxialiang on 2018/4/2.
 */
public interface KnowledgeService {

    /**
     * ���֪ʶ������knowledgeID
     */
    public Long addKnowledge(KnowledgeBean knowledgeBean);

    /**
     * ����֪ʶID����֪ʶ
     * @param knowledgeBean
     * @return
     */
    public boolean updateKnowledgeById(KnowledgeBean knowledgeBean);

    /**
     * ����bizID��bizType��ѯ֪ʶ
     * @param bizId
     * @param bizType
     * @return
     */
    public Knowledge queryByBizIdAndBizType(Long bizId,int bizType);

    /**
     * ����knowledge������ɾ��
     * @param id
     */
    public boolean delKnowledgeById(Long id);

    /**
     * ����ɾ��knowledge��knowledge_content���ȫ������
     * @return
     */
    public boolean delAllKnowledge();

    /**
     * ����bizID��bizType��ѯ֪ʶBean
     * @param bizId
     * @param bizType
     * @return
     */
    public KnowledgeBean queryKnowledgeBeanByBizIdAndBizType(Long bizId,int bizType);

}
