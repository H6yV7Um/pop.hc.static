package com.jd.help.center.admin.search;

import com.jd.common.util.StringUtils;
import com.jd.common.web.result.Result;
import com.jd.help.center.admin.base.HelpBaseAction;
import com.jd.help.center.domain.HelpCenterLeftNavigate;
import com.jd.help.center.domain.constants.UMPConstants;
import com.jd.help.center.domain.helpsys.HelpSYS;
import com.jd.help.center.domain.question.HelpQuestion;
import com.jd.help.center.domain.search.SearchResult;
import com.jd.help.center.domain.search.SearchResultPage;
import com.jd.help.center.domain.search.SearchResultParagraph;
import com.jd.help.center.domain.search.SearchResultParagraphContent;
import com.jd.help.center.service.category.FrontQuestionService;
import com.jd.help.center.service.navigate.LeftNavigateService;
import com.jd.mongodbclient.util.JsonCommon;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuxianjun
 * Date: 13-4-23
 * Time: 上午9:30
 * To change this template use File | Settings | File Templates.
 */
public class HelpSearchAction extends HelpBaseAction {

    private static final Log log = LogFactory.getLog(HelpSearchAction.class);
    protected int topicId;

    protected String sysName;

    protected int questionId;

    protected String mainword;
    protected String mainwordinput;
    protected FrontQuestionService frontQuestionService;
    private LeftNavigateService leftNavigateService;

    private int page;
    private int pagesize = 15;
    private String sort_type;

    final String CHARSET = "GBK";

    private String searchDomain;

    public String search() {

        CallerInfo info = Profiler.registerInfo(UMPConstants.UMP_HELP_CENTER_SEARCH, UMPConstants.enableHeart, UMPConstants.enableTP);
        SearchResult searchResult = null;
        Result result = leftNavigateService.getLeftNavigateResult(sysName);
        int sysId  = (Integer) result.get("systemId");


        ArrayList<SearchResultParagraph> Paragraph = null;
        SearchResultPage searchResultPage = null;


        long resultCount = 0;
        try {
            if (sysId < 1) {
                this.toVm(result);
                return ERROR;
            }
            String json = getSearch(sysId);
            if (StringUtils.isNotBlank(json)) {
                searchResult = JsonCommon.toObject(json, SearchResult.class);
                resultCount = searchResult.getHead().getSummary().getResultCount();
                Paragraph = searchResult.getParagraph();
                searchResultPage = searchResult.getHead().getSummary().getPage();
                //搜索关键字标红
//                if (Paragraph != null && Paragraph.size() > 0) {
//                    if (StringUtils.isNotBlank(mainwordinput)) {
//                        String[] keys = mainwordinput.trim().split(" ");
//                        for (SearchResultParagraph rp : Paragraph) {
//                            SearchResultParagraphContent content = rp.getContent();
//                            if (content == null)
//                                continue;
//                            String answer = content.getAnswer();
//                            String question = content.getQuestion();
////                            boolean hasAnswer = StringUtils.isNotBlank(answer);
////                            boolean hasQuestion = StringUtils.isNotBlank(question);
////                            for (String key : keys) {
////                                if (hasAnswer)
////                                    answer = answer.replace(key, "<span style='color:red'>" + key + "</span>");
////                                if (hasQuestion)
////                                    question = question.replace(key, "<span style='color:red'>" + key + "</span>");
////                            }
//                            content.setAnswer(answer);
//                            content.setQuestion(question);
//                        }
//                    }
//                }
            }
        } catch (Exception e) {
            log.error("UMPConstants.UMP_HELP_CENTER_SEARCH",e);
            log.error("获取查询返回信息出错，json", e);
            resultCount = 0;
            Paragraph = null;
            searchResultPage = null;

        }
        //搜索结果为空，显示常见问题
        if (resultCount == 0 || Paragraph == null || Paragraph.size() < 1) {
            Result resultQuestion = frontQuestionService.getQuestion(sysName, 61);
            result.addDefaultModel("questions", resultQuestion.get("questions"));
            Profiler.functionError(info);
        }else
            Profiler.registerInfoEnd(info);
        result.addDefaultModel("resultCount", resultCount);
        result.addDefaultModel("paragraph", Paragraph);
        result.addDefaultModel("searchResultPage", searchResultPage);
        result.addDefaultModel("searchResult", searchResult);
        if(StringUtils.isNotBlank(mainwordinput) && mainwordinput.length() > 20){
            String omitMainWordInput = mainwordinput.substring(0,20)+"...";
            result.addDefaultModel("omitMainWordInput",omitMainWordInput);
        }else {
            result.addDefaultModel("omitMainWordInput",mainwordinput);
        }
        this.toVm(result);

        return "search_" + sysName;
    }

    private String getSearch(int sysId) {
        String body = "";
        try {
            //构造HttpClient的实例
            HttpClient httpClient = new HttpClient();
            mainwordinput = URLDecoder.decode(mainword, "UTF-8");
            mainwordinput = URLDecoder.decode(mainwordinput, "UTF-8");
            String m = URLEncoder.encode(mainwordinput, CHARSET);

            //系统过滤
            String filt_type = "&filt_type=sys_id,l" + sysId + "m" + sysId;
            //分页
            if (page < 1)
                page = 1;
            pagesize = 15;
            if (pagesize < 1)
                pagesize = 15;
            String searchPage = "&page=" + page + "&pagesize=" + pagesize;
            String url =    searchDomain + "?key=" + m + filt_type + searchPage;
            GetMethod getMethod = new GetMethod(url);

            getMethod.setRequestHeader("Content-Type", "text/html; charset=" + CHARSET);

            //使用系统提供的默认的恢复策略
            DefaultHttpMethodRetryHandler handler = new DefaultHttpMethodRetryHandler();
            getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, handler);
            //定义五秒钟的超时
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
            //执行getMethod
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                log.error("method failure: " + getMethod.getStatusLine());
            } else {
                body = EncodingUtil.getString(getMethod.getResponseBody(), CHARSET);
            }
        } catch (Exception e) {
            log.error("Exception  is happening", e);
        }

        return body;
    }

    public String viewQuestion() {
        Result result = leftNavigateService.getLeftNavigateResult(sysName);

        Result resultQuestion = frontQuestionService.previewQuestion(topicId);
        List<HelpQuestion> list = (List<HelpQuestion>) resultQuestion.get("questions");
        if (list == null || list.size() < 1 || questionId < 1)
            return ERROR;
        HelpQuestion question = null;
        for (HelpQuestion q : list) {
            if (questionId == q.getQuestionId()) {
                question = q;
                break;
            }
        }
        if (question == null)
            return ERROR;
        result.addDefaultModel("viewQuestion", question);
        toVm(result);
        return "search_" + sysName;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getMainword() {
        return mainword;
    }

    public void setMainword(String mainword) {
        this.mainword = mainword;
    }

    public FrontQuestionService getFrontQuestionService() {
        return frontQuestionService;
    }

    public void setFrontQuestionService(FrontQuestionService frontQuestionService) {
        this.frontQuestionService = frontQuestionService;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public String getSearchDomain() {
        return searchDomain;
    }

    public void setSearchDomain(String searchDomain) {
        this.searchDomain = searchDomain;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getMainwordinput() {
        return mainwordinput;
    }

    public void setMainwordinput(String mainwordinput) {
        this.mainwordinput = mainwordinput;
    }

    public LeftNavigateService getLeftNavigateService() {
        return leftNavigateService;
    }

    public void setLeftNavigateService(LeftNavigateService leftNavigateService) {
        this.leftNavigateService = leftNavigateService;
    }
}
