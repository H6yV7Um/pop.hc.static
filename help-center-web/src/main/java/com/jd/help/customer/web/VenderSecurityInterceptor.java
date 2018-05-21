package com.jd.help.customer.web;

import com.jd.common.struts.context.LoginContext;
import com.jd.common.struts.interceptor.JdInterceptor;
import com.jd.common.web.url.JdUrl;
import com.jd.common.web.url.JdUrlUtils;
import com.jd.help.SysLoginContext;
import com.jd.help.center.web.util.ShopWebHelper;
import com.jd.pop.module.utils.JsonUtils;
import com.jd.pop.vender.center.domain.auth.LoginResult;
import com.jd.pop.vender.center.domain.seller.constants.SellerConstants;
import com.jd.pop.vender.center.service.auth.AuthSafService;
import com.jd.pop.vender.center.service.auth.dto.AuthLoginExt;
import com.jd.pop.vender.center.service.auth.dto.AuthLoginExtResult;
import com.jd.pop.vender.center.service.auth.dto.AuthRequest;
import com.jd.pop.vender.usercenter.domain.LoginAccountConstants;
import com.jd.ump.profiler.CallerInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Enumeration;

/**
 * Created by lipengfei5 on 2016/11/2.
 */
public class VenderSecurityInterceptor extends JdInterceptor {
    private Log log= LogFactory.getLog(VenderSecurityInterceptor.class);
    protected JdUrlUtils jdUrlUtils;
    protected String homeModule = "homeModule";

    @Resource
    AuthSafService authSafServiceOutOfUsercenter;
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext actionContext = actionInvocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
        if(LoginContext.getLoginContext() == null){
            return actionInvocation.invoke();
        }
        SysLoginContext.remove();
        String userPin = ShopWebHelper.getPin();
        initVenderInfo(userPin);
        SysLoginContext.setUserPin(userPin);
        log.info("SysLoginContext----" + JsonUtils.toJson(SysLoginContext.context.get()));
        return actionInvocation.invoke();
    }

    private void initVenderInfo(String pin) {

        LoginResult loginResult = null;
        CallerInfo callerInfo = com.jd.ump.profiler.proxy.Profiler.registerInfo(LoginAccountConstants.UMP_KEY_VENDER_LOOKUP_USER, false, true);
        try {
            loginResult = newGetLoginResult(pin);
        } catch (Exception e) {
            log.error("vender.help.customer.orderService.getOrderInfo",e);
            com.jd.ump.profiler.proxy.Profiler.functionError(callerInfo);
        } finally {
            com.jd.ump.profiler.proxy.Profiler.registerInfoEnd(callerInfo);
        }

//        SysLoginContext.setNick(ShopWebHelper.getNick());
//        SysLoginContext.setUserId(ShopWebHelper.getUserId());
        if (loginResult == null || loginResult.getUserName() == null) {
            return;
        } else if (loginResult.getStatus() == LoginAccountConstants.LOGIN_ACCOUNT_STATUS_STOP) {
            return;
        } else if (loginResult.getVenderStatus() != SellerConstants.SELLER_STATUS_HAS_STRAT) {
            return;
        } else if (loginResult.getShopStatus() == 3) {
            return;
        }
        CallerInfo info = com.jd.ump.profiler.proxy.Profiler.registerInfo(LoginAccountConstants.UMP_KEY_VENDER_LONG, true, true);
        //SysLoginContext.setUserPin(loginResult.getPin());
        SysLoginContext.setVenderId(loginResult.getUserId());
        try {
            SysLoginContext.setNick(ShopWebHelper.getNick());
            SysLoginContext.setUserId(ShopWebHelper.getUserId());
            SysLoginContext.setShopName(URLDecoder.decode(loginResult.getShopName(), "GBK"));
            SysLoginContext.setCompanyName(URLDecoder.decode(loginResult.getCompanyName(), "GBK"));
            SysLoginContext.setUserPin(URLDecoder.decode(pin, "GBK"));
        } catch (Exception e) {
            log.error("LoginAccountConstants.UMP_KEY_VENDER_LONG",e);
            e.printStackTrace();
        }
        //SysLoginContext.setCompanyName(loginResult.getCompanyName());
        SysLoginContext.setPreLoginTime(loginResult.getPreLoginTime());
        SysLoginContext.setLoginTime(loginResult.getLoginTime());
        log.error("vender_error___"+SysLoginContext.getVenderId()+"_____"+SysLoginContext.getUserPin());
        return ;
    }

    private LoginResult newGetLoginResult(String pin) {
        AuthLoginExtResult authLoginResult = authSafServiceOutOfUsercenter.lookupUserAndLoginTime(pin, new AuthRequest("index.action"), null, null);
        LoginResult loginResult = new LoginResult();
        if (authLoginResult != null && authLoginResult.isSuccess()) {
            AuthLoginExt authLogin = authLoginResult.getAuthLoginExt();
            loginResult.setUserName(authLogin.getPin());
            loginResult.setUserId(authLogin.getVenderId());
            loginResult.setCompanyId(authLogin.getCompanyId());
            loginResult.setShopId(authLogin.getShopId());
            loginResult.setShopName(authLogin.getShopName());
            loginResult.setCompanyName(authLogin.getCompanyName());
            loginResult.setStatus(authLogin.getStatus());
            loginResult.setVenderStatus(authLogin.getVenderStatus());
            loginResult.setShopStatus(authLogin.getShopStatus());
            loginResult.setPreLoginTime(authLogin.getPreLoginTime());
            loginResult.setLoginTime(authLogin.getLoginTime());
            loginResult.setColType(authLogin.getColType());
            loginResult.setUserType(authLogin.getUserType());
        }
        return loginResult;
    }

    protected String getCurrentUrl(HttpServletRequest request) {
        JdUrl venderUrl = jdUrlUtils.getJdUrl(homeModule);
        venderUrl.getTarget(request.getRequestURI());
        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = (String) parameterNames.nextElement();
            venderUrl.addQueryData(key, request.getParameterValues(key));
        }
        return venderUrl.toString();
    }

    public void setJdUrlUtils(JdUrlUtils jdUrlUtils) {
        this.jdUrlUtils = jdUrlUtils;
    }

    public void setHomeModule(String homeModule) {
        this.homeModule = homeModule;
    }
}
