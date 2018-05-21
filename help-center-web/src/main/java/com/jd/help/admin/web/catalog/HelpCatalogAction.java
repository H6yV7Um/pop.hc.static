package com.jd.help.admin.web.catalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.jd.common.web.result.Result;
import com.jd.help.HelpBaseAction;
import com.jd.help.admin.web.common.NoHrmPrivilegeException;
import com.jd.help.center.web.util.WebHelper;
import com.jd.help.domain.Catalog;
import com.jd.help.domain.HrmPurviewConstants;
import com.jd.help.service.CatalogService;

@Controller
public class HelpCatalogAction extends HelpBaseAction {

    @Resource
    private CatalogService catalogService;

    private Catalog catalog;

    private Catalog refCatalog;

    private Map<String, Object> jsonRoot = new HashMap<String, Object>();
    //是否验证权限
    private final static boolean IS_VALIDATE = HrmPurviewConstants.IS_VALIDATE;
    
    public String findAll() throws NoHrmPrivilegeException{
        if (catalog == null) {
            catalog = new Catalog();
            catalog.setSiteId(siteId);
        }
        siteId = catalog.getSiteId();
        //权限验证
        initSite(IS_VALIDATE);
        page = (page == 0 ? 1 : page);
        Result result = catalogService.list(catalog, page, Integer.MAX_VALUE);
        this.toVm(result);
        return "toList";
    }

    public String doInsert()  throws NoHrmPrivilegeException{
        if (catalog != null) {
            catalog.setCreator(WebHelper.getPin());
            catalog.setModifier(WebHelper.getPin());
        }
        siteId = catalog.getSiteId();
        //权限验证
        initSite(IS_VALIDATE);
        Result result = catalogService.insert(catalog);
        this.toVm(result);
        return "result";
    }


    public String moveUp() {
        if (catalog != null) {
            catalog.setModifier(WebHelper.getPin());
        }
        Result result = catalogService.updateSortOrder(catalog, refCatalog);
        this.toVm(result);
        return "result";
    }

    public String moveDown() {
        if (catalog != null) {
            catalog.setModifier(WebHelper.getPin());
        }
        Result result = catalogService.updateSortOrder(catalog, refCatalog);
        this.toVm(result);
        return "result";
    }

    /**
     * Update object data
     *
     * @return
     */
    public String update() throws NoHrmPrivilegeException{
        if (catalog != null) {
            //catalog.setModifier(WebHelper.getPin());
        }
      //权限验证
        initSite(IS_VALIDATE);
        Result result = catalogService.detail(catalog);
        this.toVm(result);
        return "toUpdate";
    }

    /**
     * Use Ajax(Status : Enum)
     *
     * @return
     */
    public String updateCatalogStatus() throws NoHrmPrivilegeException{
        if (catalog != null) {
            catalog.setModifier(WebHelper.getPin());
        }
        //权限验证
        initSite(IS_VALIDATE);
        Result result = catalogService.updateCatalogStatus(catalog);
        this.toVm(result);
        return "result";
    }

    /**
     * Do Update
     *
     * @return
     */
    public String doUpdate() throws NoHrmPrivilegeException{
        if (catalog != null) {
            catalog.setModifier(WebHelper.getPin());
        }
        //权限验证
        initSite(IS_VALIDATE);
        Result result = catalogService.updateCatalog(catalog);
        this.toVm(result);
        return "result";
    }

    public String findCatBySiteIdForJson() throws NoHrmPrivilegeException{
    	siteId = catalog.getSiteId();
    	//权限验证
        initSite(IS_VALIDATE);
        Result result = catalogService.listAllBySiteId(catalog);
        this.toVm(result);
        return "result";
    }

    public String delete() throws NoHrmPrivilegeException{
    	//权限验证
        initSite(IS_VALIDATE);
    	if (catalog != null) {
            catalog.setModifier(WebHelper.getPin());
        }
      
        Result result = catalogService.delete(catalog);
        this.toVm(result);
        return "result";
    }

    public String loadChildrenCatalog() throws NoHrmPrivilegeException{
    	//权限验证
        initSite(IS_VALIDATE);
    	catalog.setStatus((byte) 1);
        Result result = catalogService.listAllBySiteId(catalog);
        List<String> catalogs = new ArrayList<String>();
        if (result.isSuccess()) {
            for (Catalog c : (List<Catalog>) result.get("catalogList")) {
                catalogs.add(c.getName());
            }
        }
        jsonRoot.put("catalogs", catalogs.toString());
        return "json";
    }

    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Catalog getRefCatalog() {
        return refCatalog;
    }

    public void setRefCatalog(Catalog refCatalog) {
        this.refCatalog = refCatalog;
    }

    public Map<String, Object> getJsonRoot() {
        return jsonRoot;
    }

    public void setJsonRoot(Map<String, Object> jsonRoot) {
        this.jsonRoot = jsonRoot;
    }
}
