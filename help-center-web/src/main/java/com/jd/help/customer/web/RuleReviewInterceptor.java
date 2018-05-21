package com.jd.help.customer.web;

import com.jd.common.struts.interceptor.JdInterceptor;
import com.jd.help.SysLoginContext;
import com.jd.help.service.KkongService;
import com.opensymphony.xwork2.ActionInvocation;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by lipengfei5 on 2017/9/23.
 */
public class RuleReviewInterceptor extends JdInterceptor {
    @Resource
    private KkongService kkongService;
    private final String loginUrl="https://passport.jd.com/new/login.aspx?ReturnUrl=";
    private String domainName;
    private String returnUrl;
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        if(SysLoginContext.getVenderId() == null){
            String actionName = actionInvocation.getProxy().getActionName();
            String nameSpace = actionInvocation.getProxy().getNamespace();
            returnUrl = loginUrl+domainName+nameSpace+"/"+actionName;
//            ServletActionContext.getResponse().sendRedirect();
            return "login";
        }
        // 获取action后附带参数
        Map parameters = actionInvocation.getInvocationContext().getParameters();
        Long reviewFormId = (Long) parameters.get("reviewFormId");
        if(kkongService.hasReviewAuth(reviewFormId,SysLoginContext.getVenderId()+"")){
            actionInvocation.invoke();
        }
        return "illegal";
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
