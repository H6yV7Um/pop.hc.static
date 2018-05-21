package com.jd.help.dao.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.dao.KnowledgeDao;
import com.jd.help.domain.knowledge.Knowledge;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Created by yfxialiang on 2018/4/8.
 */
@Repository("knowledgeDao")
@SuppressWarnings("unused")
public class KnowledgeDaoImpl extends BaseDao implements KnowledgeDao {
    /**
     * 注入数据源
     */
    @Override
    @Resource(name = "helpCenterDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        int r = delete("Knowledge.deleteByPrimaryKey",id);
        return r;
    }

    @Override
    public long insert(Knowledge record) {
        long r = (Long)insert("Knowledge.insert",record);
        return r;
    }

    @Override
    public List<Knowledge> queryByMap(Map map) {
        List<Knowledge> r = (List<Knowledge>)queryForList("Knowledge.queryByMap", map);
        return r;
    }

    @Override
    public int updateByPrimaryKeySelective(Knowledge record) {
        int r = update("Knowledge.updateByPrimaryKeySelective",record);
        return r;
    }

    @Override
    public int delAllKnowledge() {
        int r = delete("Knowledge.deleteAllKnowledge");
        return r;
    }

}
