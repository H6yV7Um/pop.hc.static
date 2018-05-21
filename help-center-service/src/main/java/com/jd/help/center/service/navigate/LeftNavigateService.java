package com.jd.help.center.service.navigate;

import com.jd.common.web.result.Result;
import com.jd.help.center.domain.HelpCenterLeftNavigate;

/**
 * Created with IntelliJ IDEA.
 * User: xuxianjun
 * Date: 13-7-12
 * Time: ����11:39
 * To change this template use File | Settings | File Templates.
 */
public interface LeftNavigateService {

    /**
     * ����ϵͳ�ڲ�ҳ��ʹ����ർ��
     *
     * @param name
     * @return
     */
    public Result getLeftNavigateResult(String name);

    /**
     * ��ȡǰ̨�������ĵ���ർ��
     *
     * @return
     */
    public HelpCenterLeftNavigate getLeftNavigate();

    /**
     * ��ȡ�������ĵ���ർ��
     *
     * @param sysName
     * @return
     */
    public HelpCenterLeftNavigate getLeftNavigate(String sysName);

    /**
     * ��ȡ�������ĵ���ർ��
     *
     * @param sysName
     * @param sysId
     * @return
     */
    public HelpCenterLeftNavigate getLeftNavigate(String sysName, int sysId);


}
