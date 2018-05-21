package com.jd.help.dao.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.dao.RelationKnowledgeDao;
import com.jd.help.domain.RelationKnowledge;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyu1099
 * @date 2018/04/16
 */
@Repository("relationKnowledgeDao")
@SuppressWarnings("unused")
public class RelationKnowledgeDaoImpl extends BaseDao implements RelationKnowledgeDao {
    /**
     * 注入数据源
     */
    @Override
    @Resource(name = "helpCenterDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    /**
     * 删除弱关联知识
     *
     * @param id                  主键id
     * @param relationKnowledgeId 弱关联知识
     * @return int 1：删除成功 0：失败
     */
    @Override
    public int deleteWeakRelation(Long id, Long relationKnowledgeId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("relationKnowledgeId", relationKnowledgeId);
        return delete("RelationKnowledge.deleteWeakRelation", paramMap);
    }

    /**
     * 删除所有的关联知识
     * 该方法在修改时调用
     *
     * @param id 帮助中心的知识id
     * @return int 0：失败
     */
    @Override
    public int deleteRelation(Long id) {
        return update("RelationKnowledge.deleteRelation", id);
    }

    /**
     * 新增数据
     *
     * @param relationKnowledge 知识对象
     * @return long
     */
    @Override
    public long insert(RelationKnowledge relationKnowledge) {
        insert("RelationKnowledge.insert", relationKnowledge);
        return 1L;
    }


    /**
     * 查询知识的强关联知识列表
     *
     * @param id 知识id
     * @return List<RelationKnowledge>
     */
    @Override
    public  List<RelationKnowledge> listRelationKnowledge(Long id) {
        return (List<RelationKnowledge>) queryForList("RelationKnowledge.listRelationKnowledge", id);
    }

}
