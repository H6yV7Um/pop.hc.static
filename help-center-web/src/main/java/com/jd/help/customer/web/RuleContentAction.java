package com.jd.help.customer.web;

import com.jd.common.web.result.Result;
import com.jd.help.HelpBaseAction;
import com.jd.help.SysLoginContext;
import com.jd.help.domain.Notice;
import com.jd.help.service.KarmaService;
import com.jd.karma.facade.domain.model.CategoryFacade;
import com.jd.karma.facade.domain.model.NoticeFacade;
import com.jd.karma.facade.domain.model.SceneFacade;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Parser;
import org.htmlparser.visitors.TextExtractingVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhangmanliang on 2016/12/20.
 */
public class RuleContentAction extends HelpBaseAction {

    private static Logger logger= LoggerFactory.getLogger(RuleContentAction.class);

    public Long ruleId;
    public NoticeFacade entity;
    @Resource
    private KarmaService karmaService;
    private Map<String, Object> jsonRoot = new HashMap<String, Object>();
    private List<CategoryFacade> categoryList;
    private List<SceneFacade> hotScenes;
    private List<SceneFacade> specialScenes;
    private List<NoticeFacade> notices;
    private String title = null;
    private Long id;
    private SceneFacade sceneDetail;
    private String currentCategory;
    private List<SceneFacade> listScenes;
    private String outerCategory;
    private String publishTime;

    private String loginUrl="https://passport.jd.com/new/login.aspx?ReturnUrl=";
    private String redirectUrl;
    private String domainName;

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getOuterCategory() {
        return outerCategory;
    }

    public String getCurrentCategory() {
        return currentCategory;
    }

    public SceneFacade getSceneDetail() {
        return sceneDetail;
    }

    public Map<String, Object> getJsonRoot() {
        return jsonRoot;
    }

    public List<SceneFacade> getHotScenes() {
        return hotScenes;
    }

    public List<CategoryFacade> getCategoryList() {
        return categoryList;
    }

    public List<SceneFacade> getSpecialScenes() {
        return specialScenes;
    }

