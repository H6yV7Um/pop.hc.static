package com.jd.help.service.impl;

import com.jd.common.util.StringUtils;
import com.jd.help.service.KarmaService;
import com.jd.karma.facade.*;
import com.jd.karma.facade.domain.ResultFacade;
import com.jd.karma.facade.domain.model.*;
import com.jd.karma.facade.domain.query.CategoryQueryFacade;
import com.jd.karma.facade.domain.query.NoticeQueryFacade;
import com.jd.karma.facade.domain.query.SceneQueryFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangmanliang on 2016/12/20.
 */
@Service
public class KarmaServiceImpl implements KarmaService {

    private static Logger logger= LoggerFactory.getLogger(KarmaServiceImpl.class);

    private static final Integer MEDIUM_CODE = 5;

    @Resource
    private KarmaCategoryService categoryService;

    @Resource
    private KarmaSceneSearchService sceneSearchService;

    @Resource
    private KarmaSceneSearchNewService sceneSearchNewService;

    @Resource
    private KarmaSceneService sceneService;

    @Resource
    private KarmaNoticeService karmaNoticeService;

    @Resource
    private KarmaSceneService karmaSceneService;

    @Override
    public List<CategoryFacade> getAllTopCategory() {

//        karmaSceneService.ge
        ResultFacade resultFacade = categoryService.getFirstLevelCategoryByMediumCode(MEDIUM_CODE);

        List<CategoryFacade> categoryFacadeList = new ArrayList<CategoryFacade>();

        if (resultFacade.isSuccess()) {//查询成功
            categoryFacadeList = (List<CategoryFacade>) resultFacade.getResultDto();
        }
        return categoryFacadeList;
    }

    /**
     * 自营商家查看分类规则
     *
     * @param categoryType 类目分类
     * @return List<CategoryFacade>
     */
    @Override
    public List<CategoryFacade> getAllTopCategoryByCategoryType(Short categoryType) {
        ResultFacade resultFacade = categoryService.getFirstLevelCategoryByMediumCodeAndCategoryType(MEDIUM_CODE, categoryType);
        List<CategoryFacade> categoryFacadeList = new ArrayList<CategoryFacade>();
        if (resultFacade.isSuccess()) {//查询成功
            categoryFacadeList = (List<CategoryFacade>) resultFacade.getResultDto();
        }
        return categoryFacadeList;
    }

    @Override
    public List<CategoryFacade> getSubcategory(Long parentId) {

        CategoryQueryFacade queryFacade = new CategoryQueryFacade();

        queryFacade.setMediumCode(MEDIUM_CODE);
        queryFacade.setParentId(parentId);
        ResultFacade resultFacade = categoryService.getSecondLevelCategory(queryFacade);

        List<CategoryFacade> categoryFacadeList = new ArrayList<CategoryFacade>();

        if (resultFacade.isSuccess()) {//查询成功
             categoryFacadeList.addAll((List<CategoryFacade>) resultFacade.getResultDto()) ;
        }

        return categoryFacadeList;
    }

    @Override
    public List<SceneFacade> listHotScenes() {
//        List<CategoryFacade> facades = getAllTopCategory();

        List<SceneFacade> sceneFacades = new ArrayList<SceneFacade>();
//        for (CategoryFacade categoryFacade : facades) {
//            if (categoryFacade.getHotSceneList() != null) {
//
//                for(SceneFacade facade:categoryFacade.getHotSceneList()){
//                    SceneFacade sceneFacade=findById(facade.getId());
//                    if(sceneFacade!=null){
//                        facade.setSceneTitle(findById(facade.getId()).getSceneTitle());
//                    }
//
//                }
//                sceneFacades.addAll(categoryFacade.getHotSceneList());
//            }
//        }
        ResultFacade resultFacade = karmaSceneService.getHotSceneInfo(MEDIUM_CODE);
        if (resultFacade.isSuccess()) {//查询成功
            sceneFacades = (List<SceneFacade>) resultFacade.getResultDto();
        }
        return sceneFacades;
    }

    /**
     * 自营商家查询热门规则
     *
     * @param categoryType 类目分类
     * @return List<SceneFacade>
     */
    @Override
    public List<SceneFacade> listHotScenesByCategoryType(Short categoryType){
        List<SceneFacade> sceneFacades = new ArrayList<SceneFacade>();
        ResultFacade resultFacade = karmaSceneService.getHotSceneInfoByCategoryType(MEDIUM_CODE, categoryType);
        if (resultFacade.isSuccess()) {//查询成功
            sceneFacades = (List<SceneFacade>) resultFacade.getResultDto();
        }
        return sceneFacades;
    }

