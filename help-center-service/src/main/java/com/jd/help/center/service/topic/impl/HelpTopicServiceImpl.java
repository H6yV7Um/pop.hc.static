package com.jd.help.center.service.topic.impl;

import com.jd.common.util.StringUtils;
import com.jd.common.web.result.Result;

import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.helpsys.HelpSYS;
import com.jd.help.center.domain.topic.HelpTopic;
import com.jd.help.center.domain.topic.HelpTopicVO;
import com.jd.help.center.manager.category.LocalHelpCategoryManager;
import com.jd.help.center.manager.topic.HelpTopicManager;
import com.jd.help.center.service.topic.HelpTopicService;
import com.jd.pop.component.url.PopJdUrl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-2
 * Time: 10:58:01
 * To change this template use File | Settings | File Templates.
 */
public class HelpTopicServiceImpl implements HelpTopicService {
    private static final Log log = LogFactory.getLog(HelpTopicServiceImpl.class);

    private HelpTopicManager helpTopicManager;

    private LocalHelpCategoryManager localHelpCategoryManager;

    private PopJdUrl venderHelpCenterModule;

    public void setHelpTopicManager(HelpTopicManager helpTopicManager) {
        this.helpTopicManager = helpTopicManager;
    }

    public void setLocalHelpCategoryManager(LocalHelpCategoryManager localHelpCategoryManager) {
        this.localHelpCategoryManager = localHelpCategoryManager;
    }

    public Result insertTopic(HelpTopicVO helpTopicVO) {
        Result result=new Result();
        try {
            HelpTopic topic=convertVOToTopic(helpTopicVO);
            int i=helpTopicManager.insertTopic(topic);
            if(i>0){
                result.setSuccess(true);
                topic.setTopicId(i);
                localHelpCategoryManager.getHelpTopicByCategory(topic.getCategoryId()).add(topic);
                localHelpCategoryManager.getTopicMap().put(topic.getTopicId(),topic);
            }else{
                 result.setSuccess(false);
                 result.setResultCode("info.topic.insert.error");
            }
        } catch (Exception e) {
            log.error("Method:insertTopic------->"+e.getMessage());
            result.setSuccess(false);
            result.setResultCode("info.topic.insert.error");
        }
        return result;
    }

    public Result updateTopic(HelpTopicVO helpTopicVO) {
        Result result=new Result();
        try {
            HelpTopic topic=convertVOToTopic(helpTopicVO);
            int i=helpTopicManager.updateTopic(topic);
            if(i>0){
                result.setSuccess(true);
                PropertyUtils.copyProperties(localHelpCategoryManager.getHelpTopicByTopicId(helpTopicVO.getTopicId()),topic);
            }else{
                result.setSuccess(false);
                result.setResultCode("info.topic.update.error");
            }
        } catch (Exception e) {
            log.error("Method:updateTopic------->"+e.getMessage());
            result.setSuccess(false);
            result.setResultCode("info.topic.update.error");
        }
        return result;
    }

