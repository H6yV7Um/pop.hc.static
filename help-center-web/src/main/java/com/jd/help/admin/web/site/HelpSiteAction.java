package com.jd.help.admin.web.site;

import com.jd.common.web.result.Result;
import com.jd.help.center.admin.base.HelpBaseAction;
import com.jd.help.center.web.util.WebHelper;
import com.jd.help.dao.SiteDao;
import com.jd.help.domain.Catalog;
import com.jd.help.domain.HrmPurviewConstants;
import com.jd.help.domain.Site;
import com.jd.help.service.CatalogService;
import com.jd.help.service.SiteService;
import com.jd.uim.annotation.Authorization;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class HelpSiteAction extends HelpBaseAction {


    @Resource
    private SiteService siteService;
    
    @Resource
    private CatalogService catalogService;
    
    private Result result;
    
    private Site site;
    @Resource
    private SiteDao siteDao;
    @Authorization(HrmPurviewConstants.HELP_ROOT + "," + HrmPurviewConstants.HELP_ADMIN)
    public String insert() {
        return SUCCESS;
    }
    @Authorization(HrmPurviewConstants.HELP_ROOT + "," + HrmPurviewConstants.HELP_ADMIN)
    public String doInsert() {

		site.setCreator(WebHelper.getPin());
		site.setModifier(WebHelper.getPin());
		this.setResult(siteService.insert(site));
		this.toVm(result);
        return "list";
    }
    @Authorization(HrmPurviewConstants.HELP_ROOT + "," + HrmPurviewConstants.HELP_ADMIN)
    public String list(){
        this.setResult(siteService.listAll());
    	this.toVm(result);
        return SUCCESS;
    }
    @Authorization(HrmPurviewConstants.HELP_ROOT + "," + HrmPurviewConstants.HELP_ADMIN)
    public String update(){
        this.setResult(siteService.findOne(site));
        this.toVm(result);
        return SUCCESS;
    }
    @Authorization(HrmPurviewConstants.HELP_ROOT + "," + HrmPurviewConstants.HELP_ADMIN)
    public String doUpdate() {

		site.setModifier(WebHelper.getPin());
        
        this.setResult(siteService.update(site));
        this.toVm(result);
        if(result.getSuccess()){
         //   addLog("修改站点表，系统名为-->"+site.getName()+"系统ID为-->"+site.getId());
            return "list";
        }
        return "goUpdate";
    }
    @Authorization(HrmPurviewConstants.HELP_ROOT + "," + HrmPurviewConstants.HELP_ADMIN)
    public String doDelete() {

    	Catalog catalog = new Catalog();
    	catalog.setSiteId(site.getId());
    	Result result = catalogService.getFirstCatCountBySid(catalog);
    	int count = (Integer)result.get("catCount");
    	if(count > 0){
    		result.setSuccess(false);
    		result.setResultCode("HAS_CATALOG");
    		this.setResult(result);
            this.toVm(result);
            return "jsonResult";
    	}
        
        site.setModifier(WebHelper.getPin());
        this.setResult(siteService.delete(site));
        this.toVm(result);
        if(result.getSuccess()){
         //   addLog("删除站点表，系统名为-->"+site.getName()+"系统ID为-->"+site.getId());
        }
        return "jsonResult";
    }

    public String updateEnName(){
        long result = siteDao.updateEnName(site);
        return "jsonResult";
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
    
    
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

	public SiteService getSiteService() {
		return siteService;
	}

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

    public static void main(String[] args) {
        try {
            String str = URLEncoder.encode("中", "UTF-8");
            System.out.println("\"中\"经过UTF-8编码后字节："+str);
            String str1 = URLDecoder.decode(str,"ISO-8859-1");
            System.out.println("\""+str+"\"经过ISO-8859-1解码后字符："+str1);
            String str2 = URLEncoder.encode(str1,"UTF-8");
            System.out.println("\""+str1+"\"经过UTF-8编码后字节："+str2);
            String str3 = URLDecoder.decode(str2,"UTF-8");
            System.out.println("\""+str2+"\"经过UTF-8编码后字节："+str3);
            System.out.println(URLEncoder.encode("中","ASCII"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
    
}
