package com.jd.help.center.manager.topic.impl;

import com.jd.help.center.dao.topic.HelpTopicDao;
import com.jd.help.center.domain.topic.HelpTopic;
import com.jd.help.center.manager.topic.HelpTopicManager;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-2
 * Time: 10:56:58
 * To change this template use File | Settings | File Templates.
 */
public class HelpTopicManagerImpl implements HelpTopicManager {
    private HelpTopicDao helpTopicDao;

    public void setHelpTopicDao(HelpTopicDao helpTopicDao) {
        this.helpTopicDao = helpTopicDao;
    }

    public List<HelpTopic> findTopicByCategoryId(int id) {
        return this.helpTopicDao.findTopicByCategoryId(id);
    }

    public int insertTopic(HelpTopic helpTopic) {
        return this.helpTopicDao.insertTopic(helpTopic);
    }

    public int updateTopic(HelpTopic helpTopic) {
        return this.helpTopicDao.updateTopic(helpTopic);
    }

    public HelpTopic getTopicById(int id) {
        return this.helpTopicDao.getTopicById(id);
    }

    public int updateTopicStatus(HelpTopic helpTopic) {
        return this.helpTopicDao.updateTopicStatus(helpTopic);
    }
}
