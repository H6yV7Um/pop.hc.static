package com.jd.help.center.manager.question.impl;

import com.jd.help.center.dao.question.HelpQuestionDao;
import com.jd.help.center.domain.question.HelpQuestion;
import com.jd.help.center.manager.question.HelpQuestionManager;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-2
 * Time: 10:56:14
 * To change this template use File | Settings | File Templates.
 */
public class HelpQuestionManagerImpl implements HelpQuestionManager {

    private HelpQuestionDao helpQuestionDao;

    public void setHelpQuestionDao(HelpQuestionDao helpQuestionDao) {
        this.helpQuestionDao = helpQuestionDao;
    }

    public List<HelpQuestion> findQuestionByTopicId(int id) {
        return this.helpQuestionDao.findQuestionByTopicId(id);
    }

    public int insertQuestion(HelpQuestion helpQuestion) {
        return this.helpQuestionDao.insertQuestion(helpQuestion);
    }

    public int updateQuestion(HelpQuestion helpQuestion) {
        return this.helpQuestionDao.updateQuestion(helpQuestion);
    }

    public HelpQuestion getQuestionById(int id) {
        return this.helpQuestionDao.getQuestionById(id);
    }

    public int updateQuestionStatus(HelpQuestion helpQuestion) {
        return this.helpQuestionDao.updateQuestionStatus(helpQuestion);
    }

    public int deleteQuestion(int questionId) {
        return this.helpQuestionDao.deleteQuestion(questionId);
    }

    public List<HelpQuestion> findQuestionFront(int topicId) {
        List<HelpQuestion> questions = this.helpQuestionDao.findQuestionFront(topicId);
        if (CollectionUtils.isNotEmpty(questions)) {
            for (HelpQuestion question : questions) {
                question.removeHttp();
            }
        }
        return questions;
    }
}
