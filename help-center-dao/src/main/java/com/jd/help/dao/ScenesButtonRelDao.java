package com.jd.help.dao;

import com.jd.help.domain.ScenesButtonRel;

import java.util.List;

/**
 * Created by lining7 on 2017/10/10.
 */
public interface ScenesButtonRelDao {
    /**
     * 插入数据库
     * @param scenesButtonRelList
     */
    void insert(List<ScenesButtonRel> scenesButtonRelList);

    /**
     * 删除
     * @param scenesButtonRel
     * @return
     */
    int delete(ScenesButtonRel scenesButtonRel);

    /**
     * type、relId查询数量
     * @param scenesButtonRel
     * @return
     */
    int queryForCount(ScenesButtonRel scenesButtonRel);

    /**
     * type、relId查询列表
     * @param scenesButtonRel
     * @return
     */
    List<ScenesButtonRel> queryForList(ScenesButtonRel scenesButtonRel);
}
