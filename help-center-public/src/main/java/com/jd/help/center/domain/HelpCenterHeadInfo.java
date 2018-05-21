package com.jd.help.center.domain;

import java.io.Serializable;

/**
 * 头信息
 * User: xuxianjun
 * Date: 13-7-25
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
public class HelpCenterHeadInfo implements Serializable {

    private String returnCode;

    private String returnMsg;

    private String paramName;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
}
