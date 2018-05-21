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
 * ����ϵ���ǡ�����������ȷ������ϵ���ǡ���չʾ��Щ����
 *
 * @author laichendong
 * @since 2014/12/31 13:35
 */
public class ContactUsInterceptor implements Interceptor {
    private CookieUtils cookieUtils;
    private UserTagProxy userTagProxy;

    /**
     * 1������Ƿ����_ctu_��cookie��������ڣ��򲻹��ˡ������ڣ������
     * 2����actionContext�м���¼��Ϣ�����û�е�¼��cookie������ needLogin�������¼�ˣ������
     * 3������pin ��ȡ�û���ǩ�������A+ ��D���û���������cookie�� ������ǣ������
     * 4������pin ��ȡ�û��ȼ�����ʯ�ͷ���ʯ�����ò�ͬ��cookie
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
            // ���cookie����ֵ  �Ҳ���needLogin ���û����Ѿ���¼״̬����ֱ�ӹ��������ٵ����ⲿ�ӿ�ȷ��cookieֵ��
            return actionInvocation.invoke();
        }
        cookieValue = refreshCookieValue(actionContext);
        cookieValue = URLEncoder.encode(cookieValue, "gbk"); // ��ֹtomcat��cookieֵ���˼���˫����
        cookieUtils.setCookie(response, "_ctu_", cookieValue);
        return actionInvocation.invoke();
    }

    public String refreshCookieValue(ActionContext actionContext) {
        String cookieValue;
        if (!isLogin(actionContext)) {
            // û��¼
            cookieValue = "needLogin";
        } else {
           /* String userTag = tagOfUser(actionContext);
            if ("A".equals(userTag)) {
                // A+�û�
                cookieValue = isBusyMonth() ? "appointment,sellerLine" : "appointment,diamondLine,sellerLine";
            } else if ("D".equals(userTag)) {
                // D���û�
                cookieValue = isBusyMonth() ? "busy" : "appointment,sellerLine";
            } else {
                if (isDiamondLevelUser(actionContext)) {
                    // ��ʯ�û�
                    cookieValue = isBusyMonth() ? "appointment,sellerLine" : "appointment,diamondLine,sellerLine";
                } else {
                    // ��ͨ�û�
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
     * ��ȡ�û���tag�� �������A+ ��D������null
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
     * �жϵ�ǰ�Ƿ��¼
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
     * �жϵ�ǰ�Ƿ�Ϊ�ͷ�æµ���·�
     * 6�º�11�¶���Ϊæµ�·�
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
