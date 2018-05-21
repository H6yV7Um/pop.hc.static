package com.jd.help.center.service.helpsys.impl;

import com.jd.common.web.result.Result;
import com.jd.help.center.domain.helpsys.HelpSYS;
import com.jd.help.center.manager.category.LocalHelpCategoryManager;
import com.jd.help.center.manager.helpsys.HelpSYSManager;
import com.jd.help.center.service.helpsys.HelpSYSService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-8-3
 * Time: 17:52:50
 * To change this template use File | Settings | File Templates.
 */
public class HelpSYSServiceImpl  implements HelpSYSService {
    private static final Log log= LogFactory.getLog(HelpSYSServiceImpl.class);
    private HelpSYSManager helpSYSManager;
    private LocalHelpCategoryManager localHelpCategoryManager;

    public void setHelpSYSManager(HelpSYSManager helpSYSManager) {
        this.helpSYSManager = helpSYSManager;
    }

    public void setLocalHelpCategoryManager(LocalHelpCategoryManager localHelpCategoryManager) {
        this.localHelpCategoryManager = localHelpCategoryManager;
    }

    public Result findSYSAll() {
        Result result=new Result();
        try {
            List<HelpSYS> list=helpSYSManager.findSYSAll();
            result.setSuccess(true);
            if(list.size()>0){
                result.addDefaultModel("helpSYSs",list);
            }else{
                result.setResultCode("info.sys.load.error");
            }
        } catch (Exception e) {
            log.error("Method:findSYSAll----------->"+e.getMessage());
            result.setResultCode("info.sys.load.error");
            result.setSuccess(false);
        }
        return result;
    }

    public Result getSYSById(int id) {
        Result result=new Result();
        try {
            HelpSYS helpSYS=localHelpCategoryManager.getHelpSYSById(id);
            if(helpSYS!=null){
                result.setSuccess(true);
                result.addDefaultModel("helpsys",helpSYS);
            }else{
                result.setSuccess(false);
                result.setResultCode("info.sys.load.error");
            }
        } catch (Exception e) {
            log.error("Method:findSYSAll----------->"+e.getMessage());
            result.setResultCode("info.sys.load.error");
            result.setSuccess(false);
        }
        return result;
    }

    public Result updatSYS(HelpSYS helpSYS) {
        Result result=new Result();
        try {
            int i=helpSYSManager.updateSYS(helpSYS);
            result.setSuccess(true);
            if(i<=0){
                result.setSuccess(false);
                result.setResultCode("info.sys.load.error");
            }
            //将修改后的值放到内存中
            PropertyUtils.copyProperties(localHelpCategoryManager.getHelpSYSById(helpSYS.getId()),helpSYS);
        } catch (Exception e) {
            log.error("Method:findSYSAll----------->"+e.getMessage());
            result.setResultCode("info.sys.load.error");
            result.setSuccess(false);
        }
        return result;
    }

    public Result updateSYSStatus(HelpSYS helpSYS) {
        Result result=new Result();
        try {
            int i=helpSYSManager.updateSYSStatus(helpSYS);
            result.setSuccess(true);
            if(i<=0){
                result.setSuccess(false);
                result.setResultCode("info.sys.load.error");
            }
            localHelpCategoryManager.getHelpSYSById(helpSYS.getId()).setStatus(helpSYS.getStatus());
        } catch (Exception e) {
            log.error("Method:findSYSAll----------->"+e.getMessage());
            result.setResultCode("info.sys.load.error");
            result.setSuccess(false);
        }
        return result;
    }

    public Result insertSYS(HelpSYS helpSYS) {
        Result result=new Result();
        try {
            int i=helpSYSManager.insert(helpSYS);
            result.setSuccess(true);
            if(i<=0){
                result.setSuccess(false);
                result.setResultCode("info.sys.load.error");
            }else{
                helpSYS.setId(i);
                localHelpCategoryManager.getAllHelpSYSList().add(helpSYS);
                localHelpCategoryManager.getSysMap().put(helpSYS.getId(),helpSYS);
            }
        } catch (Exception e) {
            log.error("Method:findSYSAll----------->"+e.getMessage());
            result.setResultCode("info.sys.load.error");
            result.setSuccess(false);
        }
        return result;
    }

    public void initCategory() {
        localHelpCategoryManager.initLocalHelpCategory();
    }
}
