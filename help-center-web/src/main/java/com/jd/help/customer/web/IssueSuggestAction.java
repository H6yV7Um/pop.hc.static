package com.jd.help.customer.web;

import com.jd.common.web.result.Result;
import com.jd.help.HelpBaseAction;
import com.jd.help.IssueSuggestEnum;
import com.jd.help.center.web.util.ShopWebHelper;
import com.jd.help.domain.Issue;
import com.jd.help.domain.IssueSuggest;
import com.jd.help.service.IssueSuggestService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lipengfei5 on 2016/9/27.
 */
public class IssueSuggestAction extends HelpBaseAction {
    @Resource
    private IssueSuggestService issueSuggestService;
    private IssueSuggest issueSuggest;
    private Issue issue;
    private Map<String,Object> jsonRoot = new HashMap<String, Object>();
    public String doInsert(){
        if(issueSuggest == null || issueSuggest.getIssueId() == null){
            jsonRoot.put("isSuccess",false);
            return "json";
        }
        issueSuggest.setCreator(ShopWebHelper.getPin());
        issueSuggest.setModifier(ShopWebHelper.getPin());
        issueSuggest.setrFid(ShopWebHelper.getPin());
        issueSuggest.setStatus(1);
        issueSuggest.setSuggestStatus(IssueSuggestEnum.UN_SUGGEST.getStatus());
        boolean result = issueSuggestService.insert(issueSuggest);
        jsonRoot.put("isSuccess",result);
        return "json";
    }
    public String doUpdate(){
        Result result = new Result(false);
        issueSuggest.setModifier(ShopWebHelper.getPin());
        IssueSuggest isg = new IssueSuggest();
        isg.setIssueId(issue.getId());
        isg.setrFid(ShopWebHelper.getPin());
        issueSuggest.setId(issueSuggestService.findOne(isg).getId());
        issueSuggest.setSuggestStatus(IssueSuggestEnum.SUGGESTED.getStatus());
        boolean isSuccess = issueSuggestService.update(issueSuggest);
        jsonRoot.put("isSuccess",isSuccess);
        return "json";
    }
    public String findAll(){
        return SUCCESS;
    }

    public String findAllNumber(){
        Long allNum = issueSuggestService.findAllNumber(issueSuggest);
        if(allNum<0){
            jsonRoot.put("isSuccess",false);
            return "json";
        }
        jsonRoot.put("isSuccess",true);
        jsonRoot.put("allNum",allNum);
        return "json";
    }
    /*public String findOne(){
        Result result = new Result()
        if(issueSuggest.getId() != null){
            issueSuggestService.selectOneById(issueSuggest.getId());
        }
        issueSuggestService.findOne(issueSuggest);
    }*/
    public IssueSuggestService getIssueSuggestService() {
        return issueSuggestService;
    }

    public void setIssueSuggestService(IssueSuggestService issueSuggestService) {
        this.issueSuggestService = issueSuggestService;
    }

    public IssueSuggest getIssueSuggest() {
        return issueSuggest;
    }

    public void setIssueSuggest(IssueSuggest issueSuggest) {
        this.issueSuggest = issueSuggest;
    }

    public Map<String, Object> getJsonRoot() {
        return jsonRoot;
    }

    public void setJsonRoot(Map<String, Object> jsonRoot) {
        this.jsonRoot = jsonRoot;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
}
