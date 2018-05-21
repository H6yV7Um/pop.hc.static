package com.jd.help.center.manager.category;

import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.category.query.HelpCategoryQuery;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-1
 * Time: 14:10:25
 * To change this template use File | Settings | File Templates.
 */
public interface HelpCategoryManager {
    
    List<HelpCategory> findCategoryAllById(int id);

    /**
     * 根据查询条件查询分类信息
     * @param helpCategoryQuery
     * @return
     */
    List<HelpCategory> findCategoryByQuery(HelpCategoryQuery helpCategoryQuery);

    int updateCategory(HelpCategory helpCategory);

    int insertCategory(HelpCategory helpCategory);

    HelpCategory getCategoryById(int categoryId);

    int updateCategoryStatus(HelpCategory helpCategory);

    List<HelpCategory> findCategoryFront(int fid);

}
