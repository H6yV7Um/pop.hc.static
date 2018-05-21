package com.jd.help.center.manager.question;

import com.jd.help.center.domain.question.HelpQuestion;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-2
 * Time: 10:31:52
 * To change this template use File | Settings | File Templates.
 */
public interface HelpQuestionManager {
    
    List<HelpQuestion> findQuestionByTopicId(int id);

    int insertQuestion(HelpQuestion helpQuestion);

    int updateQuestion(HelpQuestion helpQuestion);

    HelpQuestion getQuestionById(int id);

    int updateQuestionStatus(HelpQuestion helpQuestion);

    int deleteQuestion(int questionId);

    List<HelpQuestion> findQuestionFront(int topicId);
}
