package com.jd.help.center.admin.base;

import com.jd.common.struts.action.BaseAction;
import com.jd.help.center.web.util.WebHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-12-2
 * Time: 17:10:57
 * To change this template use File | Settings | File Templates.
 */
public class HelpBaseAction extends BaseAction {
    private static final Log log = LogFactory.getLog(HelpBaseAction.class);
    public void addLog(String info){
        String name= WebHelper.getPin();
        String ipAddress=WebHelper.getIpAddress();
        StringBuffer sbf=new StringBuffer();
        sbf.append("操作人："+name+" 操作IP："+ipAddress+" 操作："+info);
        log.debug(sbf.toString());
    }
}
