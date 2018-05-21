package com.jd.help.customer.web;

import com.jd.common.web.result.Result;
import com.jd.help.HelpBaseAction;
import com.jd.help.PageModule;
import com.jd.help.domain.HtmlModule;
import com.jd.help.service.HtmlModuleService;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author laichendong
 * @since 2014/12/17 15:01
 */
public class PageAction extends HelpBaseAction {

    @Resource
    private HtmlModuleService htmlModuleService;

    @Resource
    private ContactUsInterceptor contactUsInterceptor;

    private HtmlModule htmlModule;

    /**
     * ҳ������
     */
    private String pageName;

    private Map<String, Object> jsonRoot = new HashMap<String, Object>();

    /**
     * ��ʾǰ̨ҳ��
     *
     * @return
     */
    public String page() {
        initSite();
        Result r = new Result(true);
        findHtmlModuleForPage(pageName, r);

        // ��ͬ��page ȡ��ͬ������
        if (pageName.equals("issue")) {
            if("vender".equals(siteEnName)){
                // ����������ҳ������ٱ���
                initAllLeftMenu(r);
            }
            else{
                initLeftMenu(r);
            }
        }
        toVm(r);
        return SUCCESS;
    }

    /**
     * Ϊÿ��ҳ���ҵ���Ӧ��htmlģ��
     *
     * @param pageName
     * @param r
     */
    private void findHtmlModuleForPage(String pageName, Result r) {
        PageModule pageModule = null;
        try {
            pageModule = PageModule.valueOf(pageName.toUpperCase());
            if (pageModule != null && pageModule.getModuleKeies() != null && pageModule.getModuleKeies().length > 0) {
                List<HtmlModule> moduleList = htmlModuleService.findByKeies(pageModule.getModuleKeies(),site.getId());
                if (CollectionUtils.isNotEmpty(moduleList)) {
                    for (HtmlModule module : moduleList) {
                        module.removeHttp();
                        r.addDefaultModel(module.getKey(), module);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            // slide is golden
        }
    }

    /**
     * ��ʾǰ̨ҳ��
     *
     * @return
     */
    public String pagePreview() {
        initSite();
        pageName = pageNameFromModuleKey(htmlModule.getKey());
        Result r = new Result(true);
        findHtmlModuleForPage(pageName, r);
        // ��ͬ��page ȡ��ͬ������
        if (pageName.equals("issue")) {
            // ����������ҳ
            initLeftMenu(r);
        }

        r.addDefaultModel(htmlModule.getKey(), htmlModule);

        toVm(r);
        return SUCCESS;
    }

    /**
     * ˢ��contact us ��cookie
     *
     * @return
     */
    public String refreshCtu() {
        String ctu = contactUsInterceptor.refreshCookieValue(ActionContext.getContext());
        jsonRoot.put("ctu", ctu);
        return "json";
    }

    private String pageNameFromModuleKey(String key) {
        return key.split("_")[0];
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public HtmlModule getHtmlModule() {
        return htmlModule;
    }

    public void setHtmlModule(HtmlModule htmlModule) {
        this.htmlModule = htmlModule;
    }

    public Map<String, Object> getJsonRoot() {
        return jsonRoot;
    }

    public void setJsonRoot(Map<String, Object> jsonRoot) {
        this.jsonRoot = jsonRoot;
    }
}
