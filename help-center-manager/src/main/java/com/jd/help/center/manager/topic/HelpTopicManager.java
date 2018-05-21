package com.jd.help.center.manager.topic;

import com.jd.help.center.domain.topic.HelpTopic;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-2
 * Time: 10:56:42
 * To change this template use File | Settings | File Templates.
 */
public interface HelpTopicManager {

    List<HelpTopic> findTopicByCategoryId(int id);

    int insertTopic(HelpTopic helpTopic);

    int updateTopic(HelpTopic helpTopic);

    HelpTopic getTopicById(int id);

    int updateTopicStatus(HelpTopic helpTopic);
}
