package com.jd.help.center.service.category.impl;

import com.jd.common.web.result.Result;
import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.category.query.HelpCategoryQuery;
import com.jd.help.center.domain.helpsys.HelpSYS;
import com.jd.help.center.manager.category.HelpCategoryManager;
import com.jd.help.center.manager.category.LocalHelpCategoryManager;
import com.jd.help.center.manager.helpsys.HelpSYSManager;
import com.jd.help.center.service.category.HelpCategoryService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-1
 * Time: 14:42:03
 * To change this template use File | Settings | File Templates.
 */
public class HelpCategoryServiceImpl implements HelpCategoryService {
    private static final Log log = LogFactory.getLog(HelpCategoryServiceImpl.class);
    HelpCategoryManager helpCategoryManager;
    HelpSYSManager helpSYSManager;

    LocalHelpCategoryManager localHelpCategoryManager;

    public void setHelpCategoryManager(HelpCategoryManager helpCategoryManager) {
        this.helpCategoryManager = helpCategoryManager;
    }

    public void setHelpSYSManager(HelpSYSManager helpSYSManager) {
        this.helpSYSManager = helpSYSManager;
    }

    public void setLocalHelpCategoryManager(LocalHelpCategoryManager localHelpCategoryManager) {
        this.localHelpCategoryManager = localHelpCategoryManager;
    }

    public Result findCategoryAllById(int id) {
        Result result=new Result();
        try {
            List<HelpCategory> list=helpCategoryManager.findCategoryAllById(id);
            result.setSuccess(true);
            if(list.size()>0){
                result.addDefaultModel("categorys",list);
            }else{
                result.setResultCode("info.category.load.error");
            }
        } catch (Exception e) {
            log.error("Method:findCategoryAll----------->"+e.getMessage());
            result.setResultCode("info.category.load.error");
            result.setSuccess(false);
        }
        return result;
    }

    public Result findCategoryByQuery(HelpCategoryQuery helpCategoryQuery) {
        Result result = new Result();
        try {
            if(helpCategoryQuery == null || helpCategoryQuery.getSysId() == null){
                throw  new RuntimeException();
            }
            List<HelpCategory> list = helpCategoryManager.findCategoryByQuery(helpCategoryQuery);
            result.addDefaultModel("categorys",list);
            result.setSuccess(true);
        }catch (Exception e){
            log.error("Method:findCategoryByQuery----------->"+e.getMessage());
            result.setResultCode("system.error");
        }

        return result;
    }

    public Result updateCategory(HelpCategory helpCategory) {
        Result result=new Result();
        try {
            int i=helpCategoryManager.updateCategory(helpCategory);
            result.setSuccess(true);
            if(i<=0){
                result.setResultCode("info.category.update.error");
                result.setSuccess(false);
            }else{
                helpCategory.setCategoryId(i);
                PropertyUtils.copyProperties(localHelpCategoryManager.getHelpCategoryByCategoryId(helpCategory.getCategoryId()),helpCategory);
            }
        } catch (Exception e) {
            log.error("Method:updateCategory----->"+e.getMessage());
            result.setResultCode("info.category.update.error");
            result.setSuccess(false);
        }
        return result;
    }

    public Result insertCategory(HelpCategory helpCategory) {
        Result result=new Result();
        try {
            int i=helpCategoryManager.insertCategory(helpCategory);
            result.setSuccess(true);
            if(i<=0){
                result.setResultCode("info.category.insert.error");
                result.setSuccess(false);
            }else{
                helpCategory.setCategoryId(i);
                localHelpCategoryManager.getCategoryListMap().get(helpCategory.getFid()).add(helpCategory);
                localHelpCategoryManager.getCategoryMap().put(helpCategory.getCategoryId(),helpCategory);
            }
        } catch (Exception e) {
            log.error("Method:insertCategory----->"+e.getMessage());
            result.setResultCode("info.category.insert.error");
            result.setSuccess(false);
        }
        return result;
    }

    public Result getCategoryById(int categoryId) {
        Result result=new Result();
        try {
            HelpCategory helpCategory=localHelpCategoryManager.getHelpCategoryByCategoryId(categoryId);
            List<HelpSYS> list=localHelpCategoryManager.getAllHelpSYSList();
            result.addDefaultModel("helpSYSs",list);
            result.setSuccess(true);
            if(helpCategory==null)
                result.setResultCode("info.category.load.error");
            result.addDefaultModel("helpCategory",helpCategory);
        } catch (Exception e) {
            log.error("Method:getCategoryById--->"+e.getMessage());
            result.setSuccess(false);
            result.setResultCode("info.category.load.error");
        }

        return result;
    }

    public Result updateCategoryStatus(HelpCategory helpCategory) {
        Result result=new Result();
        try {
            int i=helpCategoryManager.updateCategoryStatus(helpCategory);
            result.setSuccess(true);
            if(i<=0){
                result.setSuccess(false);
                result.setResultCode("info.category.update.error");
            }else{
                localHelpCategoryManager.getHelpCategoryByCategoryId(helpCategory.getCategoryId()).setStatus(helpCategory.getStatus());
            }
        } catch (Exception e) {
            log.error("Method:updateCategoryStatus------>"+e.getMessage());
            result.setSuccess(false);
            result.setResultCode("info.category.update.error");
        }
        return result;
    }

    public Result deleteCategory(HelpCategory helpCategory) {
        Result result=new Result();
        try {
            helpCategory.setStatus(-1);
            int i=helpCategoryManager.updateCategoryStatus(helpCategory);
            result.setSuccess(true);
            if(i<=0){
                result.setSuccess(false);
                result.setResultCode("info.category.delete.error");
            }else{
                localHelpCategoryManager.getCategoryMap().remove(helpCategory.getCategoryId());
            }
        } catch (Exception e) {
            log.error("Method:deleteCategory categoryId:"+helpCategory.getCategoryId()+"------>"+e.getMessage());
            result.setSuccess(false);
            result.setResultCode("info.category.delete.error");
        }
        return result;
    }

    //    public Result findCategoryFront() {
//        Result result=new Result();
//        List<HelpCategory> list= null;
//        try {
//            list = this.helpCategoryManager.findCategoryFront();
//            result.setSuccess(true);
//            if(list.size()>0){
//            if(list.size()>0){
//            result.addDefaultModel("categorys",list);
//            }else{
//                result.setResultCode("info.load.error");
//            }
//        } catch (Exception e) {
//            log.error("Method:findCategoryFront--->"+e.getMessage());
//        }
//        return result;
//    }
}
