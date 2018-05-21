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
     * վ��Id
     */
    protected Integer siteId;

    /**
     * վ��Ӣ����
     */
    protected String siteEnName;

    /**
     * ���ݲ�����siteId ��siteEnName ��ʼsite��ص�����
     * �����û��  ����Ĭ��վ��
     */
    protected void initSite() {
        if (response != null) {
            response.setHeader("Cache-Control", "max-age=1800");
            response.addDateHeader("Last-Modified", System.currentTimeMillis());
        }

        // ͨ��������ʼ��
        Site param = new Site();
        param.setStatus(1);
        if ((site == null) && ((siteId != null && siteId > 0) || StringUtils.isNotBlank(siteEnName))) {
            // ͨ�� siteId �� siteEnNameָ���ĳ�ʼ����
            // siteId��siteEnNameͬʱ������ ���������������ֵ�Ҷ�Ӧ���õ�site������Ϊ���߶��Ǵ���ģ��������Ĭ��վ��
            param.setId(siteId);
            param.setEnName(siteEnName);
        } else if (site != null && ((site.getId() != null && site.getId() > 0) || (StringUtils.isNotBlank(site.getEnName())))) {
            //  ͨ��site.id ָ���ĳ�ʼ������ ��
            //  ͨ��site.enName ָ���ĳ�ʼ������
            param = site;
        }
        Result r = siteService.findOne(param);
        if (r.isSuccess()) {
            site = (Site) r.get("site");
        }

        // ������ʼ��ʧ�� ���Ĭ��վ��
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
     * ����Ȩ����֤
     * ���ݲ�����siteId ��siteEnName ��ʼsite��ص�����
     * �����û��  ����Ĭ��վ��
     * @param validatePrivilegea �Ƿ���֤Ȩ��
     */
    protected void initSite(boolean validatePrivilegea) throws NoHrmPrivilegeException{
        log.error("goOutGEt:"+validatePrivilegea);
        // ͨ��������ʼ��
        Site param = new Site();
        param.setStatus(1);
        if ((site == null) && ((siteId != null && siteId > 0) || StringUtils.isNotBlank(siteEnName))) {
            // ͨ�� siteId �� siteEnNameָ���ĳ�ʼ����
            // siteId��siteEnNameͬʱ������ ���������������ֵ�Ҷ�Ӧ���õ�site������Ϊ���߶��Ǵ���ģ��������Ĭ��վ��
            param.setId(siteId);
            param.setEnName(siteEnName);
        } else if (site != null && ((site.getId() != null && site.getId() > 0) || (StringUtils.isNotBlank(site.getEnName())))) {
            //  ͨ��site.id ָ���ĳ�ʼ������ ��
            //  ͨ��site.enName ָ���ĳ�ʼ������
            param = site;
        }
        Result r = siteService.findOne(param);
        if (r.isSuccess()) {
            site = (Site) r.get("site");
        }

        // ������ʼ��ʧ�� ���Ĭ��վ��
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
        //��֤Ȩ�ޣ���Ȩ�����׳��쳣
        if(validatePrivilegea && !auth(HrmPurviewConstants.HELP_ROOT + "," + HrmPurviewConstants.HELP_SITE + siteEnName)){
            log.error("����siteId:"+siteId+"____siteEnName"+siteEnName);
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
     * ��ʼ�����˵�����
     *
     * @param result ����Action��Ҫ���ص�result
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
     * �ж����pin��û�����վ��Ĺ���Ȩ��
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
     * ��ʼ�����˵������������˵���
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
