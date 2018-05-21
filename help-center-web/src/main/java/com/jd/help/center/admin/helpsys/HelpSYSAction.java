package com.jd.help.center.admin.helpsys;

import com.jd.common.struts.action.BaseAction;
import com.jd.common.web.result.Result;
import com.jd.help.center.admin.base.HelpBaseAction;
import com.jd.help.center.domain.base.HelpCenterStringUtils;
import com.jd.help.center.domain.constants.HrmPurviewConstants;
import com.jd.help.center.domain.helpsys.HelpSYS;
import com.jd.help.center.service.helpsys.HelpSYSService;
import com.jd.uim.annotation.Authorization;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-8-3
 * Time: 18:15:52
 * To change this template use File | Settings | File Templates.
 */
public class HelpSYSAction extends HelpBaseAction {
    private static final Log log = LogFactory.getLog(HelpSYSAction.class);
    private HelpSYS helpSYS;
    private HelpSYSService helpSYSService;
    private Result result;

    public void setHelpSYSService(HelpSYSService helpSYSService) {
        this.helpSYSService = helpSYSService;
    }

    @Authorization(HrmPurviewConstants.HELP_CENTER_CATEGORY_LIST)
    public String findAll(){
        this.setResult(helpSYSService.findSYSAll());
        this.toVm(result);
        return SUCCESS;
    }

    public String insert(){
        return SUCCESS;
    }

    public void initCategory(){
       boolean flag = false;
       PrintWriter out = null;
        try {
            helpSYSService.initCategory();
            flag=true;
        } catch (Exception e) {
            log.error("HelpSYSAction-->initCategory error---->",e);
        }
        try{
          response.setContentType("text/html;charset=utf-8");
          out = response.getWriter();
          out.write(flag+"");
          out.flush();
       }catch (Exception e){
          log.error("Method--> ajaxAddTags error",e);
       }finally {
          if(out!=null){
              out.close();
          }
       }
    }

    @Authorization(HrmPurviewConstants.HELP_CENTER_CATEGORY_UPDATE)
    public String doInsert(){
        this.setResult(helpSYSService.insertSYS(helpSYS));
        this.toVm(result);
        if(result.getSuccess()){
            addLog("添加系统，系统名为-->"+helpSYS.getName());
            return "list";
        }
        return "toInsert";
    }

    @Authorization(HrmPurviewConstants.HELP_CENTER_CATEGORY_UPDATE)
    public String doUpdate(){
        this.setResult(this.helpSYSService.updatSYS(helpSYS));
        this.toVm(result);
        if(result.getSuccess()){
            addLog("修改系统表，系统名为-->"+helpSYS.getName()+"系统ID为-->"+helpSYS.getId());
            return "list";
        }
        return "toUpdate";
    }

    @Authorization(HrmPurviewConstants.HELP_CENTER_CATEGORY_UPDATE)
    public String updateStatus(){
        if(helpSYS.getStatus()==1){
            helpSYS.setStatus(0);
        }else{
            helpSYS.setStatus(1);
        }

        this.setResult(helpSYSService.updateSYSStatus(helpSYS));
        this.toVm(result);
        if(result.getSuccess()){
            addLog("修改系统状态，系统ID为-->"+helpSYS.getId()+"状态改为-->"+ HelpCenterStringUtils.convertStatusInAction(helpSYS.getStatus()));
            return "list";
        }
        return "toList";
    }

    public String update(){
        this.setResult(helpSYSService.getSYSById(helpSYS.getId()));
        this.toVm(result);
        return SUCCESS;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public HelpSYS getHelpSYS() {
        return helpSYS;
    }

    public void setHelpSYS(HelpSYS helpSYS) {
        this.helpSYS = helpSYS;
    }
}