    @Override
    public List<SceneFacade> specialScenes() {

        List<CategoryFacade> facades = getAllTopCategory();

        CategoryFacade specialCategory = null;
        for (CategoryFacade categoryFacade : facades) {
            if ("\u89C4\u5219\u4E13\u9898".equals(categoryFacade.getCategoryName())) {
                specialCategory = categoryFacade;
                break;
            }
        }
        if (specialCategory != null) {
            List<SceneFacade> sceneFacades=specialCategory.getHotSceneList();
            for(SceneFacade facade: specialCategory.getHotSceneList()){
                SceneFacade detail=findById(facade.getId());
                if(detail!=null){
                    facade.setSceneContent(detail.getSceneContent());
                    facade.setSceneTitle(detail.getSceneTitle());
                }

            }

            return specialCategory.getHotSceneList();
        }

        return new ArrayList<SceneFacade>();
    }

    @Override
    public SceneFacade findById(Long id) {

        SceneQueryFacade sceneQueryFacade = new SceneQueryFacade();

        sceneQueryFacade.setId(id);
        sceneQueryFacade.setMediumCode(MEDIUM_CODE);

        ResultFacade resultFacade = sceneService.getSceneDetail(sceneQueryFacade);

        if (resultFacade.isSuccess()) {
            return resultFacade.getResultDto() == null ? null : (SceneFacade) resultFacade.getResultDto();
        }
        return null;
    }

    @Override
    public NoticeFacade findNoticeById(Long id) {
        if (null == id) {
            return null;
        }
        NoticeQueryFacade noticeQueryFacade = new NoticeQueryFacade();

        noticeQueryFacade.setId(id);

        ResultFacade resultFacade = karmaNoticeService.getNoticeDetail(noticeQueryFacade);

        if (resultFacade.isSuccess()) {
            return resultFacade.getResultDto() == null ? null : (NoticeFacade) resultFacade.getResultDto();
        }
        return null;
    }

    @Override
    public List<SceneFacade> searchScene(String title) {

        SceneQueryFacade queryFacade = new SceneQueryFacade();
        queryFacade.setSceneTitle(title);
        queryFacade.setMediumCode(MEDIUM_CODE);

        ResultFacade resultFacade = sceneSearchService.searchScene(queryFacade);
        List<SceneFacade> facades=new ArrayList<SceneFacade>();
        if (resultFacade.isSuccess() && resultFacade.getResultDto() != null) {
            for(SearchSceneFacade sceneFacade: (List<SearchSceneFacade>) resultFacade.getResultDto()){
                SceneFacade sceneFacade1=new SceneFacade();
                sceneFacade1.setId(sceneFacade.getSceneId());
                sceneFacade1.setSceneTitle(sceneFacade.getSceneTitle());
                facades.add(sceneFacade1);
            }
            return facades;
        }
        return null;
    }

    public List<NoticeFacade> getAllNotice() {
        ResultFacade resultFacade = karmaNoticeService.getNoticeListByMediumCode(MEDIUM_CODE);

        if (resultFacade.isSuccess() && resultFacade.getResultDto() != null) {
            return (List<NoticeFacade>) resultFacade.getResultDto();
        }
        return null;
    }

    @Override
    public List<SceneFacade> listScenesByCategory(Long categoryId) {
        CategoryQueryFacade queryFacade=new CategoryQueryFacade();
        queryFacade.setMediumCode(MEDIUM_CODE);
        queryFacade.setId(categoryId);
        categoryService.getSecondLevelCategory(queryFacade);
        return null;
    }

    @Override
    public List<SceneFacade> perMonthRule() {
        String pattern="新规汇编";

        ResultFacade resultFacade = sceneSearchNewService.searchMonthRule(pattern);
        if (resultFacade.isSuccess() && resultFacade.getResultDto() != null) {
            List<SceneFacade> list = (List<SceneFacade>)resultFacade.getResultDto();
            return list;
        }
        return null;
    }

    @Override
    public List<SceneFacade> searchSceneNew(String title) {
        if(StringUtils.isEmpty(title)){
            return new ArrayList<SceneFacade>();
        }
        ResultFacade resultFacade = sceneSearchNewService.searchScence(title.trim());
        if (resultFacade.isSuccess() && resultFacade.getResultDto() != null) {
            List<SceneFacade> list = (List<SceneFacade>)resultFacade.getResultDto();
            return list;
        }
        return null;
    }
}
