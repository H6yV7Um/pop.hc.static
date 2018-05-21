package com.jd.help.service.impl;

import com.jd.common.util.PaginatedList;
import com.jd.common.util.StringUtils;
import com.jd.common.util.base.PaginatedArrayList;
import com.jd.common.web.result.Result;
import com.jd.help.service.KkongService;
import com.jd.pop.form.api.domain.client.ClientSource;
import com.jd.pop.form.api.domain.status.FormType;
import com.jd.pop.form.api.exception.FormException;
import com.jd.pop.form.api.open.dto.FormDTO;
import com.jd.pop.form.api.open.dto.FormElementDTO;
import com.jd.pop.form.api.open.query.FormQuery;
import com.jd.pop.form.api.open.service.FormReadService;
import com.jd.pop.form.api.open.service.FormWriteService;
import com.jd.pop.form.api.open.service.analysis.FormReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lipengfei5 on 2017/9/5.
 */
@Service("kkongService")
public class KkongServiceImpl implements KkongService {
    private final static String appName = "vender_help_center";
    private final static String userType = "ven_";
    @Resource
    private FormReadService formReadService;
    @Resource
    private FormWriteService formWriteService;
    @Resource
    private FormReportService formReportServiceOpen;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public Result saveReviewContent(Long reviewFormId, String loginUser, Map<String, Object> dataDetail) {
       Result result = new Result(false);
        if(reviewFormId == null){
            result.addDefaultModel("resultMsg","参数为空");
            return result;
        }
        try {
            formWriteService.saveData(getClientSource(),reviewFormId,userType,loginUser,dataDetail);
            result.setSuccess(true);
            result.addDefaultModel("resultMsg","保存成功");
            return result;
        } catch (FormException e){
            result.addDefaultModel("resultMsg",e.getMsg());
            return result;
        }catch (Exception e) {
            logger.error("保存失败reviewFormId:{}",reviewFormId,e);
            result.addDefaultModel("resultMsg","系统繁忙，请稍后重试");
            return result;
        }
    }

    @Override
    public PaginatedList<FormDTO> getReviewList(FormQuery query, String loginUser, int pageNo, int pageSize) {
        if(StringUtils.isBlank(loginUser)){
            return new PaginatedArrayList<FormDTO>();
        }
        try {
            query.setType(FormType.RULEREVIEW.getType());
            query.setModuleType(2);
            PaginatedList<FormDTO> list = new PaginatedArrayList<FormDTO>(pageNo,pageSize);
            Long signUpTotalNumber = formReadService.getSignUpTotalNumber(getClientSource(), query, userType, loginUser);
            List<FormDTO> signUpList = formReadService.getSignUpList(getClientSource(), query, userType, loginUser, pageNo, pageSize);
            list.addAll(signUpList);
            list.setTotalItem(signUpTotalNumber.intValue());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询评审列表失败",e);
            return new PaginatedArrayList<FormDTO>();
        }
    }

    @Override
    public Long getReviewTotalNumber(FormQuery query, String loginUser) {
        try {
            query.setType(FormType.RULEREVIEW.getType());
            query.setModuleType(2);
            return formReadService.getSignUpTotalNumber(getClientSource(),query,userType,loginUser);
        } catch (Exception e) {
            logger.error("查询评审总数",e);
            return -1L;
        }
    }

    @Override
    public List<FormElementDTO> getReviewElements(Long reviewFormId,String loginUser){
        try {
            return formReadService.getElements(getClientSource(),reviewFormId,userType,loginUser);
        } catch (Exception e) {
            logger.error("查询评审表单问题列表",e);
            return new ArrayList<FormElementDTO>();
        }
    }

    @Override
    public Map<String, Object> getReviewContent(Long reviewFormId, String loginUser) {
        try {
            return formReadService.getData(getClientSource(), reviewFormId, userType, loginUser);
        } catch (Exception e) {
            logger.error("查询用户评审内容",e);
            return new HashMap<String,Object>();
        }
    }

    @Override
    public boolean hasReviewed(Long reviewFormId, String loginUser) {
        try {
            return formReadService.hasFillData(getClientSource(), reviewFormId, userType, loginUser);
        } catch (Exception e) {
            logger.error("用户是否已经评审过",e);
            return false;
        }
    }
    @Override
    public FormDTO getReview(Long reviewFormId, String loginUser){
        try {
            return formReadService.get(getClientSource(), reviewFormId, userType, loginUser);
        } catch (Exception e) {
            logger.error("获取评审项异常",e);
            return new FormDTO();
        }
    }

    public long getReviewVoteNumber(Long reviewFormId){
        try {
            return formReportServiceOpen.getVoteNumber(getClientSource(),reviewFormId);
        } catch (Exception e) {
            logger.error("获取投票总数量异常",e);
            return 0;
        }
    }

    public Map<String,Long> getReviewElementValueNumber(Long reviewFormId,Map<String, Object> reviewContent){
        try {
            Map<String,Long> resultMap = new HashMap<String, Long>();
            for(Map.Entry<String, Object> entry : reviewContent.entrySet()){
                long elementValueNumber = formReportServiceOpen.getElementValueNumber(getClientSource(), reviewFormId, entry.getKey(), String.valueOf(entry.getValue()));
                resultMap.put(entry.getKey(),elementValueNumber);
            }
            return resultMap;
        } catch (Exception e) {
            logger.error("获取某个选项的投票数量失败",e);
            return new HashMap<String, Long>();
        }
    }

    public boolean hasReviewAuth(Long reviewFormId,String loginUser){
        if(reviewFormId == null || StringUtils.isBlank(loginUser)){
            return false;
        }
        try {
            return formReadService.hasAuth(getClientSource(),reviewFormId,userType,loginUser);
        } catch (Exception e) {
            logger.error("查询用户评审权限接口异常",e);
            return false;
        }
    }
    public boolean markRead(Long reviewFormId,String loginUser){
        try {
            return formReadService.markRead(getClientSource(),reviewFormId,userType,loginUser);
        } catch (Exception e) {
            logger.error("用户标记为已读接口异常",e);
            return false;
        }
    }
    private ClientSource getClientSource(){
        ClientSource clientSource = new ClientSource();
        clientSource.setAppName(appName);
        return clientSource;
    }
}
