package com.jd.help.center.dao.category.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.center.dao.category.HelpCategoryDao;
import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.category.query.HelpCategoryQuery;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-1
 * Time: 13:56:48
 * To change this template use File | Settings | File Templates.
 */
public class HelpCategoryDaoImpl extends BaseDao implements HelpCategoryDao {


    public List<HelpCategory> findCategoryAllById(int id) {
        return this.queryForList("HelpCategory.findCategoryAllById",id);
    }

    public List<HelpCategory> findCategoryByQuery(HelpCategoryQuery helpCategoryQuery) {
        return this.queryForList("HelpCategory.findCategoryByQuery",helpCategoryQuery);
    }

    public int updateCategory(HelpCategory helpCategory) {
        return this.update("HelpCategory.updateCategory",helpCategory);
    }

    public int insert(HelpCategory helpCategory) {
        try {
            return Integer.parseInt(String.valueOf(insert("HelpCategory.createHelpCategory",helpCategory)));
        } catch (DataAccessException e) {
            log.error("HelpCategoryDaoImpl.insert----->"+e.getMessage());
            return 0;
        }
    }

    public HelpCategory getCategoryById(int categoryId) {
        return (HelpCategory)this.queryForObject("HelpCategory.getCategoryById",categoryId);
    }

    public int updateCategoryStatus(HelpCategory helpCategory) {
        return this.update("HelpCategory.updateCategoryStatus",helpCategory);
    }

    public List<HelpCategory> findCategoryFront(int fid) {
        return this.queryForList("HelpCategory.findCategoryFront",fid);
    }
}
