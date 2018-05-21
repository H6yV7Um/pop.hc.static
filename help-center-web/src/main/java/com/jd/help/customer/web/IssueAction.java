package com.jd.help.customer.web;

import com.jd.common.struts.context.LoginContext;
import com.jd.common.util.PaginatedList;
import com.jd.common.util.StringEscapeUtils;
import com.jd.common.web.result.Result;
import com.jd.help.HelpBaseAction;
import com.jd.help.SysLoginContext;
import com.jd.help.center.web.util.ShopWebHelper;
import com.jd.help.center.web.util.WebHelper;
import com.jd.help.dao.issue.search.IssueSearcher;
import com.jd.help.domain.*;
import com.jd.help.es.IssueEsService;
import com.jd.help.jmq.producer.saveKeywordProducer;
import com.jd.help.service.IssueService;
import com.jd.help.service.IssueSuggestService;
import com.jd.help.service.KeywordService;
import com.jd.help.service.LocalCacheService;
import com.jd.jim.cli.Cluster;
import com.jd.pop.component.url.PopJdUrl;
import com.jd.ump.annotation.JProfiler;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.aspectj.weaver.ast.Test;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author laichendong
 * @since 2014/12/12 14:58
 */
public class IssueAction extends HelpBaseAction {
    /**
     * 搜索关键字
     */
    String keyword;

    /**
     * 类目
     */
    private Catalog catalog;

    /**
     * 问题
     */
    private Issue issue;

    /**
     * 问题issueBO
     * 预览用
     */
    private IssueBO issueBO;

    /**
     * 新老链接映射
     * 老系统链接
     */
    private String oldFromUrl;

    @Resource
    private IssueSearcher issueSearcher;

    @Resource
    private IssueService issueService;

    @Resource
    private LocalCacheService localCacheService;
    @Resource
    private PopJdUrl venderHelpCenterModule;
    @Resource
    private IssueSuggestService issueSuggestService;

    @Resource
    private saveKeywordProducer producer;

    @Resource
    private IssueEsService issueEsService;

    @Resource
    private KeywordService keywordService;

    @Resource(name = "jimClient")
    private Cluster jimClient;

    Log log = LogFactory.getLog(IssueAction.class);
    private Map<String, Object> jsonRoot = new HashMap<String, Object>();
    /**
     * 搜索结果页
     *
     * @return
     */
    public String search() {
        CallerInfo info = Profiler.registerInfo("vender.help.customer.issue.IssueAction.search", false, true);
    	// 初始化站点
        initSite();

        Result result = new Result(true);
        if("venderportal".equals(siteEnName) || "vender".equals(siteEnName)){
            initAllLeftMenu(result);
        }else {
            initLeftMenu(result);
        }
        if (StringUtils.isBlank(keyword)) {
            toVm(result);
            return SUCCESS;
        }

        // 关键字
        keyword = StringEscapeUtils.escapeHtml(keyword.trim());

        if(keyword.length() <= 20&& page <= 0&&"true".equalsIgnoreCase(jimClient.get("recordKeywordSwitch"))){
            //封装关键词信息
            Keyword keywordDto = new Keyword();
            keywordDto.setKeyword(keyword);
            keywordDto.setCreatePin(SysLoginContext.getUserPin());
            keywordDto.setCreateTime(new Date());
            keywordDto.setVenderId(SysLoginContext.getVenderId());
            //使用jmq发送消息
            producer.saveKeyword(keywordDto);
            log.info("【IssueAction.search】：发送了保存关键词jmq");
        }

        page = (page <= 0 ? 1 : page);
        PaginatedList<IssueVO> issueList = issueEsService.searchArticle(keyword, page, PAGE_SIZE);
        result.addDefaultModel("issueList", issueList);

        toVm(result);
        Profiler.registerInfoEnd(info);
        return SUCCESS;
    }

