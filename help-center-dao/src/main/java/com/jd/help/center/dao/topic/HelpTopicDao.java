package com.jd.help.center.dao.topic;

import com.jd.help.center.domain.topic.HelpTopic;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-2
 * Time: 10:28:00
 * To change this template use File | Settings | File Templates.
 */
public interface HelpTopicDao {

    List<HelpTopic> findTopicByCategoryId(int categoryId);

    int insertTopic(HelpTopic helpTopic);

    int updateTopic(HelpTopic helpTopic);

    HelpTopic getTopicById(int id);

    int updateTopicStatus(HelpTopic helpTopic);

    List<HelpTopic> findTopicFront(int categoryId);
}
