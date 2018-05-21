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
     * ע������Դ
     */
    @Override
    @Resource(name = "helpCenterDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    /**
     * ɾ��������֪ʶ
     *
     * @param id                  ����id
     * @param relationKnowledgeId ������֪ʶ
     * @return int 1��ɾ���ɹ� 0��ʧ��
     */
    @Override
    public int deleteWeakRelation(Long id, Long relationKnowledgeId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("relationKnowledgeId", relationKnowledgeId);
        return delete("RelationKnowledge.deleteWeakRelation", paramMap);
    }

    /**
     * ɾ�����еĹ���֪ʶ
     * �÷������޸�ʱ����
     *
     * @param id �������ĵ�֪ʶid
     * @return int 0��ʧ��
     */
    @Override
    public int deleteRelation(Long id) {
        return update("RelationKnowledge.deleteRelation", id);
    }

    /**
     * ��������
     *
     * @param relationKnowledge ֪ʶ����
     * @return long
     */
    @Override
    public long insert(RelationKnowledge relationKnowledge) {
        insert("RelationKnowledge.insert", relationKnowledge);
        return 1L;
    }


    /**
     * ��ѯ֪ʶ��ǿ����֪ʶ�б�
     *
     * @param id ֪ʶid
     * @return List<RelationKnowledge>
     */
    @Override
    public  List<RelationKnowledge> listRelationKnowledge(Long id) {
        return (List<RelationKnowledge>) queryForList("RelationKnowledge.listRelationKnowledge", id);
    }

}
