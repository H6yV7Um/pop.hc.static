package com.jd.help.service;

import com.jd.common.util.PaginatedList;
import com.jd.karma.facade.domain.model.CategoryFacade;
import com.jd.karma.facade.domain.model.NoticeFacade;
import com.jd.karma.facade.domain.model.SceneFacade;
import com.jd.karma.facade.domain.model.SceneNewFacade;

import java.util.List;

/**
 * Created by zhangmanliang on 2016/12/20.
 */
public interface KarmaService {

    List<CategoryFacade> getAllTopCategory();

    List<CategoryFacade> getAllTopCategoryByCategoryType(Short categoryType);

    List<CategoryFacade> getSubcategory(Long parentId);

    /**
     * pop商家查询热门规则
     *
     * @return List<SceneFacade>
     */
    List<SceneFacade> listHotScenes();

    /**
     * 自营商家查询热门规则
     *
     * @return List<SceneFacade>
     */
    List<SceneFacade> listHotScenesByCategoryType(Short categoryType);

    List<SceneFacade> specialScenes();

    SceneFacade findById(Long id);

    NoticeFacade findNoticeById(Long id);

    List<SceneFacade> searchScene(String title);

    List<NoticeFacade> getAllNotice();

    List<SceneFacade> listScenesByCategory(Long superCategoryId);

    List<SceneFacade> perMonthRule();

    List<SceneFacade> searchSceneNew(String title);

}