    public Result findTopicByCategoryId(int categoryId) {
        Result result=new Result();
        try {
            List<HelpTopicVO> helpTopicVOList = new ArrayList<HelpTopicVO>();
            HelpCategory helpCategory = localHelpCategoryManager.getHelpCategoryByCategoryId(categoryId);
            if(helpCategory!=null){
                HelpSYS helpSYS = localHelpCategoryManager.getHelpSYSById(helpCategory.getFid());
                List<HelpTopic> list= helpTopicManager.findTopicByCategoryId(categoryId);
                for (int i = 0; i < list.size(); i++) {
                    HelpTopicVO helpTopicVO =convertTopicToVO(list.get(i));
                    //非外链
                    if(StringUtils.isBlank(helpTopicVO.getUrl())){
                        String urlInfo = "//helpcenter.jd.com/" + helpSYS.getName() + "/question-" + helpTopicVO.getTopicId() + ".html";
                        helpTopicVO.setUrl(urlInfo);
                    }
                    helpTopicVOList.add(helpTopicVO);
                }
                result.setSuccess(true);
            }
            if(helpTopicVOList.size()>0){
                result.addDefaultModel("topics",helpTopicVOList);
            }else{
                result.setResultCode("info.topic.load.error");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            log.error("Method:findTopicByCategoryId--------->"+e.getMessage());
            result.setResultCode("info.topic.load.error");
        }
        return result;
    }


    public Result getTopicById(int id) {
        Result result=new Result();
        try {
            HelpTopic helpTopic=localHelpCategoryManager.getHelpTopicByTopicId(id);
            HelpTopicVO topic=convertTopicToVO(helpTopic);
            result.setSuccess(true);
            if(helpTopic!=null){
                result.addDefaultModel("helpTopicVO",topic);
            }else{
                result.setResultCode("info.topic.load.error");
            }
        } catch (Exception e) {
            log.error("Method:getTopicById------->"+e.getMessage());
            result.setSuccess(false);
            result.setResultCode("info.topic.load.error");
        }
        return result;
    }

    public Result updateTopicStatus(HelpTopic helpTopic) {
        Result result=new Result();
        try {
            int i=helpTopicManager.updateTopicStatus(helpTopic);
            if(i>0){
                result.setSuccess(true);
                localHelpCategoryManager.getHelpTopicByTopicId(helpTopic.getTopicId()).setStatus(helpTopic.getStatus());
            }else{
                result.setSuccess(false);
                result.setResultCode("info.topic.update.error");
            }
        } catch (Exception e) {
            log.error("Method:updateTopic------->"+e.getMessage());
            result.setSuccess(false);
            result.setResultCode("info.topic.update.error");
        }
        return result;
    }

    public Result deleteTopic(HelpTopic helpTopic) {
        Result result=new Result();
        try {
            helpTopic.setStatus(-1);
            int i=helpTopicManager.updateTopicStatus(helpTopic);
            if(i>0){
                result.setSuccess(true);
                localHelpCategoryManager.getTopicMap().remove(helpTopic.getTopicId());
            }else{
                result.setSuccess(false);
                result.setResultCode("info.topic.delete.error");
            }
        } catch (Exception e) {
            log.error("Method:deleteTopic topicId:"+helpTopic.getTopicId()+"------->"+e.getMessage());
            result.setSuccess(false);
            result.setResultCode("info.topic.delete.error");
        }
        return result;
    }

    private HelpTopicVO convertTopicToVO(HelpTopic helpTopic){
        HelpTopicVO topicVO=new HelpTopicVO(helpTopic);
        String features=helpTopic.getFeatures();
        //features格式为url:http://www.360buy.com^type:1
        if(StringUtils.isNotBlank(features)){
            String featureGroup[]=features.split("\\^");
            for (int i = 0; i < featureGroup.length; i++) {
                String s = featureGroup[i];
                String[] curFeature=s.split(":");
                for (int j = 0; j < curFeature.length; j++) {
                    String s1 = curFeature[j];
                    //url中含有：，需要排除
                    if("url".equals(s1)){
                        if(curFeature.length==3){
                            topicVO.setUrl(curFeature[j+1]+":"+curFeature[j+2]);
                        }else{
                            topicVO.setUrl(curFeature[j+1]);
                        }
                        break;
                    }
                    if("type".equals(s1)){
                        topicVO.setLogoType(Integer.parseInt(curFeature[j+1]));
                        break;
                    }
                    if("anchor".equals(s1)){
                        topicVO.setAnchorStatus(Integer.parseInt(curFeature[j+1]));
                        break;
                    }
                }
            }
        }
        return topicVO;
    }

    private HelpTopic convertVOToTopic(HelpTopicVO topicVO){
        HelpTopic topic=new HelpTopic(topicVO);
        StringBuffer sbf=new StringBuffer();
        if(StringUtils.isNotBlank(topicVO.getUrl())){
            sbf.append("url:").append(topicVO.getUrl()).append("^");
        }
        if(topicVO.getLogoType()>0){
            sbf.append("type:").append(topicVO.getLogoType()).append("^");
        }
        if(topicVO.getAnchorStatus()>0){
            sbf.append("anchor:").append(topicVO.getAnchorStatus()).append("^");
        }
        if(StringUtils.isNotBlank(sbf.toString())){
            topic.setFeatures(sbf.toString().substring(0,sbf.length()-1));
        }else{
            topic.setFeatures(null);
        }
        return topic;
    }

    public void setVenderHelpCenterModule(PopJdUrl venderHelpCenterModule) {
        this.venderHelpCenterModule = venderHelpCenterModule;
    }
}
