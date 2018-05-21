package com.jd.help.dao;

import com.jd.help.domain.knowledge.KnowledgeContent;

public interface KnowledgeContentDao {
    int deleteByKnowledgeId(Long knowledgeId);

    long insert(KnowledgeContent record);

    int updateByKnowledgeId(KnowledgeContent record);

    int delAllKnowledgeContent();

    KnowledgeContent queryContentByKnowledgeId(Long knowledgeId);

}