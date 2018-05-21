package com.jd.help.service.impl;

import com.jd.common.util.PaginatedList;
import com.jd.common.util.base.PaginatedArrayList;
import com.jd.common.web.result.Result;
import com.jd.help.dao.CatalogDao;
import com.jd.help.dao.IssueDao;
import com.jd.help.dao.SiteDao;
import com.jd.help.domain.Catalog;
import com.jd.help.domain.CatalogBO;
import com.jd.help.domain.Issue;
import com.jd.help.domain.Site;
import com.jd.help.service.CatalogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Catalog service ʵ��
 * generated by bud
 *
 * @author @laichendong
 */
@Service("catalogService")
public class CatalogServiceImpl implements CatalogService {
    @Resource
    private CatalogDao catalogDao;
    @Resource
    private SiteDao siteDao;
    @Resource
    private IssueDao issueDao;

    private final static int DEFAULT_PID_VALUE = 0;
    private final static int ENABLE_STATUS_VALUE = 1;    //ö����
    private final static int DISABLE_STATUS_VALUE = 0;

    public Result list(Catalog catalog, int page, int pageSize) {
        Result result = new Result();
        if (catalog == null) {
            catalog = new Catalog();
        }
        if (catalog.getPid() == null) {
            catalog.setPid(DEFAULT_PID_VALUE);
        }
        Catalog parentCatalog = getParentCatalog(new Catalog(catalog.getPid()));
        if (catalog.getPid() == DEFAULT_PID_VALUE && catalog.getSiteId() == null) {
            return result;
        }
        if (catalog.getPid() > DEFAULT_PID_VALUE && parentCatalog == null) {
            return result;
        }
        Site site;
        if (parentCatalog != null) {
            site = new Site(parentCatalog.getSiteId());
        } else{
            site = new Site(catalog.getSiteId());
        }
        site = siteDao.findOne(site);
        if (site == null) {
            return result;
        }
        PaginatedList<Catalog> catalogList = new PaginatedArrayList<Catalog>(page, pageSize);
        catalogList.setTotalItem(catalogDao.queryForCount(catalog));
        if (catalogList.getTotalItem() > 0) {
            catalogList.addAll(catalogDao.queryForList(catalog, page, pageSize));
        }
        result.addDefaultModel("site", site);
        result.addDefaultModel("cascadeCatalogList", getCascadeCatalogList(parentCatalog));
        result.addDefaultModel("parentCatalog", parentCatalog);
        result.addDefaultModel("catalogList", catalogList);
        result.setSuccess(true);
        return result;
    }

    public Result listAllByPidAndSiteId(Catalog catalog) {
        Result result = new Result();
        if (catalog == null || catalog.getSiteId() ==  null) {
        	result.setSuccess(false);
        }
        if (catalog.getPid() == null) {
            catalog.setPid(DEFAULT_PID_VALUE);
        }
        List<Catalog> catalogList = catalogDao.queryForList(catalog);
        result.addDefaultModel("catalogList", catalogList);
        result.setSuccess(true);
        return result;
    }
    

    public Result listAllBySiteId(Catalog catalog) {
        Result result = new Result();
        if (catalog == null || catalog.getSiteId() ==  null) {
        	result.setSuccess(false);
        }
        List<Catalog> catalogList = catalogDao.queryForList(catalog);
        result.addDefaultModel("catalogList", catalogList);
        result.setSuccess(true);
        return result;
    }
    
    
    private Catalog getParentCatalog(Catalog catalog) {
        if (catalog == null || catalog.getId() == null || catalog.getId() == DEFAULT_PID_VALUE) {
            return null;
        }
        return catalogDao.queryForObject(catalog);
    }

    /**
     * ��Ŀ��ȡ������Ϣ
     * @param catalog
     * @return
     */
    public List<Catalog> getCascadeCatalogList(Catalog catalog) {
        List<Catalog> catalogList = null;
        if (catalog == null || catalog.getId() == null || catalog.getId() == DEFAULT_PID_VALUE) {
            return catalogList;
        }
        catalog = catalogDao.queryForObject(catalog);
        if (catalog == null) {
            return catalogList;
        }
        catalogList = new ArrayList<Catalog>();
        catalogList.add(catalog);
        catalog = new Catalog(catalog.getPid());
        if (catalog.getId() > DEFAULT_PID_VALUE) {
            catalogList.addAll(0,getCascadeCatalogList(catalog));
        }
        return catalogList;
    }

    public Result detail(Catalog catalog) {
        Result result = new Result();
        if (catalog == null) {
            return result;
        }
        result.setSuccess(true);
        catalog = catalogDao.queryForObject(catalog);
        if (catalog == null) {
            return result;
        }
        result.addDefaultModel("catalog", catalog);
        return result;
    }

