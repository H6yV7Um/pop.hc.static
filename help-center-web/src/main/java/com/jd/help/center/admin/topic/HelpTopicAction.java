package com.jd.help.center.admin.topic;

import com.jd.common.struts.action.BaseAction;
import com.jd.common.web.result.Result;
import com.jd.help.center.admin.base.HelpBaseAction;
import com.jd.help.center.domain.base.HelpCenterStringUtils;
import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.constants.HrmPurviewConstants;
import com.jd.help.center.domain.topic.HelpTopic;
import com.jd.help.center.domain.topic.HelpTopicVO;
import com.jd.help.center.service.topic.HelpTopicService;
import com.jd.uim.annotation.Authorization;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-2
 * Time: 18:26:12
 * To change this template use File | Settings | File Templates.
 */
public class HelpTopicAction extends HelpBaseAction {
    private static final Log log= LogFactory.getLog(HelpTopicAction.class);

    private int categoryId;

    private int sysId;

    private HelpCategory helpCategory;

    private HelpTopic helpTopic;

    private HelpTopicVO helpTopicVO;

    private Result result;

    private HelpTopicService helpTopicService;


    @Authorization(HrmPurviewConstants.HELP_CENTER_TOPIC_LIST)
    public String  findByCategoryId(){
        setResult(helpTopicService.findTopicByCategoryId(categoryId));
        this.toVm(result);
        return SUCCESS;
    }


    @Authorization(HrmPurviewConstants.HELP_CENTER_TOPIC_UPDATE)
    public String updateTopicStatus(){
        if(helpTopic.getStatus()==1){
            helpTopic.setStatus(0);
        }else{
            helpTopic.setStatus(1);
        }
        this.setResult(helpTopicService.updateTopicStatus(helpTopic));
        this.toVm(result);
        if(result.getSuccess()){
            addLog("修改Topic状态，TopicID为-->"+helpTopic.getTopicId()+"状态改为-->"+ HelpCenterStringUtils.convertStatusInAction(helpTopic.getStatus()));
            return "list";
        }
        return "toList";
    }

    public String update(){
        this.setResult(helpTopicService.getTopicById(helpTopic.getTopicId()));
        this.toVm(result);
        return SUCCESS;
    }

    public String insert(){
        return SUCCESS;
    }


    @Authorization(HrmPurviewConstants.HELP_CENTER_TOPIC_UPDATE)
    public String doInsert(){
        this.setResult(helpTopicService.insertTopic(helpTopicVO));
        this.toVm(result);
        if(result.getSuccess()){
            addLog("添加Topic，Topic的名称为---->"+helpTopicVO.getName());
            return "list";
        }
        return "toInsert";
    }


    @Authorization(HrmPurviewConstants.HELP_CENTER_TOPIC_UPDATE)
    public String doUpdate(){
        helpTopicVO.setCategoryId(categoryId);
        setResult(helpTopicService.updateTopic(helpTopicVO));
        this.toVm(result);
        if(result.getSuccess()){
            addLog("修改Topic，Topic名为-->"+helpTopicVO.getName()+"系统ID为-->"+helpTopicVO.getTopicId());
            return "list";
        }
        return "toUpdate";
    }

    @Authorization(HrmPurviewConstants.HELP_CENTER_TOPIC_UPDATE)
    public String delete(){
        setResult(helpTopicService.deleteTopic(helpTopic));
        toVm(result);
        if(result.getSuccess()){
            addLog("删除Topic，Topic名为-->"+helpTopic.getName()+"topicID为-->"+helpTopic.getTopicId());
            return "list";
        }
        return "toList";
    }

    public HelpCategory getHelpCategory() {
        return helpCategory;
    }

    public void setHelpCategory(HelpCategory helpCategory) {
        this.helpCategory = helpCategory;
    }

    public HelpTopic getHelpTopic() {
        return helpTopic;
    }

    public void setHelpTopic(HelpTopic helpTopic) {
        this.helpTopic = helpTopic;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setHelpTopicService(HelpTopicService helpTopicService) {
        this.helpTopicService = helpTopicService;
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

    public HelpTopicVO getHelpTopicVO() {
        return helpTopicVO;
    }

    public void setHelpTopicVO(HelpTopicVO helpTopicVO) {
        this.helpTopicVO = helpTopicVO;
    }
}
