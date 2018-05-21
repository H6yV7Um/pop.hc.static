package com.jd.help.customer.web;

import com.jd.common.struts.context.LoginContext;
import com.jd.common.struts.interceptor.dotnet.FormsAuthenticationTicket;
import com.jd.common.web.cookie.CookieUtils;
import com.jd.help.rpc.UserTagProxy;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Calendar;

/**
 * “联系我们”的拦截器，确定“联系我们”中展示哪些内容
 *
 * @author laichendong
 * @since 2014/12/31 13:35
 */
public class ContactUsInterceptor implements Interceptor {
    private CookieUtils cookieUtils;
    private UserTagProxy userTagProxy;

    /**
     * 1、检查是否存在_ctu_的cookie，如果存在，则不管了。不存在，则继续
     * 2、从actionContext中检查登录信息，如果没有登录，cookie中设置 needLogin；如果登录了，则继续
     * 3、根据pin 获取用户标签，如果是A+ 或D类用户，则设置cookie； 如果不是，则继续
     * 4、根据pin 获取用户等级，钻石和非钻石，设置不同的cookie
     *
     * @param actionInvocation
     * @return
     * @throws Exception
     */
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext actionContext = actionInvocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
        String cookieValue = cookieUtils.getCookieValue(request, "_ctu_");
        if (StringUtils.isNotBlank(cookieValue) && !"needLogin".equals(cookieValue) && isLogin(actionContext)) {
            // 如果cookie中有值  且不是needLogin 且用户是已经登录状态，则直接过。不用再调用外部接口确定cookie值了
            return actionInvocation.invoke();
        }
        cookieValue = refreshCookieValue(actionContext);
        cookieValue = URLEncoder.encode(cookieValue, "gbk"); // 防止tomcat在cookie值两端加上双引号
        cookieUtils.setCookie(response, "_ctu_", cookieValue);
        return actionInvocation.invoke();
    }

    public String refreshCookieValue(ActionContext actionContext) {
        String cookieValue;
        if (!isLogin(actionContext)) {
            // 没登录
            cookieValue = "needLogin";
        } else {
           /* String userTag = tagOfUser(actionContext);
            if ("A".equals(userTag)) {
                // A+用户
                cookieValue = isBusyMonth() ? "appointment,sellerLine" : "appointment,diamondLine,sellerLine";
            } else if ("D".equals(userTag)) {
                // D类用户
                cookieValue = isBusyMonth() ? "busy" : "appointment,sellerLine";
            } else {
                if (isDiamondLevelUser(actionContext)) {
                    // 钻石用户
                    cookieValue = isBusyMonth() ? "appointment,sellerLine" : "appointment,diamondLine,sellerLine";
                } else {
                    // 普通用户
                    cookieValue = isBusyMonth() ? "busy" : "appointment,sellerLine";
                }
            }*/
        	cookieValue = "appointment";
        }
        return cookieValue;
    }

//    private boolean isDiamondLevelUser(ActionContext actionContext) {
//        FormsAuthenticationTicket ticket = (FormsAuthenticationTicket) actionContext.get(LoginContext.HTTP_DOTNET_LOGIN_TICKET_CONTEXT);
//        if (ticket != null) {
//            return userLevelProxy.isDiamondLevelUser(ticket.getUsername());
//        }
//        return false;
//    }

    /**
     * 获取用户的tag， 如果不是A+ 或D　返回null
     *
     * @return
     */
    private String tagOfUser(ActionContext actionContext) {
        FormsAuthenticationTicket ticket = (FormsAuthenticationTicket) actionContext.get(LoginContext.HTTP_DOTNET_LOGIN_TICKET_CONTEXT);
        if (ticket != null) {
            return userTagProxy.tagOfUser(ticket.getUsername());
        }
        return null;
    }

    /**
     * 判断当前是否登录
     *
     * @return
     */
    public boolean isLogin(ActionContext actionContext) {
        FormsAuthenticationTicket ticket = (FormsAuthenticationTicket) actionContext.get(LoginContext.HTTP_DOTNET_LOGIN_TICKET_CONTEXT);
        return ticket != null;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init() {

    }


    /**
     * 判断当前是否为客服忙碌的月份
     * 6月和11月定义为忙碌月份
     *
     * @return
     */
    public boolean isBusyMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) == Calendar.JUNE || c.get(Calendar.MONTH) == Calendar.NOVEMBER;
    }

    public void setCookieUtils(CookieUtils cookieUtils) {
        this.cookieUtils = cookieUtils;
    }

    public void setUserTagProxy(UserTagProxy userTagProxy) {
        this.userTagProxy = userTagProxy;
    }

}
