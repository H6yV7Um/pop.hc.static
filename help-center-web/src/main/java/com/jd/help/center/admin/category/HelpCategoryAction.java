package com.jd.help.center.admin.category;

import com.jd.common.struts.action.BaseAction;
import com.jd.common.web.result.Result;
import com.jd.help.center.admin.base.HelpBaseAction;
import com.jd.help.center.domain.base.HelpCenterStringUtils;
import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.category.query.HelpCategoryQuery;
import com.jd.help.center.domain.constants.HrmPurviewConstants;
import com.jd.help.center.service.category.HelpCategoryService;
import com.jd.uim.annotation.Authorization;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-1
 * Time: 14:43:00
 * To change this template use File | Settings | File Templates.
 */
public class HelpCategoryAction extends HelpBaseAction {
    private int sysId;

    private HelpCategory helpCategory;

    private HelpCategoryQuery helpCategoryQuery;

    private HelpCategoryService helpCategoryService;

    private Result result;

    public String index(){
        return SUCCESS;
    }

    public String frame(){
        return SUCCESS;
    }

    public String  insert(){
        return SUCCESS;
    }

//    public String getFront(){
//        this.setResult(this.helpCategoryService.findCategoryFront());
//        this.toVm(result);
//        return "front";
//    }
    @Authorization(HrmPurviewConstants.HELP_CENTER_CATEGORY_UPDATE)
    public String doInsert(){
        helpCategory.setFid(sysId);
        this.setResult(helpCategoryService.insertCategory(helpCategory));
        this.toVm(result);
        if(result.getSuccess()){
            addLog("添加类目，类目名称为--->"+helpCategory.getName());
            return "list";
        }
        return "toInsert";
    }

    @Authorization(HrmPurviewConstants.HELP_CENTER_CATEGORY_LIST)
    public String findAll(){
        this.setResult(helpCategoryService.findCategoryAllById(sysId));
        this.toVm(result);
        return SUCCESS;
    }

    /**
     * 根据条件查询帮助分类信息
     * @return
     */
    @Authorization(HrmPurviewConstants.HELP_CENTER_CATEGORY_LIST)
    public String findHelpCategoryList(){
       try {
           if(helpCategoryQuery == null || helpCategoryQuery.getSysId() == null){
               addActionError(getText("system.error"));
           }else{
                sysId = helpCategoryQuery.getSysId().intValue();
                Result result  = helpCategoryService.findCategoryByQuery(helpCategoryQuery);
                this.toVm(result);
                return "toList";
           }

       }catch (Exception e){
           return ERROR;
       }

        return SUCCESS;
    }


    public String update(){
        this.setResult(helpCategoryService.getCategoryById(helpCategory.getCategoryId()));
        this.toVm(result);
        return SUCCESS;
    }


    @Authorization(HrmPurviewConstants.HELP_CENTER_CATEGORY_UPDATE)
    public String updateCategoryStatus(){
        if(helpCategory.getStatus()==1){
            helpCategory.setStatus(0);
        }else{
            helpCategory.setStatus(1);
        }
        this.setResult(helpCategoryService.updateCategoryStatus(helpCategory));
        this.toVm(result);
        if(result.getSuccess()){
            addLog("修改类目状态，类目ID为-->"+helpCategory.getCategoryId()+"状态改为-->"+ HelpCenterStringUtils.convertStatusInAction(helpCategory.getStatus()));
            return "list";
        }
        return "toList";
    }

    @Authorization(HrmPurviewConstants.HELP_CENTER_CATEGORY_UPDATE)
    public String doUpdate(){
        helpCategory.setFid(sysId);
        this.setResult(helpCategoryService.updateCategory(helpCategory));
        this.toVm(result);
        if(result.getSuccess()){
            addLog("修改类目，类目名为-->"+helpCategory.getName()+"系统ID为-->"+helpCategory.getCategoryId());
            return "list";
        }
        return "toUpdate";
    }


    @Authorization(HrmPurviewConstants.HELP_CENTER_CATEGORY_UPDATE)
    public String delete(){
        setResult(helpCategoryService.deleteCategory(helpCategory));
        toVm(result);
        if(result.getSuccess()){
            addLog("删除类目，类目名为-->"+helpCategory.getName()+"categoryID为-->"+helpCategory.getCategoryId());
            return "list";
        }
        return "toList";
    }

    public void setHelpCategoryService(HelpCategoryService helpCategoryService) {
        this.helpCategoryService = helpCategoryService;
    }

    public int getSysId() {
        return sysId;
    }

    public void setSysId(int sysId) {
        this.sysId = sysId;
    }

    public HelpCategory getHelpCategory() {
        return helpCategory;
    }

    public void setHelpCategory(HelpCategory helpCategory) {
        this.helpCategory = helpCategory;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public HelpCategoryQuery getHelpCategoryQuery() {
        return helpCategoryQuery;
    }

    public void setHelpCategoryQuery(HelpCategoryQuery helpCategoryQuery) {
        this.helpCategoryQuery = helpCategoryQuery;
    }
}
