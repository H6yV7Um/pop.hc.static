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

    //�ж�״̬������log��־
    public static String convertStatusInAction(int status){
        String statusString="";
        if(status==1){
            statusString="����״̬";
        }else{
            statusString="ͣ��״̬";
        }
        return statusString;
    }

    public static String getCatalogStatus(int status) {
        String result = "";
        if (status == 0) {
            result = "����";
        } else if (status == 1) {
            result = "ͣ��";
        }

        return result;
    }
}
