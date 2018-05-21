package com.jd.help.center.service.category;

import com.jd.common.web.result.Result;
import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.category.query.HelpCategoryQuery;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-1
 * Time: 14:39:44
 * To change this template use File | Settings | File Templates.
 */
public interface HelpCategoryService {

    Result findCategoryAllById(int id);

    /**
     * 根据查询条件查询分类列表
     * @param helpCategoryQuery
     * @return
     */
    Result findCategoryByQuery(HelpCategoryQuery helpCategoryQuery);

    Result updateCategory(HelpCategory helpCategory);

    Result insertCategory(HelpCategory helpCategory);

    Result getCategoryById(int categoryId);

    Result updateCategoryStatus(HelpCategory helpCategory);

    /**
     * 删除
     * @param helpCategory
     * @return
     */
    Result deleteCategory(HelpCategory helpCategory);

//    Result findCategoryFront();
}
