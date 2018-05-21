package com.jd.help.dao;

import com.jd.help.domain.RelationKnowledge;

import java.util.List;

public interface RelationKnowledgeDao {

    /**
     * 删除弱关联知识
     *
     * @param id 主键id
     * @param relationKnowledgeId 弱关联知识
     * @return int 1：删除成功 0：失败
     */
    int deleteWeakRelation(Long id, Long relationKnowledgeId);

    /**
     * 删除所有的关联知识
     * 该方法在修改时调用
     *
     * @param id 帮助中心的知识id
     * @return int 0：失败
     */
    int deleteRelation(Long id);

    /**
     * 新增数据
     *
     * @param record 知识对象
     * @return long
     */
    long insert(RelationKnowledge record);

    /**
     * 查询知识的强关联知识列表
     *
     * @param id 知识id
     * @return List<RelationKnowledge>
     */
    List<RelationKnowledge> listRelationKnowledge(Long id);
}