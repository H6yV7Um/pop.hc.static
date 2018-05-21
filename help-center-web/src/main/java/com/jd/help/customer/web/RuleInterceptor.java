package com.jd.help.customer.web;

import com.jd.common.struts.interceptor.JdInterceptor;
import com.jd.help.SysLoginContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletRequest;


/**
 * 不要使用这个拦截器，是错误的
 *
 * @author wangyu1099
 * @date 2018/04/23
 */
@Deprecated
public class RuleInterceptor extends JdInterceptor {

    private String domainName;
    private String returnUrl;

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {

        String nameSpace = actionInvocation.getProxy().getNamespace();
        // 其他请求直接放行，只拦截/rule/*
        if (!"/rule".startsWith(nameSpace)) {
            actionInvocation.invoke();
        }
        // 未登录
        if (SysLoginContext.getVenderId() == null) {
            String actionName = actionInvocation.getProxy().getActionName();
            returnUrl = domainName + nameSpace + "/" + actionName + ".action";
//            setReturnUrl(returnUrl);
            ServletActionContext.getResponse().sendRedirect(returnUrl);
            return "login";
        }
        int venderIdLength = SysLoginContext.getVenderId().toString().length();
        // 登陆后验证权限
        // 自营商家能使用的功能以New结尾
        String methodName = actionInvocation.getProxy().getMethod();
        if (venderIdLength == 10 && methodName.endsWith("New")) {
            actionInvocation.invoke();
        } else if (venderIdLength != 10 && !methodName.endsWith("New")) {
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
//        ActionContext actionContext = ActionContext.getContext();
//        HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
//        ValueStack valueStack = actionContext.getValueStack();
//        valueStack.set("returnUrl", returnUrl);
        this.returnUrl = returnUrl;
    }
}
