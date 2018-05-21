package com.jd.help.admin.web.htmlmodule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.jd.common.web.result.Result;
import com.jd.help.HelpBaseAction;
import com.jd.help.PageModule;
import com.jd.help.admin.web.common.NoHrmPrivilegeException;
import com.jd.help.center.web.util.WebHelper;
import com.jd.help.domain.HrmPurviewConstants;
import com.jd.help.domain.HtmlModule;
import com.jd.help.service.HtmlModuleService;

public class HelpHtmlModuleAction extends HelpBaseAction {

    @Resource
    private HtmlModuleService htmlModuleService;

    private HtmlModule htmlModule;

  //是否验证权限
    private final static boolean IS_VALIDATE = HrmPurviewConstants.IS_VALIDATE;
    
    /**
     * 新增页面模块页面
     *
     * @return
     */
    public String add() throws NoHrmPrivilegeException{
    	initSite(IS_VALIDATE);
        return SUCCESS;
    }

    /**
     * 执行新增页面模块
     *
     * @return
     */
    public String doAdd() throws NoHrmPrivilegeException{
    	initSite(IS_VALIDATE);
        if (htmlModule != null) {
            htmlModule.setCreator(WebHelper.getPin());
            htmlModule.setCreated(new Date());
            htmlModule.setModified(new Date());
            htmlModule.setModifier(WebHelper.getPin());
            htmlModule.setStatus(0);;
        }
        Result result = htmlModuleService.insert(htmlModule);
        return "list";
    }

    /**
     * 只预览模块部分内容
     *
     * @return
     */
    public String preview() {
        this.toVm(htmlModuleService.detail(htmlModule));
        return SUCCESS;
    }

    /**
     * 一键往库里初始化所有页面模块
     *
     * @return
     */
    public String init() throws NoHrmPrivilegeException{
    	initSite(IS_VALIDATE);
        Map<String, String> modules = new HashMap<String, String>();
        for (PageModule module : PageModule.values()) {
            for (int i = 0; i < module.getModuleKeies().length; i++) {
                modules.put(module.getModuleKeies()[i], module.getModuleNames()[i]);
            }
        }

        for (Map.Entry<String, String> entry : modules.entrySet()) {
            HtmlModule htmlModule = new HtmlModule();
            htmlModule.setCreator(WebHelper.getPin());
            htmlModule.setKey(entry.getKey());
            htmlModule.setName(entry.getValue());
            htmlModule.setSiteId(siteId);
            htmlModule.setStatus(0);
            htmlModuleService.insert(htmlModule);
        }

        return "list";
    }

    public String update() throws NoHrmPrivilegeException{
    	initSite(IS_VALIDATE);
        this.toVm(htmlModuleService.detail(htmlModule));
        return SUCCESS;
    }

    public String doUpdate() throws NoHrmPrivilegeException{
    	initSite(IS_VALIDATE);
        Result result = null;
        if (htmlModule == null) {
            result = new Result(false);
            return SUCCESS;
        }
        htmlModule.setStatus(0);
        htmlModule.setModifier(WebHelper.getPin());
        htmlModule.setModified(new Date());
        result = htmlModuleService.update(htmlModule);
        this.toVm(result);
        return "list";
    }

    public String list() throws NoHrmPrivilegeException{
    	initSite(IS_VALIDATE);
        if (htmlModule == null) {
            htmlModule = new HtmlModule();
        }
        htmlModule.setSiteId(site != null && site.getId() != null ? site.getId() : 0);
        if (htmlModule.getName() != null) {
            htmlModule.setName(htmlModule.getName().trim());
        }
        page = (page <= 0 ? 1 : page);
        this.toVm(htmlModuleService.list(htmlModule, page, PAGE_SIZE));
        return SUCCESS;
    }

    public String delete() throws NoHrmPrivilegeException{
    	initSite(IS_VALIDATE);
        this.toVm(htmlModuleService.delete(htmlModule));
        return "list";
    }

    public HtmlModule getHtmlModule() {
        return htmlModule;
    }

    public void setHtmlModule(HtmlModule htmlModule) {
        this.htmlModule = htmlModule;
    }

}
