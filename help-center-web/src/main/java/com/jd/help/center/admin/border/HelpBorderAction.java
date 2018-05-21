package com.jd.help.center.admin.border;

import com.jd.common.util.StringUtils;
import com.jd.common.web.result.Result;
import com.jd.help.center.admin.base.HelpBaseAction;
import com.jd.help.center.domain.border.BorderInfo;
import com.jd.help.center.manager.border.HelpBorderManager;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 12-10-10
 * Time: ÉÏÎç10:50
 * To change this template use File | Settings | File Templates.
 */
public class HelpBorderAction extends HelpBaseAction{
    private BorderInfo borderInfo;
    private HelpBorderManager helpBorderManager;


    public String index(){
        return "toIndex";
    }

    public String getAll(){
        Result result = new Result();
        List<BorderInfo> borderInfos = helpBorderManager.getBorderInfoByType(borderInfo.getType());
        result.addDefaultModel("borders",borderInfos);
        toVm(result);
        return "list";
    }

    public String insert(){
        Result result = new Result();
        if(StringUtils.isBlank(borderInfo.getType())){
            return "index";
        }
        result.addDefaultModel("borderInfo",borderInfo);
        toVm(result);
        return "insert";
    }

    public String doInsert(){
        addLog("insert border info ,key is "+borderInfo.getKey());
        helpBorderManager.insertBorderInfo(borderInfo);
        return "toList";
    }

    public String update(){
        Result result = new Result();
        BorderInfo borderInfoDB = helpBorderManager.getBorderInfoByKey(borderInfo.getKey());
        result.addDefaultModel("borderInfo",borderInfoDB);
        toVm(result);
        return "update";
    }

    public String doUpdate(){
        addLog("update border info ,key is "+borderInfo.getKey());
        helpBorderManager.updateBorderInfo(borderInfo);
        return "toList";
    }

    public String delete(){
        addLog("delete border info ,key is "+borderInfo.getKey());
        helpBorderManager.deleteBorder(borderInfo.getKey());
        return "toList";
    }


    public void setHelpBorderManager(HelpBorderManager helpBorderManager) {
        this.helpBorderManager = helpBorderManager;
    }


    public BorderInfo getBorderInfo() {
        return borderInfo;
    }

    public void setBorderInfo(BorderInfo borderInfo) {
        this.borderInfo = borderInfo;
    }
}
