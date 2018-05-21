package com.jd.help.center.admin.question;

import com.jd.common.struts.action.BaseAction;
import com.jd.common.web.result.Result;
import com.jd.help.center.service.category.FrontQuestionService;
import com.jd.help.center.service.navigate.LeftNavigateService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-9-1
 * Time: 15:10:44
 * To change this template use File | Settings | File Templates.
 */
public abstract class FrontBaseAction extends BaseAction {
    protected int topicId;

    protected String sysName;

    protected FrontQuestionService frontQuestionService;

    protected LeftNavigateService leftNavigateService;

    protected Result result;

    @Resource(name = "GISUrl")
    protected String GISUrl;

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
        if (response != null) {
            response.setHeader("Cache-Control", "max-age=1800");
            response.addDateHeader("Last-Modified", System.currentTimeMillis() + 1800 * 1000);
        }
    }

    public abstract String doExecute() throws Exception;

    public abstract Cookie getAddressCookie();

    public abstract void writeAddressCookie(String value);


    public void setFrontQuestionService(FrontQuestionService frontQuestionService) {
        this.frontQuestionService = frontQuestionService;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public LeftNavigateService getLeftNavigateService() {
        return leftNavigateService;
    }

    public void setLeftNavigateService(LeftNavigateService leftNavigateService) {
        this.leftNavigateService = leftNavigateService;
    }

}
