package com.jd.help.center.dao.question;

import com.jd.help.center.domain.question.HelpQuestion;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-2
 * Time: 10:19:18
 * To change this template use File | Settings | File Templates.
 */
public interface HelpQuestionDao {

    List<HelpQuestion> findQuestionByTopicId(int topicId);

    int insertQuestion(HelpQuestion helpQuestion);

    int updateQuestion(HelpQuestion helpQuestion);

    HelpQuestion getQuestionById(int id);

    int updateQuestionStatus(HelpQuestion helpQuestion);

    int deleteQuestion(int questionId);

    List<HelpQuestion> findQuestionFront(int topicId);
}
