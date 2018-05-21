package com.jd.help.center.admin.navigate;

import com.jd.common.struts.action.BaseAction;
import com.jd.common.util.StringUtils;
import com.jd.common.web.result.Result;
import com.jd.help.center.domain.HelpCenterHeadInfo;
import com.jd.help.center.domain.HelpCenterLeftNavigate;
import com.jd.help.center.domain.constants.UMPConstants;
import com.jd.help.center.service.navigate.LeftNavigateService;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * Created with IntelliJ IDEA.
 * User: xuxianjun
 * Date: 13-7-24
 * Time: 下午4:43
 * To change this template use File | Settings | File Templates.
 */
public class HelpNavigateAction extends BaseAction {

    private LeftNavigateService leftNavigateService;

    private String name;

    public String initNavigate() {
        CallerInfo info = Profiler.registerInfo(UMPConstants.UMP_HELP_CENTER_NAVIGATE, UMPConstants.enableHeart, UMPConstants.enableTP);
        if (StringUtils.isBlank(name)) {
            name = "help";
        }
        HelpCenterLeftNavigate navigate = leftNavigateService.getLeftNavigate(name);
        ValueStack context = ActionContext.getContext().getValueStack();

        HelpCenterHeadInfo head = new HelpCenterHeadInfo();
        head.setParamName(name);
        if (navigate == null) {
            head.setReturnCode("error");
            head.setReturnMsg("请求参数出错，没有系统导航信息");
        } else {
            head.setReturnCode("success");
        }

        context.set("head",head);
//        context.set("navigate2","aaaa");
        context.set("navigate", navigate);
        Profiler.registerInfoEnd(info);
        return "ajax";
    }


    public LeftNavigateService getLeftNavigateService() {
        return leftNavigateService;
    }

    public void setLeftNavigateService(LeftNavigateService leftNavigateService) {
        this.leftNavigateService = leftNavigateService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
