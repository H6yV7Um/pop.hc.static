package com.jd.help.dao.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.dao.KnowledgeContentDao;
import com.jd.help.domain.knowledge.KnowledgeContent;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by yfxialiang on 2018/4/8.
 */
@Repository("knowledgeContentDao")
@SuppressWarnings("unused")
public class KnowledgeContentDaoImpl extends BaseDao implements KnowledgeContentDao {
    /**
     * 注入数据源
     */
    @Override
    @Resource(name = "helpCenterDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Override
    public int deleteByKnowledgeId(Long knowledgeId) {
        int r = delete("KnowledgeContent.deleteByKnowledgeId",knowledgeId);
        return r;
    }

    @Override
    public long insert(KnowledgeContent record) {
        long r = (Long)insert("KnowledgeContent.insert",record);
        return r;
    }

    @Override
    public int updateByKnowledgeId(KnowledgeContent record) {
        int r = update("KnowledgeContent.updateByKnowledgeId",record);
        return r;
    }

    @Override
    public int delAllKnowledgeContent() {
        int r = delete("KnowledgeContent.delAllKnowledgeContent");
        return r;
    }

    @Override
    public KnowledgeContent queryContentByKnowledgeId(Long knowledgeId) {
        KnowledgeContent content = (KnowledgeContent) queryForObject("KnowledgeContent.queryContentByKnowledgeId", knowledgeId);
        return content;
    }
}
