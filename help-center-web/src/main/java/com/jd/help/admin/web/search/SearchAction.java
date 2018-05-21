package com.jd.help.admin.web.search;

import com.jd.common.struts.action.BaseAction;
import com.jd.common.web.result.Result;
import com.jd.help.dao.issue.search.IssueIndexer;

import javax.annotation.Resource;

/**
 * @author laichendong
 * @since 2014/12/10 10:13
 */
public class SearchAction extends BaseAction {

    @Resource
    private IssueIndexer issueIndexer;

    public String dashboard() {
        return SUCCESS;
    }

    public String indexAll() {
        Result result = new Result(true);
        issueIndexer.indexAll();
        result.addDefaultModel("indexAllStarted", true);
        toVm(result);
        return SUCCESS;
    }
}
