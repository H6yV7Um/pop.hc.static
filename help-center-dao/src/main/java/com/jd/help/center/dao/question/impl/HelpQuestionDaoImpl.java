package com.jd.help.center.dao.question.impl;

import com.jd.common.dao.BaseDao;
import com.jd.help.center.dao.question.HelpQuestionDao;
import com.jd.help.center.domain.question.HelpQuestion;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-2
 * Time: 10:27:34
 * To change this template use File | Settings | File Templates.
 */
public class HelpQuestionDaoImpl extends BaseDao implements HelpQuestionDao {
    public List<HelpQuestion> findQuestionByTopicId(int topicId) {
        return this.queryForList("HelpQuestion.findQuestionByTopicId",topicId);
    }

    public int insertQuestion(HelpQuestion helpQuestion) {
        try {
             return Integer.parseInt(String.valueOf(insert("HelpQuestion.insertQuestion",helpQuestion)));
        } catch (DataAccessException e) {
            log.error("HelpQuestionDaoImpl.insert------>"+e.getMessage());
            return 0;
        }
    }

    public int updateQuestion(HelpQuestion helpQuestion) {
        return this.update("HelpQuestion.updateQuestion",helpQuestion);
    }

    public HelpQuestion getQuestionById(int id) {
        return (HelpQuestion)queryForObject("HelpQuestion.getQuestionById",id);
    }

    public int updateQuestionStatus(HelpQuestion helpQuestion) {
        return this.update("HelpQuestion.updateQuestionStatus",helpQuestion);
    }

    public int deleteQuestion(int questionId) {
        return this.delete("HelpQuestion.deleteQuestion",questionId);
    }

    public List<HelpQuestion> findQuestionFront(int topicId) {
        return this.queryForList("HelpQuestion.findQuestionByTopicFront",topicId);
    }
}
