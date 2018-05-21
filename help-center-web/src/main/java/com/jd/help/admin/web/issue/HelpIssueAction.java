package com.jd.help.admin.web.issue;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jd.common.util.PaginatedList;
import com.jd.common.web.result.Result;
import com.jd.help.HelpBaseAction;
import com.jd.help.IssueTypeEnum;
import com.jd.help.admin.web.common.NoHrmPrivilegeException;
import com.jd.help.center.web.util.WebHelper;
import com.jd.help.dao.issue.search.IssueSearcher;
import com.jd.help.domain.*;
import com.jd.help.domain.knowledge.KnowledgeEsBean;
import com.jd.help.domain.knowledge.KnowledgeQueryDTO;
import com.jd.help.domain.knowledge.KnowledgeVO;
import com.jd.help.enums.KnowledgeContentTypesEnum;
import com.jd.help.es.IssueEsService;
import com.jd.help.es.domain.IssueEsQuery;
import com.jd.help.excel.XExcel;
import com.jd.help.excel.XExcelBuilder;
import com.jd.help.service.*;
import com.jd.mongodbclient.util.DateUtil;
import com.jd.ump.annotation.JProfiler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelpIssueAction extends HelpBaseAction {
    private static final Logger log = LoggerFactory.getLogger(HelpIssueAction.class);

    /**
     * 默认参数的list，不能更改结构：不可以使用add或remove
     */
    private static List<IssueTypeEnum> ISSUE_TYPE_LIST = Arrays.asList(
            IssueTypeEnum.COMMON_PROBLEM, IssueTypeEnum.OPERATION_GUIDE);

    @Resource
    private IssueService issueService;

    @Resource
    private PublicPortalService publicPortalService;

    private IssueBO issueBO;

    private Integer catLevel1;
    private Integer catLevel2;
    private Integer catLevel3;
    private Result result;

    private String referer;

    private String links;

    /**
     * 页面选择的知识标签
     */
    private String label;

    /**
     * 正在操作的知识id
     */
    private Long knowledgeId;

    /**
     * 知识替换页查询条件
     */
    private KnowledgeQueryDTO knowledgeQueryDTO;

    private Map jsonRoot = new HashMap();

    @Resource
    private IssueSuggestService issueSuggestService;
    @Resource
    private IssueEsService issueEsService;
    @Resource
    private BackdoorService backdoorService;
    

    @Resource
    private RelationKnowledgeService relationKnowledgeService;

    private OrderStatus[] orderStatuses = OrderStatus.values();
    private OrderType[] orderTypes = OrderType.values();
    private OrderShipment[] orderShipments = OrderShipment.values();
    private OrderPay[] orderPays = OrderPay.values();

  //是否验证权限
    private final static boolean IS_VALIDATE = HrmPurviewConstants.IS_VALIDATE;
    /**
     * 去增加问题页面
     *
     * @return
     */
    public String insert() throws NoHrmPrivilegeException{
    	initSite(IS_VALIDATE);
    	Result result = new Result();
        result.addDefaultModel("site", site);
        Catalog cat = new Catalog();
        cat.setId(catLevel3);
        result.addDefaultModel("catalog", cat);
        result.addDefaultModel("issueTypeList",ISSUE_TYPE_LIST);
        this.setResult(result);
        this.toVm(result);
        return SUCCESS;
    }

    /**
     * 执行增加操作
     *
     * @return
     */
    public String doInsert() throws NoHrmPrivilegeException{
        log.info(" HelpIssueAction method insert execute ");
    	site = new Site();
        site.setId(issueBO.getIssue().getSiteId());
        initSite(IS_VALIDATE);
    	issueBO.getIssue().setCataId(catLevel3);
//        issueBO.getIssue().setCataId(571);
        try {
            issueBO.getIssue().setCreator(URLEncoder.encode(WebHelper.getPin(),"GBK"));
            issueBO.getIssue().setModifier(URLEncoder.encode(WebHelper.getPin(),"GBK"));
        } catch (UnsupportedEncodingException e) {
            log.error("【HelpIssueAction.doInsert】pin解码失败:" + e.getMessage());
        }
        issueService.insert(issueBO);
        
        return "toOrderStatus";
    }

    /**
     * 去修改问题页面
     *
     * @return
     */
    public String update() throws NoHrmPrivilegeException{
        site = new Site();
        site.setId(issueBO.getIssue().getSiteId());
    	initSite(IS_VALIDATE);
    	this.setResult(issueService.queryIssueBOByIssueId2(issueBO, false));
        result.addDefaultModel("issueTypeList",ISSUE_TYPE_LIST);
        this.toVm(result);
        return SUCCESS;
    }

    /**
     * 执行修改操作
     *
     * @return
     */
    public String doUpdate() throws NoHrmPrivilegeException{
        log.info(" HelpIssueAction method update execute ");
    	site = new Site();
        site.setId(issueBO.getIssue().getSiteId());
        initSite(IS_VALIDATE);
        
    	issueBO.getIssue().setCataId(catLevel3);
        try {
            issueBO.getIssue().setModifier(URLEncoder.encode(WebHelper.getPin(),"GBK"));
        } catch (UnsupportedEncodingException e) {
            log.error("【HelpIssueAction.doUpdate】pin解码失败:" + e.getMessage());
        }
        this.setResult(issueService.update(issueBO));
        
        result.addDefaultModel("site", site);
        return "updateResult";
    }

    public String orderStatus() throws NoHrmPrivilegeException{
    	site = new Site();
        site.setId(issueBO.getIssue().getSiteId());
        initSite(IS_VALIDATE);
        this.setResult(issueService.detail(issueBO.getIssue()));
        toVm(result);
        return SUCCESS;
    }

    public String doOrderStatus() throws NoHrmPrivilegeException{
    	initSite(IS_VALIDATE);
        Issue issue = issueBO.getIssue();
        issue.setModifier(WebHelper.getPin());
        result = issueService.updateOrderStatus(issue);
        result.addDefaultModel("site", site);
        return "updateResult";
    }


    /**
     * 根据选择的知识标签进行关联知识推荐
     * 如果id == null || id == 0, 情况为新建知识->关联知识
     * 否则是修改知识->关联知识
     *
     * @return String
     */
    public String relationKnowledge() {
        log.info("method  relationKnowledge execute");
        jsonRoot.put("token", "fail");
        // 页面展示list
        List<KnowledgeVO> knowledgeList = Lists.newArrayList();
        // es匹配需要排除的知识id集合
        Set<Long> set = new HashSet<Long>();
        // 强关联知识id集合
        Set<Long> strongKnowledgeSet = new HashSet<Long>();
        // 修改
        if (knowledgeId != null && knowledgeId > 0L) {
            log.info("method relationKnowledge execute type: update");
            // 1. 先查强关联的知识和删除的知识
            List<RelationKnowledge> relationKnowledgeList = relationKnowledgeService.listStrongRelationKnowledge(knowledgeId);
            log.info("relationKnowledgeList: " + JSON.toJSONString(relationKnowledgeList));
            Map<Long, RelationKnowledge> map = Maps.newHashMap();
            // 需要让es知道需要排除哪些知识
            for (RelationKnowledge relationKnowledge : relationKnowledgeList) {
                set.add(relationKnowledge.getReplaceKnowledgeId());
                set.add(relationKnowledge.getRelationKnowledgeId());
                strongKnowledgeSet.add(relationKnowledge.getRelationKnowledgeId());
                if (relationKnowledge.getIsStrong() == 1) {
                    map.put(relationKnowledge.getRelationKnowledgeId(), relationKnowledge);
                }
            }


            // 2. 查缓存数据，缓存没有，直接认为是过去了一周，缓存数据过期，重新关联知识， 这在某些情况下会误判
            // 缓存如果有，则直接使用
            Set<String> cachedIds = relationKnowledgeService.queryInRedis(knowledgeId);
            log.info("cachedIds " + cachedIds);
            if (CollectionUtils.isEmpty(cachedIds)) {
                List<KnowledgeEsBean> esBeanList = publicPortalService.getRelationKnowledgeFromEs(set, label, knowledgeId);
                // es知识对象转换成KnowledgeVO，页面展示
                transFromEsBean2VO(map, esBeanList, knowledgeList);

            } else {
                Set<Long> idSet = new HashSet<Long>();
                for (String cachedId : cachedIds) {
                    idSet.add(NumberUtils.toLong(cachedId, 0L));
                }
                // es 根据ids搜索知识
                List<KnowledgeEsBean> esBeanList = publicPortalService.getKnowledgeFromEsInIds(idSet);
                // es知识对象转换成KnowledgeVO，页面展示
                transFromEsBean2VO(map,esBeanList, knowledgeList);
            }
        } else {
            log.info("method relationKnowledge execute  type: newCreate");
            // 新建知识，根据标签es搜索
            List<KnowledgeEsBean> esBeanList = publicPortalService.getRelationKnowledgeFromEs(set, label, knowledgeId);
            // es知识对象转换成KnowledgeVO，页面展示
            transFromEsBean2VO(null, esBeanList, knowledgeList);
        }
        jsonRoot.put("data", knowledgeList);
        jsonRoot.put("token", "success");
        return "jsonResult";
    }


    /**
     * 根据查询条件查询知识
     *
     * @return String
     */
    public String allKnowledge() {
        jsonRoot.put("token", "fail");
        Set<Long> set = new HashSet<Long>();
        // 新增知识的操作，知识还没有实际生成，不需要去查关联知识
        if (knowledgeId != null && knowledgeId > 0L) {
            // 强关联的知识和删除的
            List<RelationKnowledge> relationKnowledgeList = relationKnowledgeService.listStrongRelationKnowledge(knowledgeId);
            for (RelationKnowledge relationKnowledge : relationKnowledgeList) {
                // 被替换的
                set.add(relationKnowledge.getReplaceKnowledgeId());
                // 删除的
                set.add(relationKnowledge.getId());
            }
        }
        List<KnowledgeEsBean> esBeanList = publicPortalService.getAllKnowledgeList(knowledgeQueryDTO, set);
        List<KnowledgeVO> knowledgeList = Lists.newArrayList();
        transFromEsBean2VO(null, esBeanList, knowledgeList);
        jsonRoot.put("token", "success");
        jsonRoot.put("data", knowledgeList);
        return "jsonResult";
    }

    /**
     * 去问题管理列表页
     *
     * @return
     */
    public String list() throws NoHrmPrivilegeException{
        initSite(IS_VALIDATE);
        Result result = new Result(true);
//        SolrQuery solrQuery = bulidSolrQuery();
//        // 高亮
//        solrQuery.setHighlight(true);
//        solrQuery.addHighlightField("name");
//        solrQuery.setHighlightSimplePre("<font style=\"color:red\">");
//        solrQuery.setHighlightSimplePost("</font>");
        
        page = (page <= 0 ? 1 : page);
//        PaginatedList<Issue> issueList = issueSearcher.searchWithHighlighting(solrQuery, page, PAGE_SIZE);
        IssueQuery issueQuery = buildIssueQuery();
        PaginatedList<Issue> issueList = issueService.list(issueQuery, page, PAGE_SIZE);
        result.addDefaultModel("issueList", issueList);
        result.addDefaultModel("issueTypeList",ISSUE_TYPE_LIST);
        List<Integer> unSolveCount = new ArrayList<Integer>();
        List<Integer> solveCount = new ArrayList<Integer>();
        for (int i = 0; i < issueList.size(); i++) {
            Issue issue = issueList.get(i);
            //查询解决、未解决数量
            IssueSuggestQuery suggestQuery = new IssueSuggestQuery();
            suggestQuery.setIssueId(issue.getId());
            suggestQuery.setStatus(1);
            //都不为空，防止空指针
            if(null!=issueBO&&null!=issueBO.getIssueDateQuery()&&null!=issueBO.getIssueDateQuery().getSuggestBeginTime()&&null!=issueBO.getIssueDateQuery().getSuggestEndTime()){
                suggestQuery.setBeginTime(DateUtil.format(issueBO.getIssueDateQuery().getSuggestBeginTime(),"yyyy-MM-dd 00:00:00"));
                suggestQuery.setEndTime(DateUtil.format(issueBO.getIssueDateQuery().getSuggestEndTime(),"yyyy-MM-dd 23:59:59"));
            }
            suggestQuery.setSolveStatus(1);
            solveCount.add(issueSuggestService.queryIssueSolveCount(suggestQuery));
            suggestQuery.setSolveStatus(0);
            //将对应的未解决与解决数量存入集合
            unSolveCount.add(issueSuggestService.queryIssueSolveCount(suggestQuery));
        }
        result.addDefaultModel("unSolveCount",unSolveCount);
        result.addDefaultModel("solveCount",solveCount);
        // 用于页面计算知识到期时间
        result.addDefaultModel("currentTime", System.currentTimeMillis());
        toVm(result);
        return SUCCESS;
    }

    /**
     * 构建查询条件
     * @return
     */
    public IssueQuery buildIssueQuery(){
        IssueQuery issueQuery = new IssueQuery();
        issueQuery.setSiteId(site.getId());
        issueQuery.setStatus(1);//查询在线文章
        if(catLevel3!= null){
            issueQuery.setCataId(catLevel3);
        }
        // 关键字和状态
        if (issueBO != null && issueBO.getIssue() != null) {
            if (StringUtils.isNotBlank(issueBO.getIssue().getName())) {
                issueQuery.setName(issueBO.getIssue().getName().trim());
            }

            if (issueBO.getIssue().getStatus() != null && issueBO.getIssue().getStatus() != -100) {
                issueQuery.setStatus(issueBO.getIssue().getStatus());
            }

            //文章修改时间段
            if(issueBO.getIssueDateQuery() != null && issueBO.getIssueDateQuery().getIssueBeginTime() != null){
                if(null == issueBO.getIssueDateQuery().getIssueEndTime()){
                    issueBO.getIssueDateQuery().setIssueEndTime(new Date());
                }
                String beginDate = DateUtil.format(issueBO.getIssueDateQuery().getIssueBeginTime(),"yyyy-MM-dd 00:00:00");
                String endDate = DateUtil.format(issueBO.getIssueDateQuery().getIssueEndTime(), "yyyy-MM-dd 23:59:59");
                issueQuery.setCreatedBeginTime(beginDate);
                issueQuery.setCreateEndTime(endDate);
            }
            // 业务对接人
            if (StringUtils.isNotBlank(issueBO.getIssue().getIssueSolvePin())) {
                issueQuery.setIssueSolvePin(issueBO.getIssue().getIssueSolvePin().trim());
            }
            // 知识类型
            if (issueBO.getIssue().getIssueType() != null && issueBO.getIssue().getIssueType() >= 0) {
                issueQuery.setIssueType(issueBO.getIssue().getIssueType());
            }
            // 作者
            if (StringUtils.isNotBlank(issueBO.getIssue().getCreator())) {
                issueQuery.setCreator(issueBO.getIssue().getCreator().trim());
            }
            // 知识id
            if (issueBO.getIssue().getId() != null && issueBO.getIssue().getId() > 0) {
                issueQuery.setId(issueBO.getIssue().getId());
            }
        }
        return issueQuery;
    }

    /**
     * 导出excel
     * @return
     */
    @JProfiler(jKey = "vender.help.admin.issue.HelpIssueAction.downLoad", jAppName = "vender_help_center")
    public String downLoad() throws NoHrmPrivilegeException {
        initSite(IS_VALIDATE);
        IssueQuery issueQuery = buildIssueQuery();
        int maxCount = 10000; //最大导出量为10000条数据
        int pageSize = 100;
        int pageIndex = 1;
        int listSize = 0;
        try {
            XExcel xExcel = XExcelBuilder.instance(IssueExcelBO.class);
            xExcel.createHeard(createExcelHeader());
            do {

                PaginatedList<Issue> paginatedList = issueService.list(issueQuery, pageIndex, pageSize);
                if(CollectionUtils.isNotEmpty(paginatedList)){
                    listSize += paginatedList.size();

                    IssueSuggestQuery suggestQuery = new IssueSuggestQuery();
                    IssueExcelBO issueExcelBO = new IssueExcelBO();
                    for(Issue issue : paginatedList){
                        issueExcelBO.setId(issue.getId());
                        issueExcelBO.setName(issue.getName());
                        issueExcelBO.setUrl("http://helpcenter.jd.com/vender/issue/"+ issue.getCataId() + "-" + issue.getId() +".html");
                        suggestQuery.setIssueId(issue.getId());
                        suggestQuery.setStatus(1);
                        if(issueBO.getIssueDateQuery().getSuggestBeginTime() != null){
                            suggestQuery.setBeginTime(DateUtil.format(issueBO.getIssueDateQuery().getSuggestBeginTime(), "yyyy-MM-dd 00:00:00"));
                            if(issueBO.getIssueDateQuery().getSuggestEndTime() == null){
                                issueBO.getIssueDateQuery().setSuggestEndTime(new Date());
                            }
                        }
                        if(issueBO.getIssueDateQuery().getSuggestEndTime() != null){
                            suggestQuery.setEndTime(DateUtil.format(issueBO.getIssueDateQuery().getSuggestEndTime(), "yyyy-MM-dd 23:59:59"));
                        }
                        suggestQuery.setSolveStatus(1);
                        issueExcelBO.setSolveCount(issueSuggestService.queryIssueSolveCount(suggestQuery));
                        suggestQuery.setSolveStatus(0);
                        issueExcelBO.setUnSolveCount(issueSuggestService.queryIssueSolveCount(suggestQuery));

                        xExcel.add(issueExcelBO);
                    }
                    pageIndex++;
                } else {
                    break;
                }
            }while(listSize < maxCount);

            StringBuffer fileNameStr = new StringBuffer();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fileNameStr.append("文章已解决未解决数量统计").append(sdf.format(new Date()));
            String fileName = new String(fileNameStr.toString().getBytes(),"ISO8859-1");

            HttpServletResponse response = ServletActionContext.getResponse();
            OutputStream res = response.getOutputStream();

            //清空输出流
            response.reset();
            //设定输出文件头
            response.setHeader("Content-Disposition","attachment; filename=" + fileName + ".xls");
            response.setContentType("application/vnd.ms-excel");
            xExcel.write(res);
            res.flush();
            res.close();
        } catch (Exception e) {
            log.error("文件导出异常", e);
        }
        return null;
    }

    /**
     * 导出excel
     * @return
     */
    @JProfiler(jKey = "vender.help.admin.issue.HelpIssueAction.downLoad2", jAppName = "vender_help_center")
    public String downLoad2() throws NoHrmPrivilegeException {
        initSite(IS_VALIDATE);
        IssueQuery issueQuery = buildIssueQuery();
        int maxCount = 10000; //最大导出量为10000条数据
        int pageSize = 100;
        int pageIndex = 1;
        int listSize = 0;
        try {
            XExcel xExcel = XExcelBuilder.instance(IssueSuggestExcelBo.class);
            xExcel.createHeard(createExcelHeader2());
            do {

                PaginatedList<Issue> paginatedList = issueService.list(issueQuery, pageIndex, pageSize);
                if(CollectionUtils.isNotEmpty(paginatedList)){
                    listSize += paginatedList.size();

                    IssueSuggestQuery suggestQuery;

                    for(Issue issue : paginatedList){
                        suggestQuery  = new IssueSuggestQuery();
                        suggestQuery.setIssueId(issue.getId());
                        if(issueBO.getIssueDateQuery().getSuggestBeginTime() != null){
                            suggestQuery.setBeginTime(DateUtil.format(issueBO.getIssueDateQuery().getSuggestBeginTime(), "yyyy-MM-dd 00:00:00"));
                            if(issueBO.getIssueDateQuery().getSuggestEndTime() == null){
                                issueBO.getIssueDateQuery().setSuggestEndTime(new Date());
                            }
                        }
                        if(issueBO.getIssueDateQuery().getSuggestEndTime() != null){
                            suggestQuery.setEndTime(DateUtil.format(issueBO.getIssueDateQuery().getSuggestEndTime(), "yyyy-MM-dd 23:59:59"));
                        }
                        List<IssueSuggest> suggestList = issueSuggestService.listForUnSolve(suggestQuery,1, pageSize);
                        IssueSuggestExcelBo issueSuggestExcelBo;
                        for(IssueSuggest suggest : suggestList){
                            if(suggest == null) {
                                continue;
                            }
                            issueSuggestExcelBo = new IssueSuggestExcelBo();
                            issueSuggestExcelBo.setId(issue.getId());
                            issueSuggestExcelBo.setName(issue.getName());
                            issueSuggestExcelBo.setUnSolveReason(suggest.getUnSolveReason());
                            issueSuggestExcelBo.setSuggestContent(suggest.getSuggestContent());
                            String creator = suggest.getCreator();
                            try{
                                creator = URLDecoder.decode(suggest.getCreator(), "gbk");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            issueSuggestExcelBo.setCreator(creator);
                            issueSuggestExcelBo.setCreateTime(DateUtil.format(suggest.getCreated(), "yyyy-MM-dd hh:mm:ss"));
                            xExcel.add(issueSuggestExcelBo);
                        }
                        issueQuery = null;
                    }
                    pageIndex++;
                } else {
                    break;
                }
            }while(listSize < maxCount);

            StringBuffer fileNameStr = new StringBuffer();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fileNameStr.append("文章未解决原因").append(sdf.format(new Date()));
            String fileName = new String(fileNameStr.toString().getBytes(),"ISO8859-1");

            HttpServletResponse response = ServletActionContext.getResponse();
            OutputStream res = response.getOutputStream();

            //清空输出流
            response.reset();
            //设定输出文件头
            response.setHeader("Content-Disposition","attachment; filename=" + fileName + ".xls");
            response.setContentType("application/vnd.ms-excel");
            xExcel.write(res);
            res.flush();
            res.close();
        } catch (Exception e) {
            log.error("文件导出异常", e);
        }
        return null;
    }

    private List<String> createExcelHeader(){
        List<String> headerList = new ArrayList<String>();
        headerList.add("ID");
        headerList.add("标题");
        headerList.add("链接");
        headerList.add("未解决数量");
        headerList.add("已解决数量");
        return headerList;
    }

    private List<String> createExcelHeader2(){
        List<String> headerList = new ArrayList<String>();
        headerList.add("文章ID");
        headerList.add("文章标题");
        headerList.add("未解决原因");
        headerList.add("建议");
        headerList.add("创建者");
        headerList.add("创建时间");
        return headerList;
    }


    private SolrQuery bulidSolrQuery(){
        StringBuilder queryString = new StringBuilder();
        // 站点
        queryString.append("siteId:").append(site.getId()).append(IssueSearcher.AND);
        // 类目
        Integer c = (catLevel3 != null && catLevel3 > 0) ? catLevel3 : (catLevel2 != null && catLevel2 > 0) ? catLevel2 : (catLevel1 != null && catLevel1 > 0) ? catLevel1 : null;
        if (c != null && c > 0) {
            queryString.append("cataId:").append(String.valueOf(c)).append(IssueSearcher.AND);
        }

        //是否有关键字搜索
        boolean isKeySearch = false;

        // 关键字和状态
        if (issueBO != null && issueBO.getIssue() != null) {
            if (StringUtils.isNotBlank(issueBO.getIssue().getName())) {
                String key = ClientUtils.escapeQueryChars(issueBO.getIssue().getName());
                if (StringUtils.isNotBlank(key)) {
                    isKeySearch = true;
                    queryString.append("(");
                    queryString.append("name:").append(key).append("^10");
                    queryString.append(")").append(IssueSearcher.AND);
                }
            }


            if (issueBO.getIssue().getStatus() != -100) {
                queryString.append("status:").append(issueBO.getIssue().getStatus()).append(IssueSearcher.AND);
            } else {
                queryString.append("status:(0 1)").append(IssueSearcher.AND);
            }

            //订单维度匹配
            StringBuffer orderQueryString = new StringBuffer();
            orderQueryString.append("(");
            if(issueBO.getIssue().getOrderType() != null && !"".equals(issueBO.getIssue().getOrderType())
                    && !"-100".equals(issueBO.getIssue().getOrderType())){
                orderQueryString.append("orderType:(").append(issueBO.getIssue().getOrderType())
                        .append(")").append(IssueSearcher.AND);
            }
            if(issueBO.getIssue().getOrderStatus() != null && !"".equals(issueBO.getIssue().getOrderStatus())
                    && !"-100".equals(issueBO.getIssue().getOrderStatus())){
                orderQueryString.append("orderStatus:(").append(issueBO.getIssue().getOrderStatus())
                        .append(")").append(IssueSearcher.AND);
            }
            if(issueBO.getIssue().getOrderShipment() != null && !"".equals(issueBO.getIssue().getOrderShipment())
                    && !"-100".equals(issueBO.getIssue().getOrderShipment())){
                orderQueryString.append("orderShipment:(").append(issueBO.getIssue().getOrderShipment())
                        .append(")").append(IssueSearcher.AND);
            }

            if(issueBO.getIssue().getOrderPay() != null && !"".equals(issueBO.getIssue().getOrderPay())
                    && !"-100".equals(issueBO.getIssue().getOrderPay())){
                orderQueryString.append("orderPay:(").append(issueBO.getIssue().getOrderPay())
                        .append(")").append(IssueSearcher.AND);
            }
            if(orderQueryString.length() > 1){
                orderQueryString.delete(orderQueryString.length() - IssueSearcher.OR.length(), orderQueryString.length() -1);
                orderQueryString.append(")").append(IssueSearcher.AND);
                queryString.append(orderQueryString);
            }

            //文章修改时间段
            if(issueBO.getIssueDateQuery() != null && issueBO.getIssueDateQuery().getIssueBeginTime() != null){
                if(null == issueBO.getIssueDateQuery().getIssueEndTime()){
                    issueBO.getIssueDateQuery().setIssueEndTime(new Date());
                }
                String beginDate = DateUtil.format(issueBO.getIssueDateQuery().getIssueBeginTime(),"yyyy-MM-dd'T'00:00:00'Z'");
                String endDate = DateUtil.format(issueBO.getIssueDateQuery().getIssueEndTime(), "yyyy-MM-dd'T'23:59:59'Z'");
                queryString.append("modified:[").append(beginDate).append(" TO ").append(endDate).append("]").append(IssueSearcher.AND);
            }
        } else {
            queryString.append("status:(0 1)").append(IssueSearcher.AND);
        }

        if (queryString.substring(queryString.length() - IssueSearcher.AND.length()).equals(IssueSearcher.AND)) {
            queryString.delete(queryString.length() - IssueSearcher.AND.length(), queryString.length() - 1);
        }
        log.info("solr queryString is---" + queryString.toString().trim());
        SolrQuery solrQuery = new SolrQuery(queryString.toString().trim());
        if(isKeySearch){
            solrQuery.addSort("score", SolrQuery.ORDER.desc);
        }

        solrQuery.addSort("modified", SolrQuery.ORDER.desc);
        return solrQuery;
    }


    public String updateStatus() throws NoHrmPrivilegeException{
    	initSite(IS_VALIDATE);
    	Issue issue = issueBO.getIssue();
        if (issue != null) {
            issue.setModifier(WebHelper.getPin());
            result = issueService.updateStatus(issue);
        }
        referer = request.getHeader("referer");

        return SUCCESS;
    }
    
    
    /**
     * 获取相关问题链接
     *
     * @return
     */
    public String complteRel() throws NoHrmPrivilegeException {
        site = new Site();
        site.setId(issueBO.getIssue().getSiteId());
        initSite(IS_VALIDATE);
        IssueEsQuery query = new IssueEsQuery();
        // 类目
        Integer c = (catLevel3 != null && catLevel3 > 0) ? catLevel3 : (catLevel2 != null && catLevel2 > 0) ? catLevel2 : (catLevel1 != null && catLevel1 > 0) ? catLevel1 : null;
        if (c != null && c > 0) {
            query.setCategoryId(new Long(c));
        }

        // 排除重复的文章
        List<String> exclusions = new ArrayList<String>();
        if (StringUtils.isNotBlank(links)) {
            String[] linkArray = links.split(",");
            if (linkArray.length > 0) {
                for (String link : linkArray) {
                    String issueIdFromLink = issueIdFromLink(link);
                    if (StringUtils.isNotBlank(issueIdFromLink)) {
                        exclusions.add(issueIdFromLink);
                    }
                }
            }
        }
        if (issueBO != null && issueBO.getIssue() != null && issueBO.getIssue().getId() != null && issueBO.getIssue().getId() > 0) {
            exclusions.add(String.valueOf(issueBO.getIssue().getId()));
        }
        query.setExtra(exclusions);
        List<Issue> issueList = issueEsService.getRel(query);
        jsonRoot.put("issueList", issueList);
        jsonRoot.put("site", site);
        return "jsonResult";
    }

    private String issueIdFromLink(String link) {
        String issueId = "";
        if (StringUtils.isNotBlank(link)) {
            Pattern p = Pattern.compile("//.*/issue/\\d+-(\\d+)\\.html");
            Matcher matcher = p.matcher(link);
            if (matcher.find()) {
                issueId = matcher.group(1);
            }
        }
        return issueId;
    }

    //进入后面链接
    public String backdoor(){
        return "backdoor";
    }

    public String mysql2Es()  {
       //mysql
        try {
            initSite(IS_VALIDATE);
            backdoorService.mysql2Es();
        } catch (Exception e) {
            log.error("mysql数据导入Es失败："+e.getMessage());
            throw new RuntimeException("mysql数据导入Es失败");
        }
        return "backdoor";
    }

    /**
     * esBeanList转换成voList
     *
     * @param map        强关联知识集合
     * @param esBeanList es bean 集合
     * @param voList     vo层数据集合
     */
    private void transFromEsBean2VO(Map<Long, RelationKnowledge> map, List<KnowledgeEsBean> esBeanList, List<KnowledgeVO> voList) {
        if (CollectionUtils.isEmpty(esBeanList)) {
            log.info("method transFromEsBean2VO return, esBeanList is empty");
            return;
        }
        KnowledgeVO vo = null;
        RelationKnowledge knowledge = null;
        for (KnowledgeEsBean bean : esBeanList) {
            vo = new KnowledgeVO();
            vo.setId(bean.getKnowledgeId());
            vo.setContentType(bean.getContentType());
            vo.setName(bean.getName());
            vo.setCreateTime(new Date(bean.getCreateTime()));
            formLabelNameShow(bean, vo);
            KnowledgeContentTypesEnum typesEnum = KnowledgeContentTypesEnum.fromCode(bean.getContentType());
            vo.setContentTypeStr((typesEnum == null) ? " " : typesEnum.getDesc());
            vo.setStatus(0);
            vo.setIsStrong(0);
            vo.setReplaceKnowledgeId(0L);
            vo.setIsStrongStr("系统匹配");
            if (map != null) {
                knowledge = map.get(bean.getKnowledgeId());
                if (knowledge != null) {
                    vo.setIsStrong(1);
                    vo.setIsStrongStr("人工匹配");
                    vo.setReplaceKnowledgeId(knowledge.getReplaceKnowledgeId());
                }
            }
            voList.add(vo);
        }
    }

    /**
     * 标签展示格式 一级标签 -> 二级标签
     * <p>
     * 对标签数据错误的情况做了以下处理
     * 二级标签名称为空，直接显示""
     * 仅有二级标签名称，直接显示全部二级标签名字
     * 一级，二级标签名称都有数据，但个数不一样，取个数最少的进行处理，其余数据丢弃不处理
     * </p>
     *
     * @param bean es的数据结构
     * @param vo   页面展示的数据结构
     */
    private void formLabelNameShow(KnowledgeEsBean bean, KnowledgeVO vo) {
        if (StringUtils.isEmpty(bean.getLabel2Names())) {
            // 数据没有被打标签
            vo.setLabel2Names(" ");
        } else {
            if (StringUtils.isEmpty(bean.getLabel1Names())) {
                // 数据有错误，仅有二级标签数据
                vo.setLabel2Names(bean.getLabel2Names());
            } else {
                String[] label_1 = bean.getLabel1Names().split(" ");
                String[] label_2 = bean.getLabel2Names().split(" ");
                // 避免数据越界，取数组最小长度作为遍历界限，多余数据不再处理
                int length = label_1.length > label_2.length ? label_2.length : label_1.length;
                // 其实不需要显示使用StringBuilder， jvm会进行代码优化的
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    stringBuilder.append(label_1[i]).append("->").append(label_2[i]).append(" ");
                }
                vo.setLabel2Names(stringBuilder.toString());
            }
        }
    }

//
    /**
     //     * 去公共知识管理页
     //     * 定义说明：
     //     * 强关联知识 - 运营人员手动替换的，不会变化，在mysql中存储。
     //     * 弱关联知识 - 系统根据标签筛选出pv排名靠前的，存在redis中，数据会过期。
     //     *
     //     * @return String
     //     * @throws NoHrmPrivilegeException
     //     */
//    public String relationList() throws NoHrmPrivilegeException {
//        initSite(IS_VALIDATE);
//        Result result = new Result(true);
//        // 知识id存在，修改知识，需要查询强关联知识
//        if (issueBO != null && issueBO.getIssue() != null && issueBO.getIssue().getId() > 0) {
//
//        }
//        List<Knowledge> recommendKnowledge = knowledgeService.listRecommendedKnowledge(labelId);
//
//        result.addDefaultModel("knowledge", recommendKnowledge);
//        return SUCCESS;
//    }
    public IssueBO getIssueBO() {
        return issueBO;
    }

    public void setIssueBO(IssueBO issueBO) {
        this.issueBO = issueBO;
    }

    public Integer getCatLevel1() {
        return catLevel1;
    }

    public void setCatLevel1(Integer catLevel1) {
        this.catLevel1 = catLevel1;
    }

    public Integer getCatLevel2() {
        return catLevel2;
    }

    public void setCatLevel2(Integer catLevel2) {
        this.catLevel2 = catLevel2;
    }

    public Integer getCatLevel3() {
        return catLevel3;
    }

    public void setCatLevel3(Integer catLevel3) {
        this.catLevel3 = catLevel3;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public OrderStatus[] getOrderStatuses() {
        return orderStatuses;
    }

    public OrderType[] getOrderTypes() {
        return orderTypes;
    }

    public OrderShipment[] getOrderShipments() {
        return orderShipments;
    }

    public OrderPay[] getOrderPays() {
        return orderPays;
    }

    public Map getJsonRoot() {
        return jsonRoot;
    }

    public void setJsonRoot(Map jsonRoot) {
        this.jsonRoot = jsonRoot;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public KnowledgeQueryDTO getKnowledgeQueryDTO() {
        return knowledgeQueryDTO;
    }

    public void setKnowledgeQueryDTO(KnowledgeQueryDTO knowledgeQueryDTO) {
        this.knowledgeQueryDTO = knowledgeQueryDTO;
    }
}
