package com.jd.help.center.web.util;




import com.jd.common.struts.context.LoginContext;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lipengfei5 on 2016/9/2.
 */
public class ShopWebHelper {
    /**
     * 获取当前登录人的ID
     * @return long
     */
    public static final long getUserId() {
        return LoginContext.getLoginContext().getUserId();
    }
    /**
     * 获取当前登录登录人的登入账号
     * @return long
     */
    public static final String getPin() {
        return LoginContext.getLoginContext().getPin();
    }
    /**
     *  获取当前登录登录人的显示名称
     */
    public static final String getNick() {
        return LoginContext.getLoginContext().getNick();
    }
    /**
     * 获取当前登录登录人登录信息
     * @return
     */
    public static final Map<String, Object> getLoginInfo() {
        Map<String, Object> loginInfo = new HashMap<String, Object>();
        loginInfo.put("userId", getUserId());
        loginInfo.put("pin", getPin());
        loginInfo.put("nick", getNick());
        return loginInfo;
    }

    public static final String getIpAddress(){
        HttpServletRequest request = (HttpServletRequest)  ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
        String ip=request.getRemoteAddr();
        if (request.getHeader("x-forwarded-for") != null
                && !"unknown".equalsIgnoreCase(request.getHeader("x-forwarded-for"))) {
            ip=ip+","+request.getHeader("x-forwarded-for");
        }
        return ip;
    }
}
