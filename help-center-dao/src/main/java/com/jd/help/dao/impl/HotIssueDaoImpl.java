package com.jd.help.dao.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.dao.HotIssueDao;
import com.jd.help.dao.util.Page;
import com.jd.help.domain.HotIssue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * HotIssue dao实现
 * generated by bud
 *
 * @author @laichendong
 */
@Repository("hotIssueDao")
@SuppressWarnings("unused")
public class HotIssueDaoImpl extends BaseDao implements HotIssueDao {

    private final static Log logger = LogFactory.getLog(HotIssueDaoImpl.class);

    /**
     * 注入数据源
     */
    @Override
    @Resource(name = "helpCenterDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }


    public long insert(HotIssue hotIssue) {
        return (Long) insert("HotIssue.insert", hotIssue);
    }

    public int delete(HotIssue hotIssue) {
        return update("HotIssue.delete", hotIssue);
    }

    public int update(HotIssue hotIssue) {
        return update("HotIssue.update", hotIssue);
    }

    public HotIssue queryForObject(HotIssue hotIssue) {
        try {
            return (HotIssue) queryForObject("HotIssue.queryForObject", hotIssue);
        } catch (DataAccessException e) {
            logger.error("数据库访问出错！", e);
            return null;
        }
    }

    public int queryForCount(HotIssue hotIssue) {
        return (Integer) queryForObject("HotIssue.queryForCount", hotIssue);
    }

    @SuppressWarnings("unchecked")
    public List<HotIssue> queryForList(HotIssue hotIssue, int page, int pageSize) {
        Page<HotIssue> condition = new Page<HotIssue>();
        condition.setModel(hotIssue);
        condition.setPage(page);
        condition.setPageSize(pageSize);
        return queryForList("HotIssue.queryForList", condition);
    }

}
