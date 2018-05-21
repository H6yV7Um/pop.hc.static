package com.jd.help.dao.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.dao.ScenesButtonRelDao;
import com.jd.help.domain.ScenesButtonRel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created by lining7 on 2017/10/10.
 */
@Repository("scenesButtonRelDao")
public class ScenesButtonRelDaoImpl extends BaseDao implements ScenesButtonRelDao {
    
    private final static Log logger = LogFactory.getLog(ScenesButtonRelDaoImpl.class);

    /**
     * 注入数据源
     */
    @Override
    @Resource(name = "helpCenterDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Override
    public void insert(List<ScenesButtonRel> scenesButtonRelList) {
        insert("ScenesButtonRel.insert", scenesButtonRelList);
    }

    @Override
    public int delete(ScenesButtonRel scenesButtonRel) {
        return delete("ScenesButtonRel.delete", scenesButtonRel);
    }

    @Override
    public int queryForCount(ScenesButtonRel scenesButtonRel) {
        return (Integer) queryForObject("ScenesButtonRel.queryForCount", scenesButtonRel);
    }

    @Override
    public List<ScenesButtonRel> queryForList(ScenesButtonRel scenesButtonRel) {
        return queryForList("ScenesButtonRel.queryForList", scenesButtonRel);
    }
}
