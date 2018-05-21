package com.jd.help.admin.web.issue;

import com.jd.common.util.PaginatedList;
import com.jd.common.util.base.PaginatedArrayList;
import com.jd.common.web.result.Result;
import com.jd.help.HelpBaseAction;
import com.jd.help.domain.Issue;
import com.jd.help.domain.IssueSuggest;
import com.jd.help.domain.IssueSuggestQuery;
import com.jd.help.service.IssueService;
import com.jd.help.service.IssueSuggestService;
import com.jd.mongodbclient.util.DateUtil;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

/**
 * Created by lining7 on 2018/1/16.
 */
public class HelpIssueSuggestAction extends HelpBaseAction {

    @Resource
    private IssueSuggestService issueSuggestService;
    @Resource
    private IssueService issueService;

    //��ѯ�Ŀ�ʼʱ��
    private Date beginTime;
    //��ѯ�Ľ���ʱ��
    private Date endTime;

    private Integer issueId;

    public String unsolvedDetail(){
        Result result = new Result(true);
        //��װquery��ѯ����
        IssueSuggestQuery issueSuggestQuery = new IssueSuggestQuery();
        if(null!=beginTime && null!=endTime){
            String beginTimeStr = DateUtil.format(beginTime,"yyyy-MM-dd 00:00:00");
            String endTimeStr = DateUtil.format(endTime, "yyyy-MM-dd 23:59:59");
            issueSuggestQuery.setBeginTime(beginTimeStr);
            issueSuggestQuery.setEndTime(endTimeStr);
        }
        page = (page <= 0 ? 1 : page);
        issueSuggestQuery.setIssueId(issueId);
        issueSuggestQuery.setSolveStatus(0);
        issueSuggestQuery.setStatus(1);
        //��ҳ��ѯ����δ���ԭ��ķ��� return unsolvedList
        List<IssueSuggest> unSolves = issueSuggestService.listForUnSolve(issueSuggestQuery, page, PAGE_SIZE);
        //��װ��ҳ����
        PaginatedList<IssueSuggest> issueSuggests = new PaginatedArrayList<IssueSuggest>(page, PAGE_SIZE);
        for(IssueSuggest suggest : unSolves) {
            if(suggest == null) {
                continue;
            }
            try {
                suggest.setCreator(URLDecoder.decode(suggest.getCreator(), "gbk"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            issueSuggests.add(suggest);
        }
        issueSuggests.setTotalItem(issueSuggestService.queryIssueSolveCount(issueSuggestQuery));

        //��δ�������Ԫ����ӽ���ҳ������
        result.addDefaultModel("issueSuggests", issueSuggests);
        result.addDefaultModel("issueId", issueId);
        //��ȡ���±���
        Issue issue = issueService.queryById(issueId);
        result.addDefaultModel("issueName",issue.getName());
        toVm(result);
        return SUCCESS;
    }

    public IssueSuggestService getIssueSuggestService() {
        return issueSuggestService;
    }

    public void setIssueSuggestService(IssueSuggestService issueSuggestService) {
        this.issueSuggestService = issueSuggestService;
    }


    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }
}
