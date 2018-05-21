package com.jd.help.center.service.question.impl;

import com.jd.common.web.result.Result;
import com.jd.help.center.domain.category.HelpCategory;
import com.jd.help.center.domain.constants.ImageConstants;
import com.jd.help.center.domain.helpsys.HelpSYS;
import com.jd.help.center.domain.question.HelpQuestion;
import com.jd.help.center.domain.question.query.HelpQuestionQuery;
import com.jd.help.center.domain.topic.HelpTopic;
import com.jd.help.center.manager.category.LocalHelpCategoryManager;
import com.jd.help.center.manager.question.HelpQuestionManager;
import com.jd.help.center.service.question.HelpQuestionService;
import com.jd.image.common.ImageUpload;
import com.jd.image.model.Message;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-2
 * Time: 10:57:34
 * To change this template use File | Settings | File Templates.
 */
public class HelpQuestionServiceImpl implements HelpQuestionService {
    private static final Log log= LogFactory.getLog(HelpQuestionServiceImpl.class);
    private static String CACHE_NAME="helpCenter_";
    private String UPLOAD_SUCCESS = "1";
    private HashMap<String, String> imgUploadPath;
    private HelpQuestionManager helpQuestionManager;
    private LocalHelpCategoryManager localHelpCategoryManager;
    public String urlHead = "//helpcenter.jd.com";


    public void setHelpQuestionManager(HelpQuestionManager helpQuestionManager) {
        this.helpQuestionManager = helpQuestionManager;
    }

    public Result insertQuestion(HelpQuestion helpQuestion) {
        Result result =new Result();
        try {
            int i=helpQuestionManager.insertQuestion(helpQuestion);
            if(i>0){
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
                result.setResultCode("info.question.insert.error");
            }
        } catch (Exception e) {
            log.error("Method:insertQuestion------>"+e.getMessage());
            result.setSuccess(false);
            result.setResultCode("info.question.insert.error");
        }
        return result;
    }

    public Result updateQuestion(HelpQuestion helpQuestion) {
        Result result =new Result();
        try {
            int i=helpQuestionManager.updateQuestion(helpQuestion);
            if(i>0){
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
                result.setResultCode("info.question.update.error");
            }
        } catch (Exception e) {
            log.error("Method:updateQuestion----->"+e.getMessage());
            result.setSuccess(false);
            result.setResultCode("info.question.update.error");
        }
        return result;
    }

    public Result findQuestionByTopicId(int topicId) {
        Result result=new Result();
        try {
            List<HelpQuestion> list=helpQuestionManager.findQuestionByTopicId(topicId);
            result.setSuccess(true);
            if(list.size()>0){
                HelpTopic helpTopic = localHelpCategoryManager.getHelpTopicByTopicId(topicId);
                if(helpTopic!=null){
                    HelpCategory helpCategory =localHelpCategoryManager.getHelpCategoryByCategoryId(helpTopic.getCategoryId());
                    if(helpCategory!=null){
                        HelpSYS helpSYS = localHelpCategoryManager.getHelpSYSById(helpCategory.getFid());
                        for (int i = 0; i < list.size(); i++) {
                             list.get(i).setAnchorUrl(generateUrl(list.get(i),helpSYS.getName()));
                        }
                    }
                    result.addDefaultModel("questions",list);
                }
            }else{
                result.setResultCode("info.question.load.error");
            }
        } catch (Exception e) {
            log.error("Method:findQuestionByTopicId------>"+e.getMessage());
            result.setSuccess(false);
            result.setResultCode("info.question.load.error");
        }
        return result;
    }

    public Result getQuestionById(int id){
        Result result =new Result();
        try {
            HelpQuestion helpQuestion=helpQuestionManager.getQuestionById(id);
            HelpQuestionQuery helpQuestionQuery = new HelpQuestionQuery(helpQuestion);
            result.setSuccess(true);
            if(helpQuestion!=null){
                result.addDefaultModel("helpQuestionQuery",helpQuestionQuery);
            }else{
                result.setResultCode("info.question.load.error");
            }
        } catch (Exception e) {
            log.error("Method:getQuestionById------>"+e.getMessage());
            result.setSuccess(false);
            result.setResultCode("info.question.load.error");
        }
        return result;
    }

    public Result updateQuestionStatus(HelpQuestion helpQuestion) {
        Result result =new Result();
        try {
            int i=helpQuestionManager.updateQuestionStatus(helpQuestion);
            if(i>0){
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
                result.setResultCode("info.question.load.error");
            }
        } catch (Exception e) {
            log.error("Method:updateQuestionStatus----->"+e.getMessage());
            result.setSuccess(false);
            result.setResultCode("info.question.update.error");
        }
        return result;
    }

    public Result deleteQuestion(int questionId,int topicId) {
        Result result=new Result();
        try {
            int i=helpQuestionManager.deleteQuestion(questionId);
            if(i>0){
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
                result.setResultCode("info.question.update.error");
            }
        } catch (Exception e) {
            log.error("Method:deleteQuestion---->"+e.getMessage());
            result.setSuccess(false);
            result.setResultCode("info.question.update.error");
        }
        return result;
    }

    public String upLoadImage(File file) {
        String imageKey = (String)imgUploadPath.get(ImageConstants.HELP_CENTER_IMAGE_KEY);
        String basePath = (String)imgUploadPath.get(ImageConstants.HELP_CENTER_IMAGE_HOST);
        String path = null;
        try {
            path = ImageUpload.uploadFile(file, imageKey);
        } catch (Exception e) {
            log.error("ImageUpload.uploadFile error--->",e);
        }
        if(StringUtils.isNotBlank(path)){
            JSONArray array = JSONArray.fromObject(path);
            JSONObject jsonObject = (JSONObject)array.iterator().next();
            Message message =(Message)JSONObject.toBean(jsonObject, Message.class);
            if(message != null && UPLOAD_SUCCESS.equals(message.getId())){
                return basePath + message.getMsg();
            }
            log.error("ImageUpload.uploadFile error return:"+path);
        }
        return null;
    }

    private String generateUrl(HelpQuestion helpQuestion,String systemName){
        String anchorUrlInfo = "/" + systemName + "/question-" + helpQuestion.getTopicId() + ".html#help" + helpQuestion.getQuestionId();
        String anchorUrl = "#";
        anchorUrl = urlHead + anchorUrlInfo;
        return anchorUrl;
    }

    public HashMap<String, String> getImgUploadPath() {
        return imgUploadPath;
    }

    public void setImgUploadPath(HashMap<String, String> imgUploadPath) {
        this.imgUploadPath = imgUploadPath;
    }

    public void setLocalHelpCategoryManager(LocalHelpCategoryManager localHelpCategoryManager) {
        this.localHelpCategoryManager = localHelpCategoryManager;
    }

    public void setUrlHead(String urlHead) {this.urlHead = urlHead;}
}
