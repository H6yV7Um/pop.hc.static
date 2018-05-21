package com.jd.help.service;

import com.jd.help.domain.RelationKnowledge;

import java.util.List;
import java.util.Set;

/**
 * 关联知识service
 *
 * @author wangyu1099
 * @date 2018/04/17
 */
public interface RelationKnowledgeService {

    /**
     * 知识的关联知识存入到redis
     * 失效时间7天
     *
     * @param knowledgeId 知识id
     * @param relationIds 与knowledgeId关联的知识ids
     * @return
     */
    boolean addRelationKnowledgeToRedis(Long knowledgeId, Set<Long> relationIds);

    /**
     * 根据知识id查询强关联知识
     *
     * @param knowledgeId 知识id
     * @return List<RelationKnowledge>
     */
    List<RelationKnowledge> listStrongRelationKnowledge(Long knowledgeId);


    /**
     * 在redis中查询关联的知识ids
     *
     * @param knowledgeId 知识id
     * @return Set<String>
     */
    Set<String> queryInRedis(Long knowledgeId);
}
