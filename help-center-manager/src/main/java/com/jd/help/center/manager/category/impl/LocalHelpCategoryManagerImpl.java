package com.jd.help.center.manager.category.impl;

import com.jd.help.center.domain.border.BorderInfo;
import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.constants.UMPConstants;
import com.jd.help.center.domain.helpsys.HelpSYS;
import com.jd.help.center.domain.helpsys.LocalHelpCategory;
import com.jd.help.center.domain.topic.HelpTopic;
import com.jd.help.center.manager.border.HelpBorderManager;
import com.jd.help.center.manager.category.HelpCategoryManager;
import com.jd.help.center.manager.category.LocalHelpCategoryManager;
import com.jd.help.center.manager.helpsys.HelpSYSManager;
import com.jd.help.center.manager.topic.HelpTopicManager;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-12-1
 * Time: 16:43:52
 * To change this template use File | Settings | File Templates.
 */
public class LocalHelpCategoryManagerImpl implements LocalHelpCategoryManager {
    private static final Log log= LogFactory.getLog(LocalHelpCategoryManagerImpl.class);
    private static final LocalHelpCategory localHelpCategory=new LocalHelpCategory();
    private HelpCategoryManager helpCategoryManager;
    private HelpTopicManager helpTopicManager;
    private HelpSYSManager helpSYSManager;
    private HelpBorderManager helpBorderManager;

    public void setHelpCategoryManager(HelpCategoryManager helpCategoryManager) {
        this.helpCategoryManager = helpCategoryManager;
    }

    public void setHelpTopicManager(HelpTopicManager helpTopicManager) {
        this.helpTopicManager = helpTopicManager;
    }

    public void setHelpSYSManager(HelpSYSManager helpSYSManager) {
        this.helpSYSManager = helpSYSManager;
    }

    public LocalHelpCategory initLocalHelpCategory(){
        CallerInfo info = Profiler.registerInfo(UMPConstants.UMP_HELP_INIT_CATEGORY, UMPConstants.enableHeart, UMPConstants.enableTP);
        log.info("Mehtod:initLocalHelpCategory-->category init begin");
        long start = System.currentTimeMillis();
        Map<Integer,HelpSYS> sysMap=new HashMap<Integer,HelpSYS>();
        Map<Integer,HelpCategory> categoryMap=new HashMap<Integer,HelpCategory>();
        Map<Integer,HelpTopic> topicMap=new HashMap<Integer,HelpTopic>();
        Map<Integer,List<HelpTopic>> topicListMap=new HashMap<Integer,List<HelpTopic>>();
        Map<Integer,List<HelpCategory>> categoryListMap=new HashMap<Integer,List<HelpCategory>>();
        Map<String,BorderInfo> borderInfoMap = new HashMap<String,BorderInfo>();
        List<BorderInfo> borderInfoList = new ArrayList<BorderInfo>();
        List<HelpSYS> sysList= null;
        try {
            sysList = helpSYSManager.findSYSAll();
            for (int i = 0; i < sysList.size(); i++) {
                HelpSYS helpSYS=sysList.get(i);
                sysMap.put(helpSYS.getId(),helpSYS);
                List<HelpCategory> categoryList=helpCategoryManager.findCategoryAllById(helpSYS.getId());
                categoryListMap.put(helpSYS.getId(),categoryList);
                for (int j = 0; j <categoryList.size(); j++) {
                    HelpCategory category =categoryList.get(j);
                    categoryMap.put(category.getCategoryId(),category);
                    List<HelpTopic> topicList=helpTopicManager.findTopicByCategoryId(category.getCategoryId());
                    convertToMap(topicMap,topicList);
                    topicListMap.put(category.getCategoryId(),topicList);
                }
            }
            //取头尾文件及主页
            borderInfoList = helpBorderManager.getAllBorderInfo();
            if(borderInfoList!=null && borderInfoList.size()>0){
                for (int i = 0; i < borderInfoList.size(); i++) {
                    borderInfoMap.put(borderInfoList.get(i).getKey(),borderInfoList.get(i));
                }
            }

        } catch (Exception e) {
            log.error(UMPConstants.UMP_HELP_INIT_CATEGORY,e);
            log.error("Method:initLocalHelpCategory error-------->",e);
            Profiler.functionError(info);
        }catch (Throwable e){
            log.error("Method:initLocalHelpCategory error-------->",e);
            Profiler.functionError(info);
        }
        //防止重新刷新时没取到数据
        if(sysList != null && sysList.size() > 0)
            localHelpCategory.setHelpSYSList(sysList);
        //防止重新刷新时没取到数据
        if(categoryListMap != null && categoryListMap.size() > 0)
            localHelpCategory.setCategoryListMap(categoryListMap);
        if(topicListMap != null && topicListMap.size() > 0)
            localHelpCategory.setTopicListMap(topicListMap);
        if(sysMap != null && sysMap.size() > 0)
            localHelpCategory.setSysMap(sysMap);
        if(categoryMap != null && categoryMap.size() > 0)
            localHelpCategory.setCategoryMap(categoryMap);
        if(topicMap != null && topicMap.size() > 0)
            localHelpCategory.setTopicMap(topicMap);

        //防止重新刷新时没取到数据
        if(borderInfoMap != null && borderInfoMap.size() > 0)
            localHelpCategory.setBorderInfoMap(borderInfoMap);
        if(borderInfoList != null && borderInfoList.size() > 0)
            localHelpCategory.setBorderInfoList(borderInfoList);
        log.info("Mehtoud:initLocalHelpCategory-->category init end,Elapsed time:"+(System.currentTimeMillis()-start)+" milliseconds");
        Profiler.registerInfoEnd(info);
        return localHelpCategory;
    }

