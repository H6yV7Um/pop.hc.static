package com.jd.help.center.service.category.impl;

import com.jd.common.util.StringUtils;
import com.jd.common.web.result.Result;
import com.jd.pop.component.url.PopJdUrl;
import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.constants.UMPConstants;
import com.jd.help.center.domain.helpsys.HelpSYS;
import com.jd.help.center.domain.question.HelpQuestion;
import com.jd.help.center.domain.topic.HelpTopic;
import com.jd.help.center.domain.topic.HelpTopicVO;
import com.jd.help.center.manager.category.HelpCategoryManager;
import com.jd.help.center.manager.category.LocalHelpCategoryManager;
import com.jd.help.center.manager.helpsys.HelpSYSManager;
import com.jd.help.center.manager.question.HelpQuestionManager;
import com.jd.help.center.manager.topic.HelpTopicManager;
import com.jd.help.center.service.category.FrontQuestionService;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-12
 * Time: 11:25:43
 * To change this template use File | Settings | File Templates.
 */
public class FrontQuestionServiceImpl implements FrontQuestionService {
    private static final Log log = LogFactory.getLog(FrontQuestionServiceImpl.class);
    private static String SYS_NAME = "Vender";
    private static String CACHE_NAME = "helpCenter_";

    private static String CACHE_LEFT_NAVIGATE_NAME = "help_center_left_navigate_";
    private static final int CATEGORY_VALID = 1;
    String initTime;
    int cacheTime = 0;
    HelpCategoryManager helpCategoryManager;

    HelpQuestionManager helpQuestionManager;

    HelpSYSManager helpSYSManager;

    HelpTopicManager helpTopicManager;

    LocalHelpCategoryManager localHelpCategoryManager;

    private PopJdUrl venderHelpCenterModule;



    public Result getQuestion(String sysName, int topicId) {
        Result result = new Result();
        CallerInfo info = Profiler.registerInfo(UMPConstants.UMP_HELP_GET_QUESTION, UMPConstants.enableHeart, UMPConstants.enableTP);
        try {

            HelpTopic topic = localHelpCategoryManager.getHelpTopicByTopicId(topicId);
            HelpCategory category = localHelpCategoryManager.getHelpCategoryByCategoryId(topic.getCategoryId());
            HelpSYS sys = localHelpCategoryManager.getHelpSysByName(sysName);
            List<HelpQuestion> list = new ArrayList<HelpQuestion>();
            //添加判断此topic是否为此系统下的内容
            if (category.getFid() == sys.getId()) {
                list = getHelpQuestion(topicId);
            } else {
                result.setSuccess(false);
                return result;
            }
            if (list.size() > 0) {
                result.addDefaultModel("questions", list);
            } else {
                result.setResultCode("info.null.error");
                Profiler.functionError(info);
                log.error("Method:getQuestion error,is null");
            }
            HelpTopic helpTopic = localHelpCategoryManager.getHelpTopicByTopicId(topicId);
            HelpTopicVO topicVO = convertTopicToVO(helpTopic);
            result.addDefaultModel("anchorStatus", topicVO.getAnchorStatus());
            if (helpTopic != null) {
                result.addDefaultModel("helpTopic", helpTopic);
            }
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(UMPConstants.UMP_HELP_GET_QUESTION,e);
            result.setSuccess(false);
            Profiler.functionError(info);
            result.setResultCode("system.error");
            log.error("Method:getQuestion----------", e);
        } finally {
            Profiler.registerInfoEnd(info);
        }

        return result;
    }

    public List<HelpQuestion> getHelpQuestion(int topicId) {
        List<HelpQuestion> list;
        //添加判断此topic是否为此系统下的内容
        if (topicId > 0) {
            list = helpQuestionManager.findQuestionFront(topicId);
        } else {
            return Collections.emptyList();
        }
        return list;
    }

    public Result previewQuestion(int topicId) {
        Result result = new Result();
        try {
            //优先从memoryCache中取数据
            List<HelpQuestion> list = getHelpQuestion(topicId);
            if (list.size() > 0) {
                result.addDefaultModel("questions", list);
            } else {
                result.setResultCode("info.null.error");
            }
            HelpTopic helpTopic = localHelpCategoryManager.getHelpTopicByTopicId(topicId);
            HelpTopicVO topicVO = convertTopicToVO(helpTopic);
            result.addDefaultModel("anchorStatus", topicVO.getAnchorStatus());
            if (helpTopic != null) {
                result.addDefaultModel("helpTopic", helpTopic);
            }

            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResultCode("system.error");
            log.error("Method:getQuestion----------", e);
        }
        return result;
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

    private int convertToTime(String time) {
        int reTime = 1;
        try {
            String[] t = time.split("\\*");

            for (int i = 0; i < t.length; i++) {
                String s = t[i];
                reTime = reTime * Integer.parseInt(s);
            }
        } catch (Exception e) {
            reTime = 60 * 60;
        }
        return reTime;
    }

    public void setHelpCategoryManager(HelpCategoryManager helpCategoryManager) {
        this.helpCategoryManager = helpCategoryManager;
    }

    public void setHelpQuestionManager(HelpQuestionManager helpQuestionManager) {
        this.helpQuestionManager = helpQuestionManager;
    }

    public void setHelpSYSManager(HelpSYSManager helpSYSManager) {
        this.helpSYSManager = helpSYSManager;
    }

    public void setHelpTopicManager(HelpTopicManager helpTopicManager) {
        this.helpTopicManager = helpTopicManager;
    }

    public void setLocalHelpCategoryManager(LocalHelpCategoryManager localHelpCategoryManager) {
        this.localHelpCategoryManager = localHelpCategoryManager;
    }

    public void setInitTime(String initTime) {
        this.initTime = initTime;
        cacheTime = convertToTime(initTime);
    }

    public PopJdUrl getVenderHelpCenterModule() {
        return venderHelpCenterModule;
    }

    public void setVenderHelpCenterModule(PopJdUrl venderHelpCenterModule) {
        this.venderHelpCenterModule = venderHelpCenterModule;
    }
}
