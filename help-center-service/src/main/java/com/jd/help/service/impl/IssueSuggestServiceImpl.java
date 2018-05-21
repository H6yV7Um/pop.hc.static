package com.jd.help.service.impl;

import com.jd.common.web.result.Result;
import com.jd.help.dao.IssueSuggestDao;
import com.jd.help.domain.IssueSuggest;
import com.jd.help.domain.IssueSuggestQuery;
import com.jd.help.service.IssueSuggestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lipengfei5 on 2016/9/26.
 */
@Service("issueSuggestService")
public class IssueSuggestServiceImpl implements IssueSuggestService {
    @Resource
    private IssueSuggestDao issueSuggestDao;
    @Override
    public boolean insert(IssueSuggest issueSuggest) {
        long id = issueSuggestDao.insert(issueSuggest);
        if(id>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean update(IssueSuggest issueSuggest) {
        if(issueSuggest == null || issueSuggest.getId() == null){
            return false;
        }
        return issueSuggestDao.update(issueSuggest)>0;
    }

    @Override
    public IssueSuggest selectOneById(int id) {
        if(id<0){
            return new IssueSuggest();
        }
        return issueSuggestDao.queryForObject(id);
    }

    @Override
    public List<IssueSuggest> list(IssueSuggest issueSuggest, int page, int pageSize) {
        if(issueSuggest == null){
            return new ArrayList<IssueSuggest>();
        }
        return issueSuggestDao.queryForList(issueSuggest,page,pageSize);
    }
    @Override
    public Long findAllNumber(IssueSuggest issueSuggest){
        if(issueSuggest == null || issueSuggest.getIssueId() == null){
            return 0L;
        }
        return issueSuggestDao.queryAllNumber(issueSuggest.getIssueId());
    }
    @Override
    public IssueSuggest findOne(IssueSuggest issueSuggest){
        if(issueSuggest == null || issueSuggest.getIssueId() == null || issueSuggest.getrFid() == null){
            return new IssueSuggest();
        }
        IssueSuggest IssueSuggestResult = issueSuggestDao.queryOne(issueSuggest);
        if(IssueSuggestResult == null){
            return new IssueSuggest();
        }
        return IssueSuggestResult;
    }

    @Override
    public int queryIssueSolveCount(IssueSuggestQuery issueSuggestQuery) {
        if(issueSuggestQuery == null || issueSuggestQuery.getIssueId() == null || issueSuggestQuery.getSolveStatus() == null){
            return 0;
        }
        return issueSuggestDao.queryIssueSolveCount(issueSuggestQuery);
    }

    @Override
    public List<IssueSuggest> listForUnSolve(IssueSuggestQuery issueSuggestQuery, int page , int pageSize) {
        if(issueSuggestQuery == null || issueSuggestQuery.getIssueId() == null){
            return  new ArrayList<IssueSuggest>();
        }
        issueSuggestQuery.setSolveStatus(0);
//        issueSuggestQuery.setSuggestStatus(1);
        issueSuggestQuery.setStatus(1);
        return issueSuggestDao.queryIssueUnSolveList(issueSuggestQuery, page, pageSize);
    }
}
