package com.jd.help.center.admin.question;

import com.jd.common.struts.action.BaseAction;
import com.jd.help.center.domain.constants.HrmPurviewConstants;
import com.jd.help.center.service.question.HelpQuestionService;
import com.jd.uim.annotation.Authorization;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-7-9
 * Time: 17:12:10
 * To change this template use File | Settings | File Templates.
 */
public class UploadImgAction extends BaseAction {
    private final static Log log = LogFactory.getLog(UploadImgAction.class);
    private File filedata;

    private String filedataFileName;

    private HashMap imgUploadPath;
    private String msg;
    private String error;
    private HelpQuestionService helpQuestionService;

    @Authorization(HrmPurviewConstants.HELP_CENTER_QUESTION_UPDATE)
    public String uploadImage(){
        msg = helpQuestionService.upLoadImage(filedata);
        if(msg==null){
            error="UpLoad error!";
            log.error("upload Image error");
            return SUCCESS;
        }
        return SUCCESS;
    }

    public void setHelpQuestionService(HelpQuestionService helpQuestionService) {
        this.helpQuestionService = helpQuestionService;
    }

    public String getFiledataFileName() {
        return filedataFileName;
    }

    public void setFiledataFileName(String filedataFileName) {
        this.filedataFileName = filedataFileName;
    }

    public void setImgUploadPath(HashMap imgUploadPath) {
        this.imgUploadPath = imgUploadPath;
    }

    public File getFiledata() {
        return filedata;
    }

    public void setFiledata(File filedata) {
        this.filedata = filedata;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public String getError() {
        return error;
    }
}
