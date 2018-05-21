package com.jd.help;

import javax.annotation.Resource;

import com.jd.common.hrm.HrmPrivilegeHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jd.common.struts.action.BaseAction;
import com.jd.common.web.result.Result;
import com.jd.help.admin.web.common.NoHrmPrivilegeException;
import com.jd.help.center.web.util.WebHelper;
import com.jd.help.domain.Catalog;
import com.jd.help.domain.HrmPurviewConstants;
import com.jd.help.domain.Site;
import com.jd.help.service.CatalogService;
import com.jd.help.service.SiteService;

/**
 * @author laichendong
 * @since 2014/12/17 13:39
 */
public class HelpBaseAction extends BaseAction {
    
	private static final Log log = LogFactory.getLog(HelpBaseAction.class);
	
	protected Site site;
    @Resource
    protected SiteService siteService;
    @Resource
    protected CatalogService catalogService;
    @Resource(name="hrmPrivilegeHelper")
    protected HrmPrivilegeHelper hrmPrivilegeHelper;
    /**
     * 站点Id
     */
    protected Integer siteId;

    /**
     * 站点英文名
     */
    protected String siteEnName;

    /**
     * 根据参数中siteId 或siteEnName 初始site相关的数据
     * 如果都没有  就用默认站点
     */
    protected void initSite() {
        if (response != null) {
            response.setHeader("Cache-Control", "max-age=1800");
            response.addDateHeader("Last-Modified", System.currentTimeMillis());
        }

        // 通过参数初始化
        Site param = new Site();
        param.setStatus(1);
        if ((site == null) && ((siteId != null && siteId > 0) || StringUtils.isNotBlank(siteEnName))) {
            // 通过 siteId 或 siteEnName指定的初始参数
            // siteId和siteEnName同时起作用 如果两个参数都有值且对应不用的site。则认为两者都是错误的，将会加载默认站点
            param.setId(siteId);
            param.setEnName(siteEnName);
        } else if (site != null && ((site.getId() != null && site.getId() > 0) || (StringUtils.isNotBlank(site.getEnName())))) {
            //  通过site.id 指定的初始化参数 或
            //  通过site.enName 指定的初始化参数
            param = site;
        }
        Result r = siteService.findOne(param);
        if (r.isSuccess()) {
            site = (Site) r.get("site");
        }

        // 参数初始化失败 获得默认站点
        if (site == null) {
            r = siteService.findDefaultSite();
            if (r.isSuccess()) {
                site = (Site) r.get("site");
            }
        }

        if (site != null) {
            siteId = site.getId();
            siteEnName = site.getEnName();
        }

    }
    
    /**
     * 带有权限验证
     * 根据参数中siteId 或siteEnName 初始site相关的数据
     * 如果都没有  就用默认站点
     * @param validatePrivilegea 是否验证权限
     */
    protected void initSite(boolean validatePrivilegea) throws NoHrmPrivilegeException{
        log.error("goOutGEt:"+validatePrivilegea);
        // 通过参数初始化
        Site param = new Site();
        param.setStatus(1);
        if ((site == null) && ((siteId != null && siteId > 0) || StringUtils.isNotBlank(siteEnName))) {
            // 通过 siteId 或 siteEnName指定的初始参数
            // siteId和siteEnName同时起作用 如果两个参数都有值且对应不用的site。则认为两者都是错误的，将会加载默认站点
            param.setId(siteId);
            param.setEnName(siteEnName);
        } else if (site != null && ((site.getId() != null && site.getId() > 0) || (StringUtils.isNotBlank(site.getEnName())))) {
            //  通过site.id 指定的初始化参数 或
            //  通过site.enName 指定的初始化参数
            param = site;
        }
        Result r = siteService.findOne(param);
        if (r.isSuccess()) {
            site = (Site) r.get("site");
        }

        // 参数初始化失败 获得默认站点
        if (site == null) {
            r = siteService.findDefaultSite();
            if (r.isSuccess()) {
                site = (Site) r.get("site");
            }
        }

        if (site != null) {
            siteId = site.getId();
            siteEnName = site.getEnName();
        }
        log.error("siteId:"+siteId+"____siteEnName"+siteEnName);
        //验证权限，无权限是抛出异常
        if(validatePrivilegea && !auth(HrmPurviewConstants.HELP_ROOT + "," + HrmPurviewConstants.HELP_SITE + siteEnName)){
            log.error("错了siteId:"+siteId+"____siteEnName"+siteEnName);
        	throw new NoHrmPrivilegeException("no privlilege");
        }
    }
    
    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getSiteEnName() {
        return siteEnName;
    }

    public void setSiteEnName(String siteEnName) {
        this.siteEnName = siteEnName;
    }

    /**
     * 初始化左侧菜单对象
     *
     * @param result 传入Action需要返回的result
     */
    protected void initLeftMenu(Result result) {
        if (result == null || site.getId() == null) {
            return;
        }
        Catalog catalog = new Catalog();
        catalog.setSiteId(site.getId());
        Result res = catalogService.getLeftMenuCatalog(catalog);
        if (res.getSuccess()) {
            result.addDefaultModel("catalogBOList", res.get("catalogBOList"));
        }
        return;
    }

    /**
     * 判断这个pin有没有这个站点的管理权限
     *
     * @param privlilegeCode
     * @return
     */
    protected boolean auth(String privlilegeCode){
        String userPin = null;
    	try{
    		userPin = WebHelper.getPin();
            log.error("Privilege pin=" + userPin + ",code="+privlilegeCode);
    		return hrmPrivilegeHelper.hasHrmPrivilege(userPin,privlilegeCode);
        }catch(Exception e){
        	log.error("validate privlilege error.",e);
        	return false;
        }
    }

    /**
     * 初始化左侧菜单（包括三级菜单）
     * @param result
     */
    protected void initAllLeftMenu(Result result){
        if (result == null || site.getId() == null) {
            return;
        }
        if("venderportal".equals(siteEnName)){
            Site st = new Site();
            st.setEnName("vender");
            Result r = siteService.findOne(st);
            if (r.isSuccess()) {
                site = (Site) r.get("site");
                siteEnName=site.getEnName();
                siteId=site.getId();
            }
        }
        Catalog catalog = new Catalog();
        catalog.setSiteId(site.getId());
        Result res = catalogService.getAllLeftMenuCatalog(catalog);
        if (res.getSuccess()) {
            result.addDefaultModel("catalogBOList", res.get("catalogBOList"));
        }
        return;
    }
}
