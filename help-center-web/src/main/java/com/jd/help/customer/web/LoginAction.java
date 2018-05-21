package com.jd.help.customer.web;

import com.jd.help.HelpBaseAction;
import com.jd.help.SysLoginContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lipengfei5 on 2016/11/9.
 */
public class LoginAction extends HelpBaseAction{
    private static final Log log = LogFactory.getLog(LoginAction.class);

    private final String loginUrl="https://passport.jd.com/new/login.aspx?ReturnUrl=";

    private String toDirectUrl;
    public void login(){
        try {
            ServletActionContext.getResponse().sendRedirect(loginUrl + getToDirectUrl());
        } catch (IOException e) {
            log.info("Ìø×ªµÇÂ¼Ò³´íÎó", e);
        }
    }
    public void logout(){
//        SysLoginContext.setNick("");
//        SysLoginContext.setUserId(null);
//        SysLoginContext.setShopName("");
//        SysLoginContext.setCompanyName("");
//        SysLoginContext.setUserPin("");
        SysLoginContext.remove();
        toLoginPage(ServletActionContext.getResponse());
    }

    public String getToDirectUrl() {
        return toDirectUrl;
    }

    public void setToDirectUrl(String toDirectUrl) {
        this.toDirectUrl = toDirectUrl;
    }

    private void toLoginPage(HttpServletResponse response) {
        try {
            Cookie thor = new Cookie("thor","invalid");
            thor.setDomain("jd.com");
            thor.setMaxAge(-1);
            thor.setPath("/");
            response.addCookie(thor);
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("expires", -1);
            response.sendRedirect(loginUrl + getToDirectUrl());
        } catch (IOException e) {
            log.info("Ìø×ªµÇÂ¼Ò³´íÎó", e);
        }
    }
}
