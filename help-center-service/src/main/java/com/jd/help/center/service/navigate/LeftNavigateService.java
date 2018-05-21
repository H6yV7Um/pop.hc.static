package com.jd.help.center.service.navigate;

import com.jd.common.web.result.Result;
import com.jd.help.center.domain.HelpCenterLeftNavigate;

/**
 * Created with IntelliJ IDEA.
 * User: xuxianjun
 * Date: 13-7-12
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
public interface LeftNavigateService {

    /**
     * 返回系统内部页面使用左侧导航
     *
     * @param name
     * @return
     */
    public Result getLeftNavigateResult(String name);

    /**
     * 获取前台帮助中心的左侧导航
     *
     * @return
     */
    public HelpCenterLeftNavigate getLeftNavigate();

    /**
     * 获取帮助中心的左侧导航
     *
     * @param sysName
     * @return
     */
    public HelpCenterLeftNavigate getLeftNavigate(String sysName);

    /**
     * 获取帮助中心的左侧导航
     *
     * @param sysName
     * @param sysId
     * @return
     */
    public HelpCenterLeftNavigate getLeftNavigate(String sysName, int sysId);


}