    public List<NoticeFacade> getNotices() {
        return notices;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return StringEscapeUtils.escapeXml(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SceneFacade> getListScenes() {
        return listScenes;
    }

    /**
     * 规则中心前段展示首页
     * pop商家
     *
     * @return String
     */
    public String index() {

        try {
            //热门规则
            hotScenes = karmaService.listHotScenes();

            // 规则分类
            // categoryList = karmaService.getAllTopCategory();
            listCategories();
            // 规则专题
            specialScenes = karmaService.specialScenes();

            if(specialScenes!=null && specialScenes.size()>2){
                for(int i=0;i<3;i++){
                    SceneFacade facade=specialScenes.get(i);
                    facade.setSceneContent(html2Str(facade.getSceneContent()));
                }
            }
            // 公告
            notices = karmaService.getAllNotice();
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }

        return SUCCESS;
    }

    /**
     * 规则中心前段展示首页
     * 自营商家
     *
     * @return String
     */
    public String indexNew() {
//        try {
//            String check = check("indexNew");
//            if (!"OK".equalsIgnoreCase(check)) {
//                return check;
//            }
//            //热门规则
//            hotScenes = karmaService.listHotScenesByCategoryType((short) 1);
//            // 规则分类
//            listCategoriesNew();
//            // 规则专题
//            specialScenes = karmaService.specialScenes();
//            if (specialScenes != null && specialScenes.size() > 2) {
//                for (int i = 0; i < 3; i++) {
//                    SceneFacade facade = specialScenes.get(i);
//                    facade.setSceneContent(html2Str(facade.getSceneContent()));
//                }
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//        return SUCCESS;

        // 奇葩：产品要求自营首页直接是列表页
        try {
            String check = check("indexNew");
            if (!"OK".equalsIgnoreCase(check)) {
                return check;
            }
            listCategoriesNew();
            if(title == null &&id == null){
                List<Map> firstLevel = jsonRoot.get("data") == null ? null : (List<Map>) jsonRoot.get("data");
                if (firstLevel != null && firstLevel.size() > 0) {
                    outerCategory= firstLevel.get(0).get("id").toString();
                    List<Map> secondLevel = firstLevel.get(0).get("categories") == null ? null : (List<Map>) firstLevel.get(0).get("categories");

                    List<CategoryFacade> facadeList = karmaService.getSubcategory((Long) firstLevel.get(0).get("id"));
                    if (facadeList != null && facadeList.size() > 0) {
                        listScenes = facadeList.get(0).getSceneList();
                        currentCategory = facadeList.get(0).getCategoryName();
                    }
                }
                return SUCCESS;
            }

            if((title ==null &&id!=null )|| (title != null && id !=null)){
                outer:
                for (Map first : (List<Map>) jsonRoot.get("data")) {
                    if (first.get("categories") == null) {
                        continue;
                    }
                    List<Map> list = (List<Map>) first.get("categories");
                    for (int i = 0; i < list.size(); i++) {
                        if (Long.parseLong(list.get(i).get("id").toString()) == id.longValue()) {
                            currentCategory = (String) list.get(i).get("name");
                            listScenes = karmaService.getSubcategory((Long) first.get("id")).get(i).getSceneList();
                            outerCategory = first.get("id").toString();
                            break outer;
                        }
                    }
                }
                return SUCCESS;
            }

            if (title != null && id ==null) {//查询
                //title="规则";
                listScenes = karmaService.searchSceneNew(title);
                return SUCCESS;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }

        return SUCCESS;

    }

    /**
     * POP商家规则平台查看规则分类
     *
     * @return String
     */
    public String listCategories() {

        List<CategoryFacade> facades = karmaService.getAllTopCategory();

        List<Map> categoryList = new ArrayList<Map>();
        jsonRoot.put("data", categoryList);
        for (CategoryFacade categoryFacade : facades) {
            if ("\u89C4\u5219\u4E13\u9898".equals(categoryFacade.getCategoryName())) {//规则专题
                continue;
            } else {
                Map<String, Object> firstLevelCategory = new HashMap<String, Object>();
                firstLevelCategory.put("name", categoryFacade.getCategoryName());
                firstLevelCategory.put("id", categoryFacade.getId());
                List<CategoryFacade> subcategories = karmaService.getSubcategory(categoryFacade.getId());
                List<Map> subCategoryList = new ArrayList<Map>();
                firstLevelCategory.put("categories", subCategoryList);
                for (CategoryFacade subcategory : subcategories) {
                    Map<String, Object> secondLevelCategory = new HashMap<String, Object>();
                    secondLevelCategory.put("name", subcategory.getCategoryName());
                    secondLevelCategory.put("id", subcategory.getId());
                    subCategoryList.add(secondLevelCategory);
                }
                categoryList.add(firstLevelCategory);
            }
        }
        jsonRoot.put("isSuccess", true);
        return "json";
    }

    /**
     * 自营商家规则平台查看规则分类
     *
     * @return String
     */
    public String listCategoriesNew() {
        String check = check("listCategoriesNew");
        if (!"OK".equalsIgnoreCase(check)) {
            return check;
        }
        List<CategoryFacade> facades = karmaService.getAllTopCategoryByCategoryType((short) 1);
        List<Map> categoryList = new ArrayList<Map>();
        jsonRoot.put("data", categoryList);
        Map<String, Object> firstLevelCategory = null;
        for (CategoryFacade categoryFacade : facades) {
            if ("\u89C4\u5219\u4E13\u9898".equals(categoryFacade.getCategoryName())) {//规则专题
                continue;
            } else {
                firstLevelCategory = new HashMap<String, Object>();
                firstLevelCategory.put("name", categoryFacade.getCategoryName());
                firstLevelCategory.put("id", categoryFacade.getId());
                List<CategoryFacade> subcategories = karmaService.getSubcategory(categoryFacade.getId());
                List<Map> subCategoryList = new ArrayList<Map>();
                firstLevelCategory.put("categories", subCategoryList);
                for (CategoryFacade subcategory : subcategories) {
                    Map<String, Object> secondLevelCategory = new HashMap<String, Object>();
                    secondLevelCategory.put("name", subcategory.getCategoryName());
                    secondLevelCategory.put("id", subcategory.getId());
                    subCategoryList.add(secondLevelCategory);
                }
                categoryList.add(firstLevelCategory);
            }
        }
        jsonRoot.put("isSuccess", true);
        return "json";
    }

    /**
     * pop商家查看规则列表页
     *
     * @return String
     */
    public String list() {
        try {
            listCategories();
            if(title == null &&id == null){
                List<Map> firstLevel = jsonRoot.get("data") == null ? null : (List<Map>) jsonRoot.get("data");
                if (firstLevel != null && firstLevel.size() > 0) {
                    outerCategory= firstLevel.get(0).get("id").toString();
                    List<Map> secondLevel = firstLevel.get(0).get("categories") == null ? null : (List<Map>) firstLevel.get(0).get("categories");

                    List<CategoryFacade> facadeList = karmaService.getSubcategory((Long) firstLevel.get(0).get("id"));
                    if (facadeList != null && facadeList.size() > 0) {
                        listScenes = facadeList.get(0).getSceneList();
                        currentCategory = facadeList.get(0).getCategoryName();
                    }
                }
                return SUCCESS;
            }

            if((title ==null &&id!=null )|| (title != null && id !=null)){
                outer:
                for (Map first : (List<Map>) jsonRoot.get("data")) {
                    if (first.get("categories") == null) {
                        continue;
                    }
                    List<Map> list = (List<Map>) first.get("categories");
                    for (int i = 0; i < list.size(); i++) {
                        if (Long.parseLong(list.get(i).get("id").toString()) == id.longValue()) {
                            currentCategory = (String) list.get(i).get("name");
                            listScenes = karmaService.getSubcategory((Long) first.get("id")).get(i).getSceneList();
                            outerCategory = first.get("id").toString();
                            break outer;
                        }
                    }
                }
                return SUCCESS;
            }

            if (title != null && id ==null) {//查询
                //title="规则";
                listScenes = karmaService.searchSceneNew(title);
                return SUCCESS;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }

        return SUCCESS;
    }

//    /**
//     * 自营商家查看规则列表页
//     *
//     * @return String
//     */
//    public String listNew() {
//        try {
//            String check = check("listNew");
//            if (!"OK".equalsIgnoreCase(check)) {
//                return check;
//            }
//            listCategoriesNew();
//            if(title == null &&id == null){
//                List<Map> firstLevel = jsonRoot.get("data") == null ? null : (List<Map>) jsonRoot.get("data");
//                if (firstLevel != null && firstLevel.size() > 0) {
//                    outerCategory= firstLevel.get(0).get("id").toString();
//                    List<Map> secondLevel = firstLevel.get(0).get("categories") == null ? null : (List<Map>) firstLevel.get(0).get("categories");
//
//                    List<CategoryFacade> facadeList = karmaService.getSubcategory((Long) firstLevel.get(0).get("id"));
//                    if (facadeList != null && facadeList.size() > 0) {
//                        listScenes = facadeList.get(0).getSceneList();
//                        currentCategory = facadeList.get(0).getCategoryName();
//                    }
//                }
//                return SUCCESS;
//            }
//
//            if((title ==null &&id!=null )|| (title != null && id !=null)){
//                outer:
//                for (Map first : (List<Map>) jsonRoot.get("data")) {
//                    if (first.get("categories") == null) {
//                        continue;
//                    }
//                    List<Map> list = (List<Map>) first.get("categories");
//                    for (int i = 0; i < list.size(); i++) {
//                        if (Long.parseLong(list.get(i).get("id").toString()) == id.longValue()) {
//                            currentCategory = (String) list.get(i).get("name");
//                            listScenes = karmaService.getSubcategory((Long) first.get("id")).get(i).getSceneList();
//                            outerCategory = first.get("id").toString();
//                            break outer;
//                        }
//                    }
//                }
//                return SUCCESS;
//            }
//
//            if (title != null && id ==null) {//查询
//                //title="规则";
//                listScenes = karmaService.searchSceneNew(title);
//                return SUCCESS;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            logger.error(e.getMessage(),e);
//        }
//
//        return SUCCESS;
//    }

    /**
     * pop商家查看详情
     *
     * @return String
     */
    public String ruleDetail() {
        if (ruleId != null) {
            this.sceneDetail = karmaService.findById(ruleId);
            if(sceneDetail!=null){
                publishTime=new SimpleDateFormat("yyyyMMdd").format(sceneDetail.getUpdateTime()==null?new Date():sceneDetail.getUpdateTime());
            }
        }
        return SUCCESS;
    }

    /**
     * 自营商家查看详情
     *
     * @return String
     */
    public String ruleDetailNew() {
        String check = check("ruleDetailNew");
        if (!"OK".equalsIgnoreCase(check)) {
            return check;
        }
        if (ruleId != null) {
            this.sceneDetail = karmaService.findById(ruleId);
            if (sceneDetail != null) {
                publishTime = new SimpleDateFormat("yyyyMMdd").format(sceneDetail.getUpdateTime() == null ? new Date() : sceneDetail.getUpdateTime());
            }
        }
        return SUCCESS;
    }

    /**
     * 公告详情
     * @return
     */
    public String noticeDetail() {
        notices = karmaService.getAllNotice();
        if (entity != null && entity.getId() != null) {
            this.entity = karmaService.findNoticeById(entity.getId());
        } else if (notices!=null && notices.size()>0){
            this.entity = karmaService.findNoticeById(notices.get(0).getId());
        }
        return SUCCESS;
    }
    /**
     *  每月新编
     */
    public String monthNewly(){
        listScenes=karmaService.perMonthRule();

        if(id==null && listScenes !=null){
          sceneDetail=  listScenes.size()>0?karmaService.findById(listScenes.get(0).getId()) :null;
        }else {
            sceneDetail=karmaService.findById(id);
        }

        return SUCCESS;
    }

    public NoticeFacade getEntity() {
        return entity;
    }

    public void setEntity(NoticeFacade entity) {
        this.entity = entity;
    }

    public String ruleJury(){
        return SUCCESS;
    }

    public static String html2Str(String html) {
        try {

            Parser parser = Parser.createParser(html, "utf-8");
            TextExtractingVisitor visitor = new TextExtractingVisitor();
            parser.visitAllNodesWith(visitor);
            return visitor.getExtractedText();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * todo
     * 自营商家登录拦截
     * @param methodName
     * @return
     */
    public String check(String methodName) {
//        Long venderId = SysLoginContext.getVenderId();
//        String pin = SysLoginContext.getUserPin();
//        if (venderId == null) {
//            redirectUrl = loginUrl + "http://" + domainName + "/rule/" + methodName + ".action";
//            return "toLogin";
//        }
//
//        int venderIdLength = SysLoginContext.getVenderId().toString().length();
//        if (venderIdLength == 10 && methodName.endsWith("New")) {
//            return "OK";
//        }
//        return "illegal";
        // 本地tomcat调试
        // 五一前不暴露
        return "OK";
    }


    public String getLoginUrl() {
        return loginUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
