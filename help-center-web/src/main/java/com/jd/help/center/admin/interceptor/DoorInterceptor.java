package com.jd.help.center.admin.interceptor;


import com.jd.help.center.domain.base.MD5Util;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletRequest;


/**
 * @author GaoFei
 * Date: 2010-10-27
 * Time: 17:14:25
 */
public class DoorInterceptor extends MethodFilterInterceptor{
    private static final Log log = LogFactory.getLog(DoorInterceptor.class);
    private static final String PWD_EXEC= "87f9fe0e2dababcfbf859ae7bb038a0c";
    
    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
        if(!ckeckIdentitySafe(invocation)){ 
           return "login_error"; 
        }
        return invocation.invoke();
    }

    private boolean ckeckIdentitySafe(ActionInvocation invocation){
         ActionContext actionContext = invocation.getInvocationContext();
         HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
         String formValue = request.getParameter("pwd_exec");
         if(StringUtils.isNotBlank(formValue) && PWD_EXEC.equals(MD5Util.md5Hex(formValue))){
             return true;
         }
         return false;
    }

}
