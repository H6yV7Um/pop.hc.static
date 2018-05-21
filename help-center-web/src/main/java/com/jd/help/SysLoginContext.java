package com.jd.help;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 登陆信息等内容
 * Created by lipengfei on 16/10/26.
 */
public class SysLoginContext {

    public static final ThreadLocal<LoginUserInfo> context = new ThreadLocal<LoginUserInfo>();

    private SysLoginContext() {
    }

    /**
     * 登陆用户Pin
     *
     * @return
     */
    public static String getUserPin() {
        try {
            return context.get().getPin();
        } catch (Exception e) {
            return null;
        }
    }
    public static String getUserNick(){
        try {
            return context.get().getNick();
        }catch (Exception e){
            return null;
        }
    }

    public static Long getUserId(){
        try {
            return context.get().getUserId();
        } catch (Exception e){
            return null;
        }
    }

    /**
     * 商家ID
     *
     * @return
     */
    public static Long getVenderId() {
        try {
            return context.get().getVenderId();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getShopName() {
        try {
            return context.get().getShopName();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getCompanyName() {
        try {
            return context.get().getCompanyName();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getPreLoginTime() {
        try {
            return DateFormatUtils.format(context.get().getPreLoginTime(), "yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            return null;
        }
    }

    public static String getLoginTime() {
        try {
            return DateFormatUtils.format(context.get().getLoginTime(), "yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            return null;
        }
    }

    public static void setUserPin(String userPin) {
        userPin = StringUtils.trimToEmpty(userPin);
        init().setPin(userPin);
    }

    public static void setVenderId(Long venderId) {
        init().setVenderId(venderId);
    }

    public static void setShopName(String shopName) {
        init().setShopName(shopName);
    }

    public static void setCompanyName(String companyName) {
        init().setCompanyName(companyName);
    }

    public static void setPreLoginTime(Date preLoginTime) {
        init().setPreLoginTime(preLoginTime);
    }

    public static void setLoginTime(Date loginTime) {
        init().setLoginTime(loginTime);
    }

    public static void setPhone(String phone) {
        init().setPhone(phone);
    }

    public static void remove() {
        context.remove();
    }

    public static void setNick(String nick){
        init().setNick(nick);
    }
    public static void setUserId(Long userId){
        init().setUserId(userId);
    }
    private static LoginUserInfo init() {
        LoginUserInfo loginUserInfo = context.get();
        if (loginUserInfo == null) {
            loginUserInfo = new LoginUserInfo();
            context.set(loginUserInfo);
        }
        return loginUserInfo;
    }

    /**
     * 登陆信息
     */
    private static class LoginUserInfo implements Serializable {

        //登陆用户ID
        String pin;
        //登陆商家ID
        Long venderId;
        //店铺名称
        String shopName;
        //机构名称
        String companyName;
        //上次登录
        Date preLoginTime;
        //本次登录
        Date loginTime;
        //手机号
        String phone;
        //用户昵称
        String nick;
        //用户ID
        Long userId;

        public String getPin() {
            return pin;
        }

        public void setPin(String pin) {
            this.pin = pin;
        }

        public Long getVenderId() {
            return venderId;
        }

        public void setVenderId(Long venderId) {
            this.venderId = venderId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public Date getPreLoginTime() {
            return preLoginTime;
        }

        public void setPreLoginTime(Date preLoginTime) {
            this.preLoginTime = preLoginTime;
        }

        public Date getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(Date loginTime) {
            this.loginTime = loginTime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }
    }
}
