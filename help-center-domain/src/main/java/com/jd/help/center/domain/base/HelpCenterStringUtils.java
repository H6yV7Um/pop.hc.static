package com.jd.help.center.domain.base;

import com.jd.common.util.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-12-2
 * Time: 18:33:38
 * To change this template use File | Settings | File Templates.
 */
public class HelpCenterStringUtils {

    //判断状态，用在log日志
    public static String convertStatusInAction(int status){
        String statusString="";
        if(status==1){
            statusString="启用状态";
        }else{
            statusString="停用状态";
        }
        return statusString;
    }

    public static String getCatalogStatus(int status) {
        String result = "";
        if (status == 0) {
            result = "启用";
        } else if (status == 1) {
            result = "停用";
        }

        return result;
    }
}