    /**
     * ���������Ŀ����ֵ todo wjl refactor
     * @param catalog
     * @param refCatalog
     * @return
     */
    public Result updateSortOrder(Catalog catalog, Catalog refCatalog) {
        Result result = new Result(false);
        if (catalog == null || catalog.getId() == null || refCatalog == null || refCatalog.getId() == null) {
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        catalog = catalogDao.queryForObject(catalog);
        refCatalog = catalogDao.queryForObject(refCatalog);
        if (catalog == null ||  refCatalog == null) {
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        int sortOrder = catalog.getSortOrder();
        catalog.setSortOrder(refCatalog.getSortOrder());
        refCatalog.setSortOrder(sortOrder);
        //collection operate in transaction manager
        if (catalogDao.updateCatalogSortOrder(catalog) > 0) {
            catalogDao.updateCatalogSortOrder(refCatalog);
            result.setSuccess(true);
            result.setResultCode("system.insert.success");
        } else {
            result.setResultCode("system.update.error");
        }
        return result;
    }

    public Result insert(Catalog catalog) {
        Result result = new Result();
        if (catalog == null) {
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        if (catalog.getPid() == null) {
            catalog.setPid(DEFAULT_PID_VALUE);
            catalog.setLevel(1);
        }
        catalog.setSortOrder(getNextSortOrder(catalog));
        catalog.setStatus((byte) DISABLE_STATUS_VALUE);
        if (catalogDao.insert(catalog) <= 0) {
            result.setResultCode("system.insert.error");
        } else {
            result.setSuccess(true);
            result.setResultCode("system.insert.success");
        }
        return result;
    }

    /**
     * ��ȡCatalog�������һ������ֵ
     * @param catalog
     * @return
     */
    private Integer getNextSortOrder(Catalog catalog) {
        Integer result = catalogDao.getMaxSortOrder(catalog);
        if (result == null) {
            result = 0;
        }
        return result + 1;
    }

    public Result update(Catalog catalog) {
        Result result = new Result(false);
        if (catalog == null || catalog.getId() == null || catalog.getId() <= 0) {
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        if (catalogDao.update(catalog) > 0) {
            result.setResultCode("system.update.success");
            result.setSuccess(true);
        } else {
            result.setResultCode("system.update.error");
        }
        return result;
    }

    public Result updateCatalog(Catalog catalog) {
        Result result = new Result(false);
        if (catalog == null || catalog.getId() == null || catalog.getId() <= 0) {
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        if (catalogDao.updateCatalog(catalog) > 0) {
            result.setResultCode("system.update.success");
            result.setSuccess(true);
        } else {
            result.setResultCode("system.update.error");
        }
        return result;
    }

    public Result updateCatalogStatus(Catalog catalog) {
        Result result = new Result(false);
        if (catalog == null || catalog.getId() == null || catalog.getId() <= 0) {
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        if (catalog.getStatus() == 1) {
            catalog.setStatus((byte) DISABLE_STATUS_VALUE);
        } else {
            catalog.setStatus((byte) ENABLE_STATUS_VALUE);
        }
        if (catalogDao.updateCatalogStatus(catalog) > 0) {
            result.setResultCode("system.update.success");
            result.setSuccess(true);
        } else {
            result.setResultCode("system.update.error");
        }
        return result;
    }

    public Result delete(Catalog catalog) {
        Result result = new Result();
        if (catalog == null || catalog.getId() == null || catalog.getId() <= 0) {
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
        if(isCanDelete(catalog) && catalogDao.delete(catalog) > 0){
            result.setSuccess(true);
            result.setResultCode("system.delete.success");
        } else {
            result.setResultCode("system.delete.error");
        }
        return result;
    }
    
    public Result getFirstCatCountBySid(Catalog catalog){
    	Result result = new Result();
    	if (catalog == null || catalog.getSiteId() == null) {
            result.setSuccess(false);
            result.setResultCode("system.no.entity.to.operate");
            return result;
        }
    	//ֻ��ѯһ����Ŀ
    	catalog.setPid(DEFAULT_PID_VALUE);
    	
    	int count = catalogDao.queryForCount(catalog);
    	result.addDefaultModel("catCount",count);
    	result.setSuccess(true);
        result.setResultCode("system.delete.success");
        return result;
    }
    
    private boolean isCanDelete(Catalog catalog) {
        if (catalog == null || catalog.getId() == null || catalog.getId() <= 0) {
            return false;
        }
        if (isHasSubCatalog(catalog) || isHasIssue(catalog)) {
            return false;    //has sub catalog or issue ,cannot delete
        }
        return true;
    }

    private boolean isHasSubCatalog(Catalog catalog) {
        if (catalog == null || catalog.getId() == null || catalog.getId() <= 0) {
            return false;
        }
        Catalog subCatalog = new Catalog(null, catalog.getId());
        return catalogDao.queryForCount(subCatalog) > 0;
    }

    private boolean isHasIssue(Catalog catalog) {
        if (catalog == null || catalog.getId() == null || catalog.getId() <= 0) {
            return false;
        }
        Issue issue = new Issue();
        issue.setCataId(catalog.getId());
        issue.setStatus(0);
        int i = issueDao.queryForCount(issue);
        issue.setStatus(1);
        int j = issueDao.queryForCount(issue);
        return i + j > 0;
    }
    
    public Result getLeftMenuCatalog(Catalog catalog){
    	
    	Result result = new Result();
    	List<Catalog> catalogList = catalogDao.getLeftMenuCatalog(catalog);
    	List<CatalogBO> catalogBOList = new ArrayList<CatalogBO>();
    	for(int i =0 ; i < catalogList.size(); i ++){
    		Catalog cat = catalogList.get(i);
    		if(cat != null){
    			if( cat.getLevel() == 1){
    				catalogBOList.add(convertCatalogToBO(cat));
    			}else{
    				setCatalogBOChildren(catalogBOList,convertCatalogToBO(cat));
    			}
    		}
    	}
    	result.setSuccess(true);
    	result.addDefaultModel("catalogBOList",catalogBOList);
    	
    	return result;
    }
    
    private CatalogBO convertCatalogToBO(Catalog catalog){
    	if(catalog != null){
    		CatalogBO catalogBO = new CatalogBO();
    		catalogBO.setCreated(catalog.getCreated());
    		catalogBO.setCreator(catalog.getCreator());
    		catalogBO.setId(catalog.getId());
    		catalogBO.setPid(catalog.getPid());
    		catalogBO.setLevel(catalog.getLevel());
    		catalogBO.setModified(catalog.getModified());
    		catalogBO.setModifier(catalog.getModifier());
    		catalogBO.setName(catalog.getName());
    		catalogBO.setNotes(catalog.getNotes());
    		catalogBO.setSiteId(catalog.getSiteId());
    		catalogBO.setSortOrder(catalog.getSortOrder());
    		catalogBO.setStatus(catalog.getStatus());
    		return catalogBO;
    	}
    	return null;
    	
    }
    
    private void setCatalogBOChildren(List<CatalogBO> catalogBOList,CatalogBO catalogBO){
    	for(CatalogBO cat : catalogBOList){
    		if(cat.getId().intValue() == catalogBO.getPid().intValue()){
    			if(cat.getChildren() == null){
    				List<CatalogBO> childrenList = new ArrayList<CatalogBO>();
    				childrenList.add(catalogBO);
    				cat.setChildren(childrenList);
    			}else{
    				cat.getChildren().add(catalogBO);
    			}
    		}
    	}
    }
    
    public Result getCatalogLevel3List(Catalog catalog){
    	Result result = new Result();
    	if(catalog == null && catalog.getPid() == null){
    		 result.setSuccess(false);
             result.setResultCode("system.no.entity.to.operate");
             return result;
    	}
    	List<Catalog> catalogList = catalogDao.getCatalogLevel3List(catalog);
    	result.addDefaultModel("catalogLevel3List",catalogList);
    	result.setSuccess(true);
    	return result;
    }

    public Result getAllLeftMenuCatalog(Catalog catalog){
        Result result = new Result();
        List<Catalog> catalogList = catalogDao.getAllLeftMenuCatalog(catalog);
        List<CatalogBO> catLogOneBOList = new ArrayList<CatalogBO>();
        List<CatalogBO> temCatalogBOList = new ArrayList<CatalogBO>();
        List<CatalogBO> catalogBOList = new ArrayList<CatalogBO>();
        for(int i =0 ; i < catalogList.size(); i ++){
            Catalog cat = catalogList.get(i);
            if(cat != null){
                if(cat.getLevel() == 1){
                    catLogOneBOList.add(convertCatalogToBO(cat));
                }else if( cat.getLevel() == 2){
                    temCatalogBOList.add(convertCatalogToBO(cat));
                }else{
                    setCatalogBOChildren(temCatalogBOList, convertCatalogToBO(cat));
                }
            }
        }
        for(int i = 0;i<catLogOneBOList.size();i++){
            CatalogBO catOneBO = catLogOneBOList.get(i);
            if(catOneBO != null){
                for(CatalogBO catalogBO :temCatalogBOList){
                    if(catalogBO.getPid().intValue() == catOneBO.getId().intValue()){
                        if(catOneBO.getChildren() == null){
                            List<CatalogBO> childrenList = new ArrayList<CatalogBO>();
                            childrenList.add(catalogBO);
                            catOneBO.setChildren(childrenList);
                        }else {
                            catOneBO.getChildren().add(catalogBO);
                        }
                    }
                }
                catalogBOList.add(catOneBO);
            }
        }
        result.setSuccess(true);
        result.addDefaultModel("catalogBOList",catalogBOList);

        return result;
    }

    @Override
    public Catalog getCatalogById(Integer cataId) {
        Catalog catalog = new Catalog();
        catalog.setId(cataId);
        return catalogDao.queryForObject(catalog);
    }
}