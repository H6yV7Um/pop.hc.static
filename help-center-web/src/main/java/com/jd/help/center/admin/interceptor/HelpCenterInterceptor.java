package com.jd.help.center.admin.interceptor;

import com.jd.common.struts.interceptor.StrutsAuthorizationInterceptor;
import com.jd.common.util.StringUtils;
import com.jd.common.web.LoginContext;
import com.jd.jsf.gd.util.JsonUtils;
import com.jd.uim.annotation.Authorization;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * Created by lipengfei5 on 2016/8/31.
 */
public class HelpCenterInterceptor extends StrutsAuthorizationInterceptor {
    private static final Log log = LogFactory.getLog(HelpCenterInterceptor.class);
    private List<String> specialUsers;
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        log.error("specialUsers:"+JsonUtils.toJSONString(specialUsers));
        log.error("erpPin:"+LoginContext.getLoginContext().getPin());
        if(CollectionUtils.isEmpty(specialUsers) || !specialUsers.contains(LoginContext.getLoginContext().getPin())){
            return this.doIntercept(invocation);
        }else{
            return invocation.invoke();
        }
    }
    public String doIntercept(ActionInvocation invocation) throws Exception{
        long startTime = System.currentTimeMillis(  );
        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest)actionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
        HttpServletResponse response = (HttpServletResponse)actionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");

        String returnStr = "";
        try {
            String methodName;
            try {
                Object e = invocation.getAction();
                methodName = invocation.getProxy().getMethod();
                if(methodName == null) {
                    String method1 = "error";
                    return method1;
                }
                Method method = e.getClass().getMethod(methodName, new Class[0]);
                Authorization annotation = (Authorization)method.getAnnotation(Authorization.class);
                if(annotation == null) {
                    return invocation.invoke();
                }

                String code = annotation.value();
                String username;
                if(StringUtils.isBlank(code)) {
                    username = invocation.invoke();
                    return username;
                }

                username = this.getUsername();
                if(username == null) {
                    return invocation.invoke();
                }

                if(this.hrmPrivilegeHelper.hasHrmPrivilege(username, code)) {
                    returnStr = invocation.invoke();
                    return returnStr;
                }
                invocation.getStack().set("resources",code.split(","));
                returnStr = "illegal";
            } catch (Exception var17) {
                log.error("权限拦截异常:" + var17.getMessage());
                response.setContentType("text/html;charset=UTF-8");
                response.sendError(401);
                methodName = null;
                return methodName;
            }
        } finally {
            if(log.isDebugEnabled()) {
                log.debug("权限拦截耗时:" + (System.currentTimeMillis() - startTime));
            }

        }

        return returnStr;
    }
    public void setSpecialUsers(List<String> specialUsers) {
        this.specialUsers = specialUsers;
    }

}
