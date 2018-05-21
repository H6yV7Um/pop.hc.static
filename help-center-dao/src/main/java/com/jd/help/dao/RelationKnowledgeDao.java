package com.jd.help.dao;

import com.jd.help.domain.RelationKnowledge;

import java.util.List;

public interface RelationKnowledgeDao {

    /**
     * ɾ��������֪ʶ
     *
     * @param id ����id
     * @param relationKnowledgeId ������֪ʶ
     * @return int 1��ɾ���ɹ� 0��ʧ��
     */
    int deleteWeakRelation(Long id, Long relationKnowledgeId);

    /**
     * ɾ�����еĹ���֪ʶ
     * �÷������޸�ʱ����
     *
     * @param id �������ĵ�֪ʶid
     * @return int 0��ʧ��
     */
    int deleteRelation(Long id);

    /**
     * ��������
     *
     * @param record ֪ʶ����
     * @return long
     */
    long insert(RelationKnowledge record);

    /**
     * ��ѯ֪ʶ��ǿ����֪ʶ�б�
     *
     * @param id ֪ʶid
     * @return List<RelationKnowledge>
     */
    List<RelationKnowledge> listRelationKnowledge(Long id);
}