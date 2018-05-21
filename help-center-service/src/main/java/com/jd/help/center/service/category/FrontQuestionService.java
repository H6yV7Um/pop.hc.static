package com.jd.help.center.service.category;

import com.jd.common.web.result.Result;
import com.jd.help.center.domain.HelpCenterLeftNavigate;
import com.jd.help.center.domain.question.HelpQuestion;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-12
 * Time: 11:24:16
 * To change this template use File | Settings | File Templates.
 */
public interface FrontQuestionService {

    Result getQuestion(String sysName, int topicId);

    Result previewQuestion(int topicId);


    public List<HelpQuestion> getHelpQuestion(int topicId);


}
