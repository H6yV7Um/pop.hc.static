package com.jd.help.center.service.topic;

import com.jd.common.web.result.Result;
import com.jd.help.center.domain.topic.HelpTopic;
import com.jd.help.center.domain.topic.HelpTopicVO;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-2
 * Time: 10:57:51
 * To change this template use File | Settings | File Templates.
 */
public interface HelpTopicService {

    Result insertTopic(HelpTopicVO helpTopicVO);

    Result updateTopic(HelpTopicVO helpTopicVO);

    Result findTopicByCategoryId(int categoryId);

    Result getTopicById(int id);

    Result updateTopicStatus(HelpTopic helpTopic);

    /**
     * É¾³ý
     * @param helpTopic
     * @return
     */
    Result deleteTopic(HelpTopic helpTopic);
}
