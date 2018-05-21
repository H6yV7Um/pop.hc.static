package com.jd.help.center.dao.topic.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.center.dao.topic.HelpTopicDao;
import com.jd.help.center.domain.topic.HelpTopic;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-2
 * Time: 10:30:59
 * To change this template use File | Settings | File Templates.
 */
public class HelpTopicDaoImpl  extends BaseDao implements HelpTopicDao {
    public List<HelpTopic> findTopicByCategoryId(int categoryId) {
        return this.queryForList("HelpTopic.findTopicByCategoryId",categoryId);
    }

    public int insertTopic(HelpTopic helpTopic) {
        try {
            return Integer.parseInt(String.valueOf(insert("HelpTopic.insertTopic",helpTopic)));
        } catch (DataAccessException e) {
            log.error("HelpTopicDaoImpl.insert------>"+e.getMessage());
            return 0;
        }
    }

    public int updateTopic(HelpTopic helpTopic) {
        return this.update("HelpTopic.updateTopic",helpTopic);
    }

    public HelpTopic getTopicById(int id) {
        return (HelpTopic)queryForObject("HelpTopic.getTopicById",id);
    }

    public int updateTopicStatus(HelpTopic helpTopic) {
        return this.update("HelpTopic.updateTopicStatus",helpTopic);
    }

    public List<HelpTopic> findTopicFront(int categoryId) {
        return this.queryForList("HelpTopic.findTopicByCategoryFront",categoryId);
    }
}
