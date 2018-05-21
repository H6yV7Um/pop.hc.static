package com.jd.help.service;

import com.jd.help.domain.RelationKnowledge;

import java.util.List;
import java.util.Set;

/**
 * ����֪ʶservice
 *
 * @author wangyu1099
 * @date 2018/04/17
 */
public interface RelationKnowledgeService {

    /**
     * ֪ʶ�Ĺ���֪ʶ���뵽redis
     * ʧЧʱ��7��
     *
     * @param knowledgeId ֪ʶid
     * @param relationIds ��knowledgeId������֪ʶids
     * @return
     */
    boolean addRelationKnowledgeToRedis(Long knowledgeId, Set<Long> relationIds);

    /**
     * ����֪ʶid��ѯǿ����֪ʶ
     *
     * @param knowledgeId ֪ʶid
     * @return List<RelationKnowledge>
     */
    List<RelationKnowledge> listStrongRelationKnowledge(Long knowledgeId);


    /**
     * ��redis�в�ѯ������֪ʶids
     *
     * @param knowledgeId ֪ʶid
     * @return Set<String>
     */
    Set<String> queryInRedis(Long knowledgeId);
}
