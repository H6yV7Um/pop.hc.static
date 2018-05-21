package com.jd.help.customer.web;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jd.help.SysLoginContext;
import com.jd.passport.checkLogin.DotnetAuthenticationUtil;
import com.jd.passport.checkLogin.FormsAuthenticationTicket;
import com.jd.pop.module.utils.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import com.jd.common.struts.context.LoginContext;
import com.jd.common.struts.interceptor.JdInterceptor;
import com.jd.common.web.cookie.CookieUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import org.springframework.web.util.WebUtils;

public class DotnetTicketLoginContextInterceptor2 extends JdInterceptor {
        private static final Log log = LogFactory.getLog(DotnetTicketLoginContextInterceptor2.class);
	    /**
	     * 读取cookie
	     */
	    protected CookieUtils cookieUtils;
	    /**
	     * dotnet cookie的解密key
	     */
	    protected String dotnetAuthenticationKey;
	    /**
	     * 自己的login cookie
	     */
	    protected String loginCookieKey;

        protected String ssoCookieName;
	
	public String intercept(ActionInvocation invocation) throws Exception {
        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
        String cookieStr = getCookie(request);

        if (cookieStr == null) {
            removeLoginContext();
            SysLoginContext.remove();
        } else {
            FormsAuthenticationTicket ticket = DotnetAuthenticationUtil.getFormsAuthenticationTicket(cookieStr, dotnetAuthenticationKey);
//            log.info("ticket is -----" + JsonUtils.toJson(ticket));
            LoginContext loginContext = LoginContext.getLoginContext();
//            log.info("loginContext-1---" + JsonUtils.toJson(loginContext));
            if (loginContext == null) {
                loginContext = getLoginContextFromTicket(ticket);
                cookieUtils.setCookie(response, loginCookieKey, URLEncoder.encode(loginContext.toCookieValue(),"GBK"));
                setLoginContext(loginContext);
            } else if(!ticket.getUsername().equals(loginContext.getPin())){
                loginContextChanged(ticket, loginContext);
                loginContext.setPin(ticket.getUsername());
                cookieUtils.setCookie(response, loginCookieKey,URLEncoder.encode(loginContext.toCookieValue(),"GBK"));
                setLoginContext(loginContext);
            }
            log.info("loginContext-2---" + JsonUtils.toJson(loginContext));
            SysLoginContext.setUserPin(loginContext.getPin());
//            log.info("SysLoginContext----" + JsonUtils.toJson(SysLoginContext.context.get()));
        }

        return invocation.invoke();
    }

    protected String getCookie(HttpServletRequest request) {
        // 读取passport cookie
        Cookie passportCookie = WebUtils.getCookie(request, ssoCookieName);
        if (passportCookie == null || passportCookie.getValue().equals("invalid")) {
            return null;
        }
        return passportCookie.getValue();
    }
	
	/**
     * 表示用户已经发生改变。logincontext的和tickit中的对应不上了
     * @param ticket
     * @param loginContext
     */
    protected void loginContextChanged(FormsAuthenticationTicket ticket, LoginContext loginContext) {

    }

    /**
     * 预留。表示没有logincontext，从ticket中创建
     * @param ticket
     */
    protected void newLoginContext(FormsAuthenticationTicket ticket) {

    }

    /**
     * 将新构造的logincontext放入actioncontext
     * 调用这个方法可能因为有ticket但是没有logincontext。有可能是新用户从passport登录过来
     * @param loginContext
     */
    protected void setLoginContext(LoginContext loginContext) {
        LoginContext.setLoginContext(loginContext);
    }

    /**
     * 从dotnet 的 ticket中重构出logincontext
     *
     * @param ticket
     * @return
     */
    protected LoginContext getLoginContextFromTicket(FormsAuthenticationTicket ticket) throws Exception{
        LoginContext loginContext = new LoginContext();
        loginContext.setPin(ticket.getUsername());
        return loginContext;
    }

    /**
     * 称去登录内容
     */
    protected void removeLoginContext() {
        LoginContext.remove();
    }


    /**
     * 把dotnet ticket放入actioncontext的key
     *
     * @return key
     */
    protected String getDotnetTicketKey() {
        return LoginContext.HTTP_DOTNET_LOGIN_TICKET_CONTEXT;
    }

    public void setCookieUtils(CookieUtils cookieUtils) {
        this.cookieUtils = cookieUtils;
    }

    public void setDotnetAuthenticationKey(String dotnetAuthenticationKey) {
        this.dotnetAuthenticationKey = dotnetAuthenticationKey;
    }

    public void setLoginCookieKey(String loginCookieKey) {
        this.loginCookieKey = loginCookieKey;
    }

    public String getSsoCookieName() {
        return ssoCookieName;
    }

    public void setSsoCookieName(String ssoCookieName) {
        this.ssoCookieName = ssoCookieName;
    }
}
