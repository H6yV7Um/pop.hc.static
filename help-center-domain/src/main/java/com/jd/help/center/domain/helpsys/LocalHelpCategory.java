package com.jd.help.center.domain.helpsys;

import com.jd.help.center.domain.border.BorderInfo;
import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.topic.HelpTopic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-11-30
 * Time: 17:26:49
 * To change this template use File | Settings | File Templates.
 */
public class LocalHelpCategory implements Serializable { 

    private List<HelpSYS> helpSYSList=new ArrayList<HelpSYS>();

    Map<Integer,List<HelpTopic>> topicListMap=new HashMap<Integer,List<HelpTopic>>();
    Map<Integer,List<HelpCategory>> categoryListMap=new HashMap<Integer,List<HelpCategory>>();

    Map<Integer,HelpSYS> sysMap=new HashMap<Integer,HelpSYS>();
    Map<Integer,HelpCategory> categoryMap=new HashMap<Integer,HelpCategory>();
    Map<Integer,HelpTopic> topicMap=new HashMap<Integer,HelpTopic>();

    Map<String,BorderInfo> borderInfoMap = new HashMap<String,BorderInfo>();

    List<BorderInfo> borderInfoList = new ArrayList<BorderInfo>();

    public List<HelpSYS> getHelpSYSList() {
        return helpSYSList;
    }

    public void setHelpSYSList(List<HelpSYS> helpSYSList) {
        this.helpSYSList = helpSYSList;
    }

    public Map<Integer, List<HelpTopic>> getTopicListMap() {
        return topicListMap;
    }

    public void setTopicListMap(Map<Integer, List<HelpTopic>> topicListMap) {
        this.topicListMap = topicListMap;
    }

    public Map<Integer, List<HelpCategory>> getCategoryListMap() {
        return categoryListMap;
    }

    public void setCategoryListMap(Map<Integer, List<HelpCategory>> categoryListMap) {
        this.categoryListMap = categoryListMap;
    }

    public Map<Integer, HelpCategory> getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(Map<Integer, HelpCategory> categoryMap) {
        this.categoryMap = categoryMap;
    }

    public Map<Integer, HelpSYS> getSysMap() {
        return sysMap;
    }

    public void setSysMap(Map<Integer, HelpSYS> sysMap) {
        this.sysMap = sysMap;
    }

    public Map<Integer, HelpTopic> getTopicMap() {
        return topicMap;
    }

    public void setTopicMap(Map<Integer, HelpTopic> topicMap) {
        this.topicMap = topicMap;
    }

    public Map<String, BorderInfo> getBorderInfoMap() {
        return borderInfoMap;
    }

    public void setBorderInfoMap(Map<String, BorderInfo> borderInfoMap) {
        this.borderInfoMap = borderInfoMap;
    }

    public List<BorderInfo> getBorderInfoList() {
        return borderInfoList;
    }

    public void setBorderInfoList(List<BorderInfo> borderInfoList) {
        this.borderInfoList = borderInfoList;
    }
}
