package com.jd.help.customer.web;

import com.alibaba.fastjson.JSON;
import com.jd.common.util.PaginatedList;
import com.jd.common.util.StringUtils;
import com.jd.common.web.result.Result;
import com.jd.help.HelpBaseAction;
import com.jd.help.SysLoginContext;
import com.jd.help.customer.web.vo.FormVo;
import com.jd.help.service.KkongService;
import com.jd.pop.form.api.domain.status.FormType;
import com.jd.pop.form.api.open.dto.FormDTO;
import com.jd.pop.form.api.open.dto.FormElementDTO;
import com.jd.pop.form.api.open.query.FormQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by lipengfei5 on 2017/9/5.
 */
public class RuleReviewAction extends HelpBaseAction{
    private final static Logger log = LoggerFactory.getLogger(RuleReviewAction.class);
    @Resource
    private KkongService kkongService;
    private FormQuery formQuery;
    private Long reviewFormId;
    private String dataDetail;
    private Map<String,Object> jsonRoot = new HashMap<String, Object>();
    private String redirectUrl;
    private String domainName;
    private final String loginUrl="https://passport.jd.com/new/login.aspx?ReturnUrl=";
    private FormDTO preFormVo;
    private List<FormElementDTO> preFormElementVos;
    private String preForm;
    public String ruleReviewList(){
        Result result = new Result();
        Long venderId = SysLoginContext.getVenderId();
//        Long venderId = 23044L;//SysLoginContext.getVenderId();
        if(venderId == null){
            redirectUrl = loginUrl+"http://"+domainName+"/ruleReview/ruleReviewList.action";
            return "login";
        }
//        boolean b = kkongService.hasReviewAuth(reviewFormId, venderId + "");
//        if(!b){
//            return "illegal";
//        }
        if(formQuery == null){
            formQuery = new FormQuery();
        }
        formQuery.setType(FormType.ALL.getType());
        if(page<=0){
            page = 1;
        }
        log.error("vender_Id:{}",venderId);
        PaginatedList<FormDTO> reviewList = kkongService.getReviewList(formQuery, /*SysLoginContext.getVenderId()*/venderId + "", page, 12);
        result.addDefaultModel("reviewList",reviewList);
        if(formQuery.getFormStatus()!=null){
            result.addDefaultModel("formStatus",formQuery.getFormStatus());
        }
        toVm(result);
        return SUCCESS;
    }
    public String getReviewElements(){
        if(reviewFormId == null){
            return ERROR;
        }
        Result result = new Result();
        List<FormElementDTO> reviewElements = kkongService.getReviewElements(reviewFormId, SysLoginContext.getVenderId() + "");
        result.addDefaultModel("reviewElements",reviewElements);
        toVm(result);
        return SUCCESS;
    }
    public String ruleReviewContent(){
        if(reviewFormId == null){
            return ERROR;
        }
        Result result = new Result();
        Long venderId = SysLoginContext.getVenderId();
//        Long venderId = 23044L;//SysLoginContext.getVenderId();
        if(venderId == null){
            redirectUrl = loginUrl+"http://"+domainName+"/ruleReview/ruleReviewContent.action?reviewFormId="+reviewFormId;
            return "login";
        }
        boolean b = kkongService.hasReviewAuth(reviewFormId, venderId + "");
        if(!b){
            redirectUrl = "http://"+domainName+"/ruleReview/ruleReviewList.action";
            return "login";
        }
        List<FormElementDTO> reviewElements = kkongService.getReviewElements(reviewFormId, /*SysLoginContext.getVenderId()*/venderId + "");
        boolean hasReviewed = kkongService.hasReviewed(reviewFormId, venderId + "");
        Map<String, Object> reviewContent = null;
        Map<String, Long> reviewElementValueNumber = null;
        long reviewVoteNumber = 0;
        if(hasReviewed){
            reviewContent = kkongService.getReviewContent(reviewFormId, /*SysLoginContext.getVenderId()*/venderId + "");
            reviewVoteNumber = kkongService.getReviewVoteNumber(reviewFormId);
            reviewElementValueNumber = kkongService.getReviewElementValueNumber(reviewFormId, reviewContent);
        }
        log.error("hasReviewed:{}",hasReviewed);
        FormDTO review = kkongService.getReview(reviewFormId, venderId + "");
        kkongService.markRead(reviewFormId,venderId+"");
        result.addDefaultModel("review",review);
        result.addDefaultModel("reviewContent",reviewContent);
        result.addDefaultModel("reviewElements",reviewElements);
        result.addDefaultModel("reviewVoteNumber",reviewVoteNumber);
        result.addDefaultModel("reviewElementValueNumber",reviewElementValueNumber);
        result.addDefaultModel("hasReviewed",hasReviewed);
        toVm(result);
        return SUCCESS;
    }
    public String saveReviewContent(){
        if(reviewFormId == null || StringUtils.isBlank(dataDetail)){
            jsonRoot.put("resultMsg","保存内容不能为空");
            jsonRoot.put("isSuccess",false);
            return "json";
        }
        Long venderId = SysLoginContext.getVenderId();
//        Long venderId = 23044L;
        Result result = kkongService.saveReviewContent(reviewFormId, /*SysLoginContext.getVenderId()*/venderId + "", JSON.parseObject(dataDetail,Map.class));
        jsonRoot.put("resultMsg",result.get("resultMsg"));
        jsonRoot.put("isSuccess",result.isSuccess());
        return "json";
    }

    public String previewRuleReviewContent(){
        log.error("输出参数"+preForm);
        if(StringUtils.isBlank(preForm)){
            return ERROR;
        }
        Result result = new Result();
        try {
            preForm = URLDecoder.decode(preForm,"UTF-8");
            FormVo formVo = JSON.parseObject(preForm, FormVo.class);
            formVo.getForm().setStartTime(new Date());
            result.addDefaultModel("review",formVo.getForm());
            result.addDefaultModel("reviewElements",formVo.getFormElementList());
            result.addDefaultModel("hasReviewed",false);
            toVm(result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }
    public FormQuery getFormQuery() {
        return formQuery;
    }
    public void setFormQuery(FormQuery formQuery) {
        this.formQuery = formQuery;
    }

    public Long getReviewFormId() {
        return reviewFormId;
    }

    public void setReviewFormId(Long reviewFormId) {
        this.reviewFormId = reviewFormId;
    }

    public String getDataDetail() {
        return dataDetail;
    }

    public void setDataDetail(String dataDetail) {
        this.dataDetail = dataDetail;
    }

    public Map<String, Object> getJsonRoot() {
        return jsonRoot;
    }

    public void setJsonRoot(Map<String, Object> jsonRoot) {
        this.jsonRoot = jsonRoot;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getPreForm() {
        return preForm;
    }

    public void setPreForm(String preForm) {
        this.preForm = preForm;
    }

    public FormDTO getPreFormVo() {
        return preFormVo;
    }

    public void setPreFormVo(FormDTO preFormVo) {
        this.preFormVo = preFormVo;
    }

    public List<FormElementDTO> getPreFormElementVos() {
        return preFormElementVos;
    }

    public void setPreFormElementVos(List<FormElementDTO> preFormElementVos) {
        this.preFormElementVos = preFormElementVos;
    }
}
