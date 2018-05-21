package com.jd.help.customer.web;

import com.jd.common.struts.context.LoginContext;
import com.jd.common.struts.interceptor.JdInterceptor;
import com.jd.help.SysLoginContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import org.apache.struts2.StrutsStatics;
import org.omg.PortableInterceptor.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lipengfei5 on 2016/11/9.
 */
public class LogoutInterceptor extends JdInterceptor {
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext actionContext = actionInvocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
        //SysLoginContext.remove();
        return actionInvocation.invoke();
    }
}
