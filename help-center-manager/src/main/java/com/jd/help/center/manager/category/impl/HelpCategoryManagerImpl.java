package com.jd.help.center.manager.category.impl;

import com.jd.help.center.dao.category.HelpCategoryDao;
import com.jd.help.center.dao.topic.HelpTopicDao;
import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.category.query.HelpCategoryQuery;
import com.jd.help.center.domain.topic.HelpTopic;
import com.jd.help.center.manager.category.HelpCategoryManager;
import com.jd.help.center.manager.category.LocalHelpCategoryManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-1
 * Time: 14:36:25
 * To change this template use File | Settings | File Templates.
 */
public class HelpCategoryManagerImpl implements HelpCategoryManager {
    Log log= LogFactory.getLog(HelpCategoryManagerImpl.class);

    private LocalHelpCategoryManager localHelpCategoryManager;

    private HelpCategoryDao helpCategoryDao;

    private HelpTopicDao helpTopicDao;

    public void setHelpCategoryDao(HelpCategoryDao helpCategoryDao) {
        this.helpCategoryDao = helpCategoryDao;
    }

    public void setHelpTopicDao(HelpTopicDao helpTopicDao) {
        this.helpTopicDao = helpTopicDao;
    }

    public List<HelpCategory> findCategoryAllById(int id) {
        try {
            return this.helpCategoryDao.findCategoryAllById(id);
        } catch (Exception e) {
            log.error("Method:findCategoryAllById------->"+e.getMessage());
        }
        return null;
    }

    public List<HelpCategory> findCategoryByQuery(HelpCategoryQuery helpCategoryQuery) {
            return this.helpCategoryDao.findCategoryByQuery(helpCategoryQuery);
    }

    public int updateCategory(HelpCategory helpCategory) {
        localHelpCategoryManager.getCategoryMap().put(helpCategory.getCategoryId(),helpCategory);
        return helpCategoryDao.updateCategory(helpCategory);
    }

    public int insertCategory(HelpCategory helpCategory) {
        return helpCategoryDao.insert(helpCategory);
    }

    public HelpCategory getCategoryById(int categoryId) {
        return this.helpCategoryDao.getCategoryById(categoryId);
    }

    public int updateCategoryStatus(HelpCategory helpCategory) {
        return helpCategoryDao.updateCategoryStatus(helpCategory);
    }

    public List<HelpCategory> findCategoryFront(int fid) {
        List<HelpCategory> list= null;
        try {
            list = this.helpCategoryDao.findCategoryFront(fid);
        } catch (Exception e) {
            log.error("Method:findCategoryFront------>"+e.getMessage());
        }
        for(HelpCategory category:list){
            List<HelpTopic> topics=new ArrayList<HelpTopic>();
            topics=this.helpTopicDao.findTopicFront(category.getCategoryId());
            category.setTopics(topics);
        }
        return list;
    }

    public void setLocalHelpCategoryManager(LocalHelpCategoryManager localHelpCategoryManager) {
        this.localHelpCategoryManager = localHelpCategoryManager;
    }
}
