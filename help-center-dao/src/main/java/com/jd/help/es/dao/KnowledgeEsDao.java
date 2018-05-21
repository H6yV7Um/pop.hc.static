package com.jd.help.es.dao;

import com.jd.help.domain.knowledge.KnowledgeEsBean;

/**
 * Created by yfxialiang on 2018/4/24.
 */
public interface KnowledgeEsDao {

    public boolean updateKnowledge(KnowledgeEsBean knowledge) throws Exception;

    public boolean deleteById(Long knowledgeId) throws Exception;

    public boolean deleteAll() throws Exception;

    public KnowledgeEsBean queryById(Long knowledgeId) throws Exception;
}
