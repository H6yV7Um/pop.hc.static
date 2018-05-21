package com.jd.help.dao.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.dao.CatalogDao;
import com.jd.help.dao.util.Page;
import com.jd.help.domain.Catalog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * generated by bud
 *
 * @author @laichendong
 */
@Repository("catalogDao")
@SuppressWarnings("unused")
public class CatalogDaoImpl extends BaseDao implements CatalogDao {

    private final static Log logger = LogFactory.getLog(CatalogDaoImpl.class);

    /**
     */
    @Override
    @Resource(name = "helpCenterDataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }


    public long insert(Catalog catalog) {
        return (Long) insert("Catalog.insert", catalog);
    }

    public int delete(Catalog catalog) {
        return update("Catalog.delete", catalog);
    }

    public int update(Catalog catalog) {
        return update("Catalog.update", catalog);
    }

    public Catalog queryForObject(Catalog catalog) {
        try {
            return (Catalog) queryForObject("Catalog.queryForObject", catalog);
        } catch (DataAccessException e) {
            logger.error("Cannot find Catalog data", e);
            return null;
        }
    }

    public int queryForCount(Catalog catalog) {
        return (Integer) queryForObject("Catalog.queryForCount", catalog);
    }

    public int updateCatalog(Catalog catalog) {
        return update("Catalog.updateCatalog", catalog);
    }

    public int updateCatalogStatus(Catalog catalog) {
        return update("Catalog.updateCatalogStatus", catalog);
    }

    public int updateCatalogSortOrder(Catalog catalog) {
        return update("Catalog.updateCatalogSortOrder", catalog);
    }


    @SuppressWarnings("unchecked")
    public List<Catalog> queryForList(Catalog catalog, int page, int pageSize) {
        Page<Catalog> condition = new Page<Catalog>();
        condition.setModel(catalog);
        condition.setPage(page);
        condition.setPageSize(pageSize);
        return queryForList("Catalog.queryForList", condition);
    }

    public List<Catalog> queryForList(Catalog catalog) {
        return queryForList("Catalog.getCatalogList", catalog);
    }

    public Integer getMaxSortOrder(Catalog catalog) {
        return (Integer)queryForObject("Catalog.getMaxSortOrder", catalog);
    }
    
    public List<Catalog> getLeftMenuCatalog(Catalog catalog){
    	return queryForList("Catalog.getLeftMenuCatalog", catalog);
    }
    
    public List<Catalog> getCatalogLevel3List(Catalog catalog){
    	return queryForList("Catalog.getCatalogLevel3List", catalog);
    }

    public List<Catalog> getAllLeftMenuCatalog(Catalog catalog){
        return queryForList("Catalog.getAllLeftMenuCatalog",catalog);
    }
}
