package com.jd.help.center.service.navigate.impl;

import com.jd.common.util.StringUtils;
import com.jd.common.web.result.Result;
import com.jd.help.center.domain.HelpCenterCategory;
import com.jd.help.center.domain.HelpCenterLeftNavigate;
import com.jd.help.center.domain.HelpCenterQuestion;
import com.jd.help.center.domain.HelpCenterTopic;
import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.helpsys.HelpSYS;
import com.jd.help.center.domain.question.HelpQuestion;
import com.jd.help.center.domain.question.query.HelpQuestionQuery;
import com.jd.help.center.domain.topic.HelpTopic;
import com.jd.help.center.domain.topic.HelpTopicVO;
import com.jd.help.center.manager.category.LocalHelpCategoryManager;
import com.jd.help.center.service.category.FrontQuestionService;
import com.jd.help.center.service.navigate.LeftNavigateService;
import com.jd.pop.component.url.PopJdUrl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuxianjun
 * Date: 13-7-12
 * Time: 上午11:43
 * To change this template use File | Settings | File Templates.
 */
public class LeftNavigateServiceImpl implements LeftNavigateService {

    private static final Log log = LogFactory.getLog(LeftNavigateServiceImpl.class);

    String urlHead = "//helpcenter.jd.com";


    private static String CACHE_LEFT_NAVIGATE_NAME = "help_center_left_navigate_";

    private LocalHelpCategoryManager localHelpCategoryManager;

    private PopJdUrl venderHelpCenterModule;
    private static final int CATEGORY_VALID = 1;
    protected FrontQuestionService frontQuestionService;

    public Result getLeftNavigateResult(String name) {
        Result result = null;
        int sysId = 0;
        HelpCenterLeftNavigate leftNavigate = getLeftNavigate(name, -1);
        result = new Result();
        result.addDefaultModel("leftNavigate", leftNavigate);
        if (leftNavigate != null /*&& leftNavigate.getCategoryList().size() > 0 */&& leftNavigate.getId() > 0)
            result.setSuccess(true);
        sysId = leftNavigate.getId();
//        } else {
//
//            result = getLeftNavigateOld(name);
//            HelpSYS sys = (HelpSYS) result.get("helpSYS");
//            if (sys != null)
//                sysId = sys.getId();
//        }
        result.addDefaultModel("systemId", sysId);
        return result;
    }

    /**
     * 逐渐使用   getLeftNavigateResult()
     *
     * @param name
     * @return
     */
    @Deprecated
    public Result getLeftNavigateOld(String name) {
        Result result = new Result();
        try {
            HelpSYS sys = new HelpSYS();
            if (name != null && name != "") {
                sys = localHelpCategoryManager.getHelpSysByName(name);
            } else {
                result.setSuccess(false);
                result.setResultCode("system.error");
                log.debug("无系统名");
                return result;
            }
            if (sys == null) {
                result.setSuccess(false);
                result.setResultCode("system.error");
                log.debug("无系统名");
                return result;
            } else {
                if (sys.getStatus() == CATEGORY_VALID) {
                    result.addDefaultModel("helpSYS", sys);
                    List<HelpCategory> curList = localHelpCategoryManager.getHelpCategoryBySysId(sys.getId());
                    List<HelpCategory> list = new ArrayList<HelpCategory>();
                    for (int i = 0; i < curList.size(); i++) {
                        HelpCategory category = curList.get(i);
                        if (category.getStatus() == CATEGORY_VALID) {
                            List<HelpTopic> curTopicList = localHelpCategoryManager.getHelpTopicByCategory(category.getCategoryId());
                            List<HelpTopic> topicList = new ArrayList<HelpTopic>();
                            List<HelpTopicVO> topicVOList = new ArrayList<HelpTopicVO>();
                            for (int j = 0; j < curTopicList.size(); j++) {
                                HelpTopic topic = curTopicList.get(j);
                                if (topic.getStatus() == CATEGORY_VALID) {
                                    topicList.add(topic);
                                    topicVOList.add(convertTopicToVO(topic));
                                }
                            }
                            category.setTopics(topicVOList);
                            list.add(category);
                        }
                    }
                    result.setSuccess(true);
                    if (list.size() > 0) {
                        result.addDefaultModel("categorys", list);
                    } else {
                        result.setResultCode("info.null.error");
                    }
                } else {
                    result.setSuccess(true);
                    result.setResultCode("system.error");
                }
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResultCode("system.error");
            log.error("Method:getLeftNavigate----", e);
        }
        return result;
    }

    /**
     * 获取帮助中心的左侧导航
     *
     * @param sysName
     * @param sysId
     * @return
     */
    public HelpCenterLeftNavigate getLeftNavigate(String sysName, int sysId) {

        HelpSYS sys = null;
        if (sysName != null && sysName != "") {
            sys = localHelpCategoryManager.getHelpSysByName(sysName);
        } else {
            if (sysId > 0)
                sys = localHelpCategoryManager.getHelpSYSById(sysId);
        }
        if (sys == null)
            return null;
        return createHelpCenterLeftNavigate(sys.getName(), sys.getId());
    }

    /**
     * 获取帮助中心的左侧导航
     *
     * @param sysName
     * @return
     */
    public HelpCenterLeftNavigate getLeftNavigate(String sysName) {
        return getLeftNavigate(sysName, -1);
    }

    /**
     * 获取前台帮助中心的左侧导航
     *
     * @return
     */
    public HelpCenterLeftNavigate getLeftNavigate() {
        return getLeftNavigate("help");
    }


    HelpCenterLeftNavigate createHelpCenterLeftNavigate(String sysName, int sysId) {
        HelpCenterLeftNavigate leftNavigate = new HelpCenterLeftNavigate();
        HelpSYS sys = null;
        if (sysName != null && sysName != "") {
            sys = localHelpCategoryManager.getHelpSysByName(sysName);
        } else {
            sys = localHelpCategoryManager.getHelpSYSById(sysId);
        }
        if (sys == null)
            return leftNavigate;
        leftNavigate.setId(sys.getId());
        leftNavigate.setName(sys.getName());

        List<HelpCategory> curList = localHelpCategoryManager.getHelpCategoryBySysId(sys.getId());
        List<HelpCenterCategory> categoryList = new ArrayList<HelpCenterCategory>();
        for (int i = 0; i < curList.size(); i++) {
            HelpCategory category = curList.get(i);
            if (category.getStatus() == CATEGORY_VALID) {
                categoryList.add(createHelpCenterCategory(category, sys.getName()));
            }
        }
        leftNavigate.setCategoryList(categoryList);
        return leftNavigate;
    }


    private HelpCenterCategory createHelpCenterCategory(HelpCategory category, String systemName) {
        HelpCenterCategory helpCenterCategory = new HelpCenterCategory();
        helpCenterCategory.setName(category.getName());
        helpCenterCategory.setCategoryId(category.getCategoryId());
        helpCenterCategory.setSortOrder(category.getSortOrder());
        helpCenterCategory.setSysId(category.getFid());

        List<HelpTopic> curTopicList = localHelpCategoryManager.getHelpTopicByCategory(category.getCategoryId());
        List<HelpCenterTopic> topicList = new ArrayList<HelpCenterTopic>();
        for (int i = 0; i < curTopicList.size(); i++) {
            HelpTopic topic = curTopicList.get(i);
            if (topic.getStatus() == CATEGORY_VALID) {
                topicList.add(createHelpCenterTopic(topic, systemName));
            }
        }
        helpCenterCategory.setTopicList(topicList);

        return helpCenterCategory;
    }

    HelpCenterTopic createHelpCenterTopic(HelpTopic topic, String systemName) {
        HelpTopicVO vo = convertTopicToVO(topic);
        HelpCenterTopic centerTopic = new HelpCenterTopic();
        centerTopic.setSortOrder(topic.getSortOrder());
        centerTopic.setCategoryId(topic.getCategoryId());
        centerTopic.setName(topic.getName());
        centerTopic.setTopicId(topic.getTopicId());
        centerTopic.setLogoType(vo.getLogoType());
        String url = "#";
        if (StringUtils.isNotBlank(vo.getUrl())) {
            url = vo.getUrl();
        } else {
            String urlInfo = "/" + systemName + "/question-" + topic.getTopicId() + ".html";
            if (venderHelpCenterModule != null) {
                url = venderHelpCenterModule.getTarget(urlInfo).toString();
            } else {
                url = urlHead + urlInfo;
            }
        }

        if (StringUtils.isNotBlank(systemName) && hasThreeAdd(systemName)) {
            List<HelpCenterQuestion> questionList = new ArrayList<HelpCenterQuestion>();
            List<HelpQuestion> questions = this.frontQuestionService.getHelpQuestion(topic.getTopicId());
            for (HelpQuestion question : questions) {
                questionList.add(createHelpCenterQuestion(question, systemName));
            }
            centerTopic.setQuestionList(questionList);
        }


        centerTopic.setUrl(url);

        return centerTopic;
    }

    /**
     * 是否需要三级地址
     *
     * @param systemName
     * @return
     */
    boolean hasThreeAdd(String systemName) {
        if ("Vender".equals(systemName)) {
            return true;
        }
        return false;

    }

    HelpCenterQuestion createHelpCenterQuestion(HelpQuestion helpQuestion, String systemName) {
        HelpCenterQuestion centerQuestion = new HelpCenterQuestion();
        centerQuestion.setSortOrder(helpQuestion.getSortOrder());
        centerQuestion.setTopicId(helpQuestion.getTopicId());
//      centerQuestion.setAnswer(helpQuestion.getAnswer());
        centerQuestion.setQuestion(helpQuestion.getQuestion());
        centerQuestion.setQuestionId(helpQuestion.getQuestionId());
        centerQuestion.setLogoType(new HelpQuestionQuery(helpQuestion).getLogoType());
        String url = "#";
        String urlInfo = "/" + systemName + "/viewQuestion-" + helpQuestion.getTopicId() + "-" + helpQuestion.getQuestionId() + ".html";
        String anchorUrlInfo = "/" + systemName + "/question-" + helpQuestion.getTopicId() + ".html#help" + helpQuestion.getQuestionId();
        String anchorUrl = "#";
        if (venderHelpCenterModule != null) {
            url = venderHelpCenterModule.getTarget(urlInfo).toString();
            anchorUrl = venderHelpCenterModule.getTarget(anchorUrlInfo).toString();
        } else {
            url = urlHead + urlInfo;
            anchorUrl = urlHead + anchorUrlInfo;
        }
        centerQuestion.setUrl(url);
        centerQuestion.setAnchorUrl(anchorUrl);

        return centerQuestion;

    }


    private HelpTopicVO convertTopicToVO(HelpTopic helpTopic) {
        HelpTopicVO topicVO = new HelpTopicVO(helpTopic);
        String features = helpTopic.getFeatures();
        //features格式为url:http://www.360buy.com^type:1
        if (StringUtils.isNotBlank(features)) {
            String featureGroup[] = features.split("\\^");
            for (int i = 0; i < featureGroup.length; i++) {
                String s = featureGroup[i];
                String[] curFeature = s.split(":");
                for (int j = 0; j < curFeature.length; j++) {
                    String s1 = curFeature[j];
                    //url中含有：，需要排除
                    if ("url".equals(s1)) {
                        topicVO.setUrl(curFeature[j + 1] + ":" + curFeature[j + 2]);
                        break;
                    }
                    if ("type".equals(s1)) {
                        topicVO.setLogoType(Integer.parseInt(curFeature[j + 1]));
                        break;
                    }
                    if ("anchor".equals(s1)) {
                        topicVO.setAnchorStatus(Integer.parseInt(curFeature[j + 1]));
                        break;
                    }
                }
            }
        }
        return topicVO;
    }

    public LocalHelpCategoryManager getLocalHelpCategoryManager() {
        return localHelpCategoryManager;
    }

    public void setLocalHelpCategoryManager(LocalHelpCategoryManager localHelpCategoryManager) {
        this.localHelpCategoryManager = localHelpCategoryManager;
    }

    public PopJdUrl getVenderHelpCenterModule() {
        return venderHelpCenterModule;
    }

    public void setVenderHelpCenterModule(PopJdUrl venderHelpCenterModule) {
        this.venderHelpCenterModule = venderHelpCenterModule;
    }

    public FrontQuestionService getFrontQuestionService() {
        return frontQuestionService;
    }

    public void setFrontQuestionService(FrontQuestionService frontQuestionService) {
        this.frontQuestionService = frontQuestionService;
    }
}
