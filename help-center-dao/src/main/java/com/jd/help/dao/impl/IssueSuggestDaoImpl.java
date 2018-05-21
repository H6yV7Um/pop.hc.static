package com.jd.help.dao.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.dao.IssueSuggestDao;
import com.jd.help.dao.util.Page;
import com.jd.help.domain.IssueSuggest;
import com.jd.help.domain.IssueSuggestQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lipengfei5 on 2016/9/26.
 */
@Repository("issueSuggestDao")
public class IssueSuggestDaoImpl extends BaseDao implements IssueSuggestDao{

    /**
     * 注入数据源
     */
    @Override
    @Resource(name = "helpCenterDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
    @Override
    public long insert(IssueSuggest issueSuggest) {
        return (Long) insert("IssueSuggest.insert",issueSuggest);
    }

    @Override
    public int update(IssueSuggest issueSuggest) {
        return update("IssueSuggest.update",issueSuggest);
    }

    @Override
    public IssueSuggest queryForObject(Integer id) {
        return (IssueSuggest) queryForObject("IssueSuggest.queryForObject",id);
    }

    @Override
    public List<IssueSuggest> queryForList(IssueSuggest issueSuggest,int page,int pageSize) {
        Page<IssueSuggest> condition = new Page<IssueSuggest>();
        condition.setModel(issueSuggest);
        condition.setPage(page);
        condition.setPageSize(pageSize);
        return queryForList("IssueSuggest.queryList",condition);
    }

    public Long queryAllNumber(Integer issueId){
        return (Long)queryForObject("IssueSuggest.queryAllNumber",issueId);
    }

    public IssueSuggest queryOne(IssueSuggest issueSuggest){
        return (IssueSuggest) queryForObject("IssueSuggest.queryOne",issueSuggest);
    }

    @Override
    public Integer queryIssueSolveCount(IssueSuggestQuery issueSuggestQuery) {
        return (Integer) queryForObject("IssueSuggest.queryIssueSolveCount", issueSuggestQuery);
    }

    @Override
    public List<IssueSuggest> queryIssueUnSolveList(IssueSuggestQuery issueSuggestQuery, int page, int pageSize) {
        Page<IssueSuggestQuery> condition = new Page<IssueSuggestQuery>();
        condition.setModel(issueSuggestQuery);
        condition.setPage(page);
        condition.setPageSize(pageSize);
        return queryForList("IssueSuggest.queryIssueUnSolveList",condition);
    }

    @Override
    public int queryUnSolveSuggestsCount(IssueSuggestQuery issueSuggestQuery) {
        return (Integer) queryForObject("IssueSuggest.queryUnSolveSuggestsCount", issueSuggestQuery);
    }
}
