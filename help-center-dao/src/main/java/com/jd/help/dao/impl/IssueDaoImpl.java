package com.jd.help.dao.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.dao.IssueDao;
import com.jd.help.dao.issue.search.IssueIndexer;
import com.jd.help.dao.util.Page;
import com.jd.help.domain.Issue;
import com.jd.help.domain.IssueQuery;
import com.jd.help.es.search.IssueEsIndexer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * Issue dao实现
 * generated by bud
 *
 * @author @laichendong
 */
@Repository("issueDao")
@SuppressWarnings("unused")
public class IssueDaoImpl extends BaseDao implements IssueDao {

    private final static Log logger = LogFactory.getLog(IssueDaoImpl.class);

    /**
     * 注入数据源
     */
    @Override
    @Resource(name = "helpCenterDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Resource
    private IssueIndexer issueIndexer;

    @Resource
    private IssueEsIndexer issueEsIndexer;


    public long insert(Issue issue) {
        Long id = (Long) insert("Issue.insert", issue);
//        if (id != null && id > 0) {
//            issueIndexer.indexOne(id);
//        }
        return id;
    }

    public int delete(Issue issue) {
        int r = update("Issue.delete", issue);
//        if (r > 0 && issue.getId() != null) {
//            issueIndexer.indexOne(issue.getId());
//        }
        return r;
    }

    public int update(Issue issue) {
        int r = update("Issue.update", issue);
//        if (r > 0 && issue.getId() != null) {
//            issueIndexer.indexOne(issue.getId());
//        }
        return r;
    }

    public Issue queryForObject(Issue issue) {
        try {
            return (Issue) queryForObject("Issue.queryForObject", issue);
        } catch (DataAccessException e) {
            logger.error("数据库访问出错！", e);
            return null;
        }
    }

    public int queryForCount(Issue issue) {
        return (Integer) queryForObject("Issue.queryForCount", issue);
    }

    @SuppressWarnings("unchecked")
    public List<Issue> queryForList(Issue issue, int page, int pageSize) {
        Page<Issue> condition = new Page<Issue>();
        condition.setModel(issue);
        condition.setPage(page);
        condition.setPageSize(pageSize);
        return queryForList("Issue.queryForList", condition);
    }

    @Override
    public int updateStatus(Issue issue) {
        int r = update("Issue.updateStatus", issue);
        int i = -1;
        if (r > 0 && issue.getId() != null) {
//            issueIndexer.indexOne(issue.getId());
            if(issue.getStatus()==0){
                //下线
                i = issueEsIndexer.updateStatus(issue);
            }else if(issue.getStatus()==-1){
                //删除
                i = issueEsIndexer.deleteOne(issue);
            }else{
                //上线
            }
        }
        return r==i?1:-1;
    }
    
    public int updateOrderStatus(Issue issue){
        int r = update("Issue.updateOrderStatus", issue);
        if (r > 0 && issue.getId() != null) {
            issueIndexer.indexOne(issue.getId());
        }
        return r;
    }
    
    public List<Issue> queryOnlineList(Issue issue){
    	return queryForList("Issue.queryOnlineList", issue);
    }
    
    public void deleteOrderStatus() {
    	update("Issue.deleteOrderStatus");
        try{
        	this.issueIndexer.indexAll();
        }catch(Exception e){
        	log.error("index all error.",e);
        }
        logger.info("-------->delete order success!");
      }

    //查询百宝箱中可未删除数据
    @Override
    public List query4Issue2Es(IssueQuery issueQuery) {
        List list = queryForList("Issue.query4Issue2Es", issueQuery);
        return list;
    }

    @Override
    public int queryTotalCount(IssueQuery issueQuery) {
        return (Integer) queryForObject("Issue.queryTotalCount", issueQuery);
    }

    @Override
    public List<Issue> queryForIssueList(IssueQuery issueQuery, int page, int pageSize) {
        Page<IssueQuery> condition = new Page<IssueQuery>();
        condition.setModel(issueQuery);
        condition.setPage(page);
        condition.setPageSize(pageSize);
        return queryForList("Issue.queryForIssueList", condition);
    }

}
