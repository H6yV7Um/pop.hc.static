package com.jd.help.center.admin.question;

import com.jd.common.struts.action.BaseAction;
import com.jd.common.web.result.Result;
import com.jd.help.center.admin.base.HelpBaseAction;
import com.jd.help.center.domain.base.HelpCenterStringUtils;
import com.jd.help.center.domain.constants.HrmPurviewConstants;
import com.jd.help.center.domain.question.HelpQuestion;
import com.jd.help.center.domain.question.query.HelpQuestionQuery;
import com.jd.help.center.service.question.HelpQuestionService;
import com.jd.help.center.web.util.WebHelper;
import com.jd.uim.annotation.Authorization;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-2
 * Time: 18:25:43
 * To change this template use File | Settings | File Templates.
 */
public class HelpQuestionAction extends HelpBaseAction {

    private int categoryId;

    private int topicId;

    private int sysId;

    private HelpQuestion helpQuestion;

    private HelpQuestionQuery helpQuestionQuery;

    private Result result;

    private HelpQuestionService helpQuestionService;

    public String insert(){
        return SUCCESS;
    }

    public String update(){
        this.setResult(helpQuestionService.getQuestionById(helpQuestion.getQuestionId()));
        this.toVm(result);
        return SUCCESS;
    }


    @Authorization(HrmPurviewConstants.HELP_CENTER_QUESTION_LIST)
    public String findByTopicId(){
        setResult(helpQuestionService.findQuestionByTopicId(topicId));
        this.toVm(result);
        return SUCCESS;
    }

//    public String findByTopicIdShow(){
//        setResult(helpQuestionService.findQuestionByTopicId(topicId));
//        this.toVm(result);
//        return SUCCESS;
//    }
    @Authorization(HrmPurviewConstants.HELP_CENTER_QUESTION_UPDATE)
    public String doInsert(){
        helpQuestion.setCreator(WebHelper.getPin());
        helpQuestion.setModifier(WebHelper.getPin());
//        helpQuestion.setCreator("creator");
//        helpQuestion.setModifier("modifier");
        helpQuestion.setFeatures(helpQuestionQuery.convertProperty(helpQuestion).getFeatures());
        this.setResult(helpQuestionService.insertQuestion(helpQuestion));
        this.toVm(result);
        if(result.getSuccess()){
            addLog("添加问题，问题名称为---->"+helpQuestion.getQuestion());
            return "list";
        }
        return "toInsert";
    }


    @Authorization(HrmPurviewConstants.HELP_CENTER_QUESTION_UPDATE)
    public String updateQuestionStatus(){
        if(helpQuestion.getStatus()==1){
            helpQuestion.setStatus(0);
        }else{
            helpQuestion.setStatus(1);
        }
        helpQuestion.setTopicId(topicId);
        helpQuestion.setModifier(WebHelper.getPin());
        this.setResult(helpQuestionService.updateQuestionStatus(helpQuestion));
        this.toVm(result);
        if(result.getSuccess()){
            addLog("修改问题的状态，问题ID为--->"+helpQuestion.getQuestionId()+"问题的状态为--->"+ HelpCenterStringUtils.convertStatusInAction(helpQuestion.getStatus()));
            return "list";
        }
        return "toList";
    }


    @Authorization(HrmPurviewConstants.HELP_CENTER_QUESTION_UPDATE)
    public String doUpdate(){
        helpQuestionQuery.getHelpQuestion().setModifier(WebHelper.getPin());
//        helpQuestion.setModifier("modifier");
        helpQuestionQuery.getHelpQuestion().setTopicId(topicId);

        this.setResult(helpQuestionService.updateQuestion(helpQuestionQuery.convertProperty(helpQuestionQuery.getHelpQuestion())));
        this.toVm(result);
        if(result.getSuccess()){
            addLog("修改问题，问题名称为--->"+helpQuestionQuery.getHelpQuestion().getQuestion()+"问题ID为-->"+helpQuestionQuery.getHelpQuestion().getQuestionId());
            return "list";
        }
        return "toUpdate";
    }


    @Authorization(HrmPurviewConstants.HELP_CENTER_QUESTION_UPDATE)
    public String deleteQuestion(){
        this.setResult(helpQuestionService.deleteQuestion(helpQuestion.getQuestionId(),topicId));
        this.toVm(result);
        if(result.getSuccess()){
            addLog("删除问题，问题ID为--->"+helpQuestion.getQuestionId());
            return "list";
        }
        return "toList";
    }

    public void setHelpQuestionService(HelpQuestionService helpQuestionService) {
        this.helpQuestionService = helpQuestionService;
    }

    public HelpQuestion getHelpQuestion() {
        return helpQuestion;
    }

    public void setHelpQuestion(HelpQuestion helpQuestion) {
        this.helpQuestion = helpQuestion;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSysId() {
        return sysId;
    }

    public void setSysId(int sysId) {
        this.sysId = sysId;
    }

    public HelpQuestionQuery getHelpQuestionQuery() {return helpQuestionQuery;}

    public void setHelpQuestionQuery(HelpQuestionQuery helpQuestionQuery) {this.helpQuestionQuery = helpQuestionQuery;}
}
