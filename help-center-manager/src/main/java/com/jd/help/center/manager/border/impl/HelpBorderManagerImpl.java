package com.jd.help.center.manager.border.impl;

import com.jd.common.util.StringUtils;
import com.jd.help.center.domain.border.BorderInfo;
import com.jd.help.center.manager.border.HelpBorderManager;
import com.jd.help.center.manager.category.LocalHelpCategoryManager;
import com.jd.mongodbclient.UpdateOperation;
import com.jd.mongodbclient2.MongoDBClient;
import com.mongodb.QueryBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.Exception;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 12-10-10
 * Time: 上午10:59
 * To change this template use File | Settings | File Templates.
 */
public class HelpBorderManagerImpl implements HelpBorderManager {
    private static final Log log = LogFactory.getLog(HelpBorderManagerImpl.class);
    private MongoDBClient helpBorderMgDao;
    private LocalHelpCategoryManager localHelpCategoryManager;

    public List<BorderInfo> getAllBorderInfo() {
        return helpBorderMgDao.select(new QueryBuilder(),0,200,BorderInfo.class);
    }

    public List<BorderInfo> getBorderInfoByType(String type) {
        List<BorderInfo> borderInfoList = new ArrayList<BorderInfo>();
        borderInfoList = localHelpCategoryManager.getBorderInfoList();
        //内存中无数据
        if(borderInfoList==null ||(borderInfoList!=null && borderInfoList.size()<1)){
            borderInfoList = helpBorderMgDao.select(new QueryBuilder(),0,200,BorderInfo.class);
            localHelpCategoryManager.getBorderInfoList().addAll(borderInfoList);
            for (int i = 0; i < borderInfoList.size(); i++) {
                localHelpCategoryManager.getBorderInfoMap().put(borderInfoList.get(i).getKey(),borderInfoList.get(i));
            }
        }
        List<BorderInfo> returnBorderInfoList = new ArrayList<BorderInfo>();
        for (int i = 0; i < borderInfoList.size(); i++) {
            //返回需要的类型
             if(type.equals(borderInfoList.get(i).getType())){
                 returnBorderInfoList.add(borderInfoList.get(i));
             }

        }
        return returnBorderInfoList;
    }

    public boolean insertOrUpdateBorderInfo(BorderInfo borderInfo) {
        Map<String,String> optionMap = new HashMap<String,String>();
        optionMap.put(BorderInfo.HELP_BORDER_CONTENT, UpdateOperation.SET);
        optionMap.put(BorderInfo.HELP_BORDER_REMARK, UpdateOperation.SET);
        try{
            return helpBorderMgDao.insertOrUpdate(QueryBuilder.start(BorderInfo.HELP_BORDER_KEY).is(borderInfo.getKey()),borderInfo,optionMap);
        }catch (Exception e){
            log.error("Method: insertOrUpdateBorderInfo error,key:"+borderInfo.getKey(),e);
            return false;
        }
    }

    public boolean updateBorderInfo(BorderInfo borderInfo) {
        Map<String,String> optionMap = new HashMap<String,String>();
        optionMap.put(BorderInfo.HELP_BORDER_CONTENT, UpdateOperation.SET);
        optionMap.put(BorderInfo.HELP_BORDER_REMARK, UpdateOperation.SET);
        optionMap.put(BorderInfo.HELP_BORDER_TYPE, UpdateOperation.SET);
        try{

            boolean  flag = helpBorderMgDao.update(QueryBuilder.start(BorderInfo.HELP_BORDER_KEY).is(borderInfo.getKey()),borderInfo,optionMap);
            if(flag){
                //更新缓存中的内容
                localHelpCategoryManager.getBorderInfoMap().put(borderInfo.getKey(),borderInfo);
                return flag;
            }
        }catch (Exception e){
            log.error("Method:updateBorderInfo error,key:"+borderInfo.getKey(),e);
            return false;
        }
        return false;
    }

    public boolean insertBorderInfo(BorderInfo borderInfo) {
        try{
            BorderInfo borderInfoDB = (BorderInfo)helpBorderMgDao.selectOne(QueryBuilder.start(BorderInfo.HELP_BORDER_KEY).is(borderInfo.getKey()),BorderInfo.class);
            if(borderInfoDB!=null && StringUtils.isNotBlank(borderInfoDB.getContent())){
                log.error("Method:insertBorderInfo error ,data has existed,key:"+borderInfo.getKey());
                return false;
            }

            boolean flag = helpBorderMgDao.insert(borderInfo);
            if(flag){
                //更新缓存中的内容
                localHelpCategoryManager.getBorderInfoMap().put(borderInfo.getKey(),borderInfo);
                localHelpCategoryManager.getBorderInfoList().add(borderInfo);
                return flag;
            }
        }catch (Exception e){
            log.error("Method:insertBorderInfo error ,key:"+borderInfo.getKey());
            return false;
        }
        return false;
    }

    public BorderInfo getBorderInfoByKey(String key) {
        //先从缓存中读取
        BorderInfo borderInfo = new BorderInfo();
        borderInfo = localHelpCategoryManager.getBorderInfoMap().get(key);
        if(borderInfo ==null){
            borderInfo=(BorderInfo)helpBorderMgDao.selectOne(QueryBuilder.start(BorderInfo.HELP_BORDER_KEY).is(key),BorderInfo.class);
            if(borderInfo!=null){
                localHelpCategoryManager.getBorderInfoMap().put(borderInfo.getKey(),borderInfo);
            }
        }
        return borderInfo;
    }

    public boolean deleteBorder(String key){
        boolean flag = helpBorderMgDao.delete(QueryBuilder.start(BorderInfo.HELP_BORDER_KEY).is(key));
        if(flag){
            localHelpCategoryManager.getBorderInfoMap().remove(key);
        }
        return flag;
    }

    public BorderInfo getBorderInfoFrontByKey(String key) {
        BorderInfo borderInfo = new BorderInfo();
        try{
            borderInfo = (BorderInfo)helpBorderMgDao.selectOne(QueryBuilder.start(BorderInfo.HELP_BORDER_KEY).is(key),BorderInfo.class);
            //如果取出的头尾文件为空的话，需要赋给其一个默认的头/尾
            if(borderInfo ==null){
                //头文件
                if(key.indexOf("head")>-1){
                    borderInfo = (BorderInfo)helpBorderMgDao.selectOne(QueryBuilder.start(BorderInfo.HELP_BORDER_KEY).is("head"),BorderInfo.class);
                }else if(key.indexOf("bottom")>-1){ //尾文件
                    borderInfo = (BorderInfo)helpBorderMgDao.selectOne(QueryBuilder.start(BorderInfo.HELP_BORDER_KEY).is("bottom"),BorderInfo.class);
                }
            }
        }catch (Exception e){
            log.error("Method:getBorderInfoFrontByKey error,key :"+key,e);
        }
        return borderInfo;
    }

    public void setHelpBorderMgDao(MongoDBClient helpBorderMgDao) {
        this.helpBorderMgDao = helpBorderMgDao;
    }

    public void setLocalHelpCategoryManager(LocalHelpCategoryManager localHelpCategoryManager) {
        this.localHelpCategoryManager = localHelpCategoryManager;
    }
}
