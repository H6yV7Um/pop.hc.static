package com.jd.help.dao;

import com.jd.help.domain.knowledge.Knowledge;
import java.util.List;
import java.util.Map;

public interface KnowledgeDao {

    int deleteByPrimaryKey(Long id);

    long insert(Knowledge record);

    List<Knowledge> queryByMap(Map map);

    int updateByPrimaryKeySelective(Knowledge record);

    int delAllKnowledge();
}