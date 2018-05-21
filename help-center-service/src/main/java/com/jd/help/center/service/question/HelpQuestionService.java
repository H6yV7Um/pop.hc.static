package com.jd.help.center.service.question;

import com.jd.common.web.result.Result;
import com.jd.help.center.domain.question.HelpQuestion;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-2
 * Time: 10:57:22
 * To change this template use File | Settings | File Templates.
 */
public interface HelpQuestionService {

    Result insertQuestion(HelpQuestion helpQuestion);

    Result updateQuestion(HelpQuestion helpQuestion);

    Result findQuestionByTopicId(int topicId);

    Result getQuestionById(int id);

    Result updateQuestionStatus(HelpQuestion helpQuestion);

    Result deleteQuestion(int questionId,int topicId);

    String upLoadImage(File file);
}
