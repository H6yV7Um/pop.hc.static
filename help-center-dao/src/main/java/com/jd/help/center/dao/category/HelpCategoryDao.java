package com.jd.help.center.dao.category;

import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.category.query.HelpCategoryQuery;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-1
 * Time: 13:53:10
 * To change this template use File | Settings | File Templates.
 */
public interface HelpCategoryDao {

    List<HelpCategory> findCategoryAllById(int id);

    /**
     * 根据查询对象查询分类列表信息
     * @param helpCategoryQuery
     * @return
     */
    List<HelpCategory> findCategoryByQuery(HelpCategoryQuery helpCategoryQuery);

    int updateCategory(HelpCategory helpCategory);

    int insert(HelpCategory helpCategory);

    HelpCategory getCategoryById(int categoryId);

    int updateCategoryStatus(HelpCategory helpCategory);

    List<HelpCategory> findCategoryFront(int fid);

}
