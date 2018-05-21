package com.jd.help.dao;

import com.jd.help.domain.ScenesButtonRel;

import java.util.List;

/**
 * Created by lining7 on 2017/10/10.
 */
public interface ScenesButtonRelDao {
    /**
     * �������ݿ�
     * @param scenesButtonRelList
     */
    void insert(List<ScenesButtonRel> scenesButtonRelList);

    /**
     * ɾ��
     * @param scenesButtonRel
     * @return
     */
    int delete(ScenesButtonRel scenesButtonRel);

    /**
     * type��relId��ѯ����
     * @param scenesButtonRel
     * @return
     */
    int queryForCount(ScenesButtonRel scenesButtonRel);

    /**
     * type��relId��ѯ�б�
     * @param scenesButtonRel
     * @return
     */
    List<ScenesButtonRel> queryForList(ScenesButtonRel scenesButtonRel);
}