    private void convertToMap(Map<Integer,HelpTopic> topicMap,List<HelpTopic> topicList){
        for (int i = 0; i <topicList.size(); i++) {
            HelpTopic topic=topicList.get(i);
            topicMap.put(topic.getTopicId(),topic);
        }
    }

    public List<HelpSYS> getAllHelpSYSList() {
        return localHelpCategory.getHelpSYSList();
    }

    public HelpSYS getHelpSYSById(int id){
        return localHelpCategory.getSysMap().get(id);
    }

    public HelpCategory getHelpCategoryByCategoryId(int categoryId){
        return localHelpCategory.getCategoryMap().get(categoryId);
    }

    public HelpTopic getHelpTopicByTopicId(int topicId){
        return localHelpCategory.getTopicMap().get(topicId);
    }

    public List<HelpTopic> getHelpTopicByCategory(int categoryId) {
        return localHelpCategory.getTopicListMap().get(categoryId);
    }

    public List<HelpCategory> getHelpCategoryBySysId(int sysId) {
        return localHelpCategory.getCategoryListMap().get(sysId);
    }

    public HelpSYS getHelpSysByName(String name) {
        List<HelpSYS> sysList=localHelpCategory.getHelpSYSList();
        if(sysList ==null){
            sysList = helpSYSManager.findSYSAll();
            localHelpCategory.getHelpSYSList().addAll(sysList);
        }
        for (int i = 0; i <sysList.size(); i++) {
            HelpSYS sys=sysList.get(i);
            if(sys.getName().equals(name)){
                return sys;
            }
        }
        return null;
    }

    public Map<Integer, List<HelpCategory>> getCategoryListMap() {
        return localHelpCategory.getCategoryListMap();
    }

    public Map<Integer, HelpCategory> getCategoryMap() {
        return localHelpCategory.getCategoryMap();
    }

    public Map<Integer, HelpSYS> getSysMap() {
        return localHelpCategory.getSysMap();
    }

    public Map<Integer, List<HelpTopic>> getTopicListMap() {
        return localHelpCategory.getTopicListMap();
    }

    public Map<Integer, HelpTopic> getTopicMap() {
        return localHelpCategory.getTopicMap();
    }

    public Map<String, BorderInfo> getBorderInfoMap() {
        return localHelpCategory.getBorderInfoMap();
    }

    public List<BorderInfo> getBorderInfoList() {
        return localHelpCategory.getBorderInfoList();
    }

    public void setHelpBorderManager(HelpBorderManager helpBorderManager) {
        this.helpBorderManager = helpBorderManager;
    }
}
