package com.jd.help;

/**
 * @author laichendong
 * @since 2014/12/27 14:52
 */
public enum PageModule {
    INDEX(new String[]{"index_hotIssue", "index_hotService", "index_catalogList","index_banner","index_studyArea","index_venderSpecial","index_venderServerNavigation"}, new String[]{"首页-热点问题", "首页-常用自助服务", "首页-类目列表","首页-轮播图","首页-学习专区","商家专题","首页-商家服务导航"}) //
    , ISSUE(new String[]{"issue_hotIssue", "issue_quickNav"}, new String[]{"常见问题首页-热点问题", "常见问题首页-快速导航"}) //
    , SERVICE(new String[]{"service_allService", "service_hotService"}, new String[]{"自助服务首页-全部自助服务", "自助服务首页-常用自助服务"}) //
    , CUSTOM(new String[]{"custom_banner"}, new String[]{"联系客服-Banner图"}) //
    , GUIDE(new String[]{"guide_guide", "guide_imgGuide"}, new String[]{"新手指南-新手购物教学", "新手指南-图文指南"}) //
    ;


    private String[] moduleKeies;
    private String[] moduleNames;

    PageModule(String[] moduleKeies, String[] moduleNames) {
        this.moduleKeies = moduleKeies;
        this.moduleNames = moduleNames;
    }

    public String[] getModuleKeies() {
        return moduleKeies;
    }

    public String[] getModuleNames() {
        return moduleNames;
    }
}
