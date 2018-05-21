package com.jd.help.center.admin.velocity;

import com.jd.common.util.StringUtils;
import com.jd.help.center.domain.border.BorderInfo;
import com.jd.help.center.domain.constants.UMPConstants;
import com.jd.help.center.manager.border.HelpBorderManager;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 12-10-12
 * Time: 上午9:54
 * To change this template use File | Settings | File Templates.
 */
public class MongoVelocityTools {
    private static final String BORDER_HEAD = "head";
    private static final String BORDER_BOTTOM = "bottom";
    Log log= LogFactory.getLog(MongoVelocityTools.class);
    private HelpBorderManager helpBorderManager;

    private Map<String,String> urlReplaceMap;

    public String getBorder(String key){
        StringBuilder stringBuilder = new StringBuilder();
        CallerInfo info = Profiler.registerInfo(UMPConstants.UMP_HELP_GET_BORDER, UMPConstants.enableHeart, UMPConstants.enableTP);
        try{
            BorderInfo borderInfo = helpBorderManager.getBorderInfoByKey(key);
            if(borderInfo ==null || StringUtils.isBlank(borderInfo.getContent())){
                Profiler.functionError(info);
            } else {
                borderInfo.removeHttp();
                stringBuilder.append(replaceUrl(borderInfo.getContent()));
            }
        }catch (Exception e){
            log.error(UMPConstants.UMP_HELP_GET_BORDER,e);
            log.error("Method: get data error,key is "+key,e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return stringBuilder.toString();
    }

    public String getHead(String key){
        StringBuilder stringBuilder = new StringBuilder();
        CallerInfo info = Profiler.registerInfo(UMPConstants.UMP_HELP_GET_HEAD, UMPConstants.enableHeart, UMPConstants.enableTP);
        try{
            BorderInfo borderInfo = helpBorderManager.getBorderInfoByKey(key);
            //取默认头文件
            if(borderInfo ==null){
                borderInfo = helpBorderManager.getBorderInfoByKey(BORDER_HEAD);
            }
            if(borderInfo ==null || StringUtils.isBlank(borderInfo.getContent())){
                Profiler.functionError(info);
            } else {
                borderInfo.removeHttp();
                stringBuilder.append(replaceUrl(borderInfo.getContent()));
            }
        }catch (Exception e){
            log.error(UMPConstants.UMP_HELP_GET_HEAD,e);
            log.error("Method: get data error,key is "+key,e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return stringBuilder.toString();
    }

    public String getBottom(String key){
        StringBuilder stringBuilder = new StringBuilder();
        CallerInfo info = Profiler.registerInfo(UMPConstants.UMP_HELP_GET_BOTTOM, UMPConstants.enableHeart, UMPConstants.enableTP);
        try{
            BorderInfo borderInfo = helpBorderManager.getBorderInfoByKey(key);
            //取默认尾文件
            if(borderInfo ==null){
                borderInfo = helpBorderManager.getBorderInfoByKey(BORDER_BOTTOM);
            }
            if(borderInfo ==null || StringUtils.isBlank(borderInfo.getContent())){
                Profiler.functionError(info);
            } else {
                borderInfo.removeHttp();
                stringBuilder.append(replaceUrl(borderInfo.getContent()));
            }
        }catch (Exception e){
            log.error(UMPConstants.UMP_HELP_GET_BOTTOM,e);
            log.error("Method: get data error,key is "+key,e);
            Profiler.functionError(info);
        }finally {
            Profiler.registerInfoEnd(info);
        }
        return stringBuilder.toString();
    }

    private String replaceUrl(String url){
//        urlReplaceMap.put("//misc.360buyimg.com/lib/skin/2013/base.css","/misc/skin/base-2013.css");
//        urlReplaceMap.put("//misc.360buyimg.com/lib/skin/2012/base.css","/misc/skin/base-2012.css");
//        urlReplaceMap.put("//misc.360buyimg.com/lib/js/2012/base-v1.js","/misc/js/base-v1.js");
//        urlReplaceMap.put("//static.360buyimg.com/dsp/ssp.js","/misc/js/ssp.js");
//        urlReplaceMap.put("//misc.360buyimg.com/lib/js/2012/lib-v1.js","/misc/js/lib-v1.js");
//        urlReplaceMap.put("//static.360buyimg.com/help/seller/skin/help-20140924.css","/misc/skin/help-20140924.css");
//        urlReplaceMap.put("//misc.360buyimg.com/help/misc/skin/helpc.css","/misc/skin/helpc.css");

        for (Map.Entry<String, String> entry : urlReplaceMap.entrySet()) {
            url=url.replace(entry.getKey(),entry.getValue());
        }
        return url;
    }

    public Map<String, String> getUrlReplaceMap() {
        return urlReplaceMap;
    }

    public void setUrlReplaceMap(Map<String, String> urlReplaceMap) {
        this.urlReplaceMap = urlReplaceMap;
    }

    public void setHelpBorderManager(HelpBorderManager helpBorderManager) {
        this.helpBorderManager = helpBorderManager;
    }
}
