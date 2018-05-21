package com.jd.help.configCenter;

import com.jd.common.util.StringUtils;
import com.jd.pop.configcenter.client.ConfigCenterClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by zhaojianhong on 2018/4/17.
 */
public class ConfigCenterUtils {

    private static final Log log = LogFactory.getLog(ConfigCenterUtils.class);

    private static final String DICTIONARY_SPLIT = ",";  //字典配置的分隔符
    private ConfigCenterClient configCenterClient;

    /**
     * 判断配置中心的字典配置是否包含相应的内容1
     *
     * @param configKey 配置中心的配置key
     * @param content   判断的内�?
     * @return 获取配置中心内容成功，并包含指定的内容，返回true，其他的情况返回false
     */
    public boolean containContent(String configKey, String content) {
        boolean result = false;
        if (StringUtils.isBlank(configKey) || StringUtils.isBlank(content)) {
            log.info("ConfigCenterUtil-->containContent 请求的参数为�? configKey:" + configKey + "; content:" + content);
            return result;
        }

        try {
            String configContent = configCenterClient.get(configKey);
            if (StringUtils.isNotBlank(configContent)) {
                String[] configContentArray = configContent.split(DICTIONARY_SPLIT);
                for (String tempContent : configContentArray) {
                    if (tempContent.equals(content)) {
                        result = true;
                        log.info("ConfigCenterUtil-->containContent 配置中心包含配置的内�? configKey:" + configKey + "; content:" + content);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log.error("ConfigCenterUtil-->containContent 获取配置中心数据异常 configKey:" + configKey + ";content:" + content, e);
        }
        return result;
    }

    /**
     * 从配置中心根据Key获取content
     *
     * @param configKey
     * @return
     */
    public String getContentFromConfigCenter(String configKey) throws Exception {
        if (StringUtils.isBlank(configKey))
            throw new Exception("ConfigCenterUtil-->getContentFromConfigCenter 请求的参数为�? configKey:" + configKey);
        String configContent = configCenterClient.get(configKey);
//        log.info("ConfigCenterUtil-->getContentFromConfigCenter get configKey from configCenter is:" + configContent);
        if (StringUtils.isBlank(configContent))
            throw new Exception("ConfigCenterUtil-->getContentFromConfigCenter 获取配置中心数据异常 configKey:" + configKey);
        return configContent;
    }

    /**
    *
    * @param configKey
    *
    **/
    public boolean isOpenSwitch(String configKey) throws Exception {
        //数据库降级开�?
        String openSwitch = configCenterClient.get(configKey);
        return Boolean.valueOf(openSwitch);
    }

    //--------------------------- setter ------------------------------


    /**
    *
    * setter of configcenterclient
    * @param configCenterClient
    *
    **/
    public void setConfigCenterClient(ConfigCenterClient configCenterClient) {
        this.configCenterClient = configCenterClient;
    }
}