    @JProfiler(jKey = "vender.help.customer.method.searchSuggest", jAppName = "vender_help_center")
    public String suggest(){
        // 初始化站点
        initSite();
        if (StringUtils.isBlank(keyword)) {
            jsonRoot.put("isSuccess",false);
            return "json";
        }
        // 关键字
        keyword = StringEscapeUtils.escapeHtml(keyword.trim());
        if (containSolrSyntaxChars(keyword)) {
            jsonRoot.put("isSuccess",false);
            return "json";
        }
        //获取文章标题
        List<String> wordList = issueEsService.searchArticleTitle(keyword);
        jsonRoot.put("isSuccess",true);
        jsonRoot.put("wordList",wordList);
        return "json";
    }

    private boolean containSolrSyntaxChars(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // These characters are part of the query syntax and must be escaped
            if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':'
                    || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'
                    || c == '*' || c == '?' || c == '|' || c == '&' || c == ';' || c == '/') {
                return true;
            }
        }
        return false;
    }

    public String question() {
        initSite();
        Result result = new Result(true);
        String jimiUrl = "//jimi.jd.com/index.action?source=helpCenterEmbed";
        if (StringUtils.isNotBlank(keyword)) {
            try {
                keyword = URLEncoder.encode(keyword.trim(), "utf8");
                jimiUrl += "&fq=" + keyword;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        result.addDefaultModel("jimiUrl", jimiUrl);
        toVm(result);
        return SUCCESS;
    }

    public String issueAnswer() throws Exception {
    	CallerInfo info = Profiler.registerInfo("vender.help.customer.issue.IssueAction.issueAnswer", false, true);
        try{
        	// 初始化站点
            initSite();
            IssueBO issueBO = new IssueBO();
            issueBO.setIssue(issue);
            Result result = issueService.queryIssueBOByIssueId(issueBO, true);
            Catalog cat = new Catalog();
            cat.setId(issue.getCataId());
            Catalog catalog3 = (Catalog) catalogService.detail(cat).get("catalog");
            result.addDefaultModel("catalog", catalog3);
            if (catalog3.getPid() != 0) {
                cat.setId(catalog3.getPid());
                Catalog catalog2 = (Catalog) catalogService.detail(cat).get("catalog");
                result.addDefaultModel("catalog2", catalog2);
                if (catalog2.getPid() != 0) {
                    cat.setId(catalog2.getPid());
                    Catalog catalog1 = (Catalog) catalogService.detail(cat).get("catalog");
                    result.addDefaultModel("catalog1", catalog1);
                }
            }
            IssueBO isBO = (IssueBO)result.get("issueBO");
            String userPin = SysLoginContext.getUserPin();
            if(LoginContext.getLoginContext() != null && userPin != null){
                IssueSuggest issueSuggest = new IssueSuggest();
                issueSuggest.setIssueId(isBO.getIssue().getId());
                issueSuggest.setrFid(userPin);
                List<IssueSuggest> list = issueSuggestService.list(issueSuggest, 1, 10);
                result.addDefaultModel("issueSuggestVO", list.size() == 0 ? new IssueSuggest():list.get(0));
            }
           result.addDefaultModel("allNum", issueSuggestService.findAllNumber(new IssueSuggest(isBO.getIssue().getId())));
            result.addDefaultModel("userPin", userPin==null?"": userPin);
            log.error("SysLoginContext.getUserPin():" + userPin);
            if("vender".equals(siteEnName)){
                initAllLeftMenu(result);
            }else{
                initLeftMenu(result);
            }
            toVm(result);
            return SUCCESS;
        }catch(Exception e){
            log.error("vender.help.customer.issue.IssueAction.issueAnswer",e);
            log.error("问题详情异常：", e);
        	Profiler.functionError(info);
        	throw e;
        }finally{
        	Profiler.registerInfoEnd(info);
        }
    	
    }

    /**
     * 获取评价详情，避免CDN缓存
     * @return
     * @throws Exception
     */
    public String findSuggestDetail() throws Exception {
        CallerInfo info = Profiler.registerInfo("vender.help.customer.issue.IssueAction.getSuggestDetail", false, true);
        try{
            if(LoginContext.getLoginContext() != null && ShopWebHelper.getPin() != null){
                IssueSuggest issueSuggest = new IssueSuggest();
                issueSuggest.setIssueId(issue.getId());
                issueSuggest.setrFid(ShopWebHelper.getPin());
                List<IssueSuggest> list = issueSuggestService.list(issueSuggest, 1, 10);
                jsonRoot.put("issueSuggest",list.size() == 0 ? new IssueSuggest():list.get(0));
            }
            jsonRoot.put("allNum",issueSuggestService.findAllNumber(new IssueSuggest(issue.getId())));
            jsonRoot.put("userPin", ShopWebHelper.getPin());
            log.error("WebHelper.getPin():" + WebHelper.getPin());
        }catch(Exception e){
            log.error("vender.help.customer.issue.IssueAction.getSuggestDetail",e);
            log.error("获取评价详情异常：", e);
            Profiler.functionError(info);
            throw e;
        }finally{
            Profiler.registerInfoEnd(info);
            return "json";
        }

    }

    /**
     * 问题预览
     *
     * @return
     */
    public String issuePreview() {
        // 初始化站点
        initSite();
        Result result = new Result();
        if("vender".equals(siteEnName)){
            initAllLeftMenu(result);
        }else {
            initLeftMenu(result);
        }
        result.addDefaultModel("issueBO", issueBO);
        if (catalog != null) {
            result.addDefaultModel("catalog", catalogService.detail(catalog).get("catalog"));
        }
        toVm(result);
        return SUCCESS;
    }

    public String issueList() {
        // 初始化站点
        initSite();
        Result result = new Result();
        if("vender".equals(siteEnName) || "venderportal".equals(siteEnName)){
            siteEnName = "vender";
            initAllLeftMenu(result);
        }else{
            result = catalogService.getCatalogLevel3List(catalog);
            initLeftMenu(result);
        }
        page = (page <= 0 ? 1 : page);
        PaginatedList<IssueVO> issueList = issueEsService.searchIssueByCategoryId(new Long(catalog.getId()), page, PAGE_SIZE);
        result.addDefaultModel("issueList", issueList);
        result.addDefaultModel("currentCatalog", catalog);

        toVm(result);
        return SUCCESS;
    }

    public String oldUrlFilter() {
    	CallerInfo info = Profiler.registerInfo("vender.help.customer.issue.IssueAction.oldUrlFilter", false, true);
    	Map<String, IssueOldNewMappingBO> map = localCacheService.urlMappingCache();
        Result r = new Result();
        //String oldPath = "/" + sysName + "/question-" + topicId + ".html";
        String oldUrl = "http:" + venderHelpCenterModule.getTarget(oldFromUrl).render();

        IssueOldNewMappingBO mappingBO = map.get(oldUrl);
        if (mappingBO != null) {
            siteId = mappingBO.getSiteId();
            initSite();
            StringBuffer newPath = new StringBuffer();
            newPath.append("/").append(site.getEnName()).append("/issue/")
                    .append(mappingBO.getCataId()).append("-").append(mappingBO.getIssueId()).append(".html");
            String newUrl = "http:" + venderHelpCenterModule.getTarget(newPath.toString()).render();
            r.addDefaultModel("redirectUrl", newUrl);
        } else {
            //r.addDefaultModel("redirectUrl", venderHelpCenterModule.getTarget("/user/index.html").render());
        	r.addDefaultModel("redirectUrl", "http:" + venderHelpCenterModule.getTarget("/o"+oldFromUrl).render());
        	Profiler.registerInfoEnd(info);
        }
        toVm(r);
        return "toNew";

    }
    public String thick(){
        return "thick";
    }
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public IssueBO getIssueBO() {
        return issueBO;
    }

    public void setIssueBO(IssueBO issueBO) {
        this.issueBO = issueBO;
    }

    public String getOldFromUrl() {
        return oldFromUrl;
    }

    public void setOldFromUrl(String oldFromUrl) {
        this.oldFromUrl = oldFromUrl;
    }

    public Map<String, Object> getJsonRoot() {
        return jsonRoot;
    }

    public void setJsonRoot(Map<String, Object> jsonRoot) {
        this.jsonRoot = jsonRoot;
    }

}
