package com.jd.help.utils;

import com.jd.businesscollege.shop.domain.BizcollOndemandCourseBean;
import com.jd.help.dao.IssueAnswerDao;
import com.jd.help.domain.*;
import com.jd.help.domain.knowledge.KnowledgeBean;
import com.jd.help.domain.knowledge.KnowledgeEsBean;
import com.jd.help.enums.KnowledgeBizTypesEnum;
import com.jd.help.enums.KnowledgeContentTypesEnum;
import com.jd.help.service.IssueSuggestService;
import com.jd.karma.facade.domain.transfer2Hc.Scene2Hc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by yfxialiang on 2018/4/25.
 */
@Service("knowledgeUtils")
public class KnowledgeUtils {

    @Autowired
    private IssueSuggestService issueSuggestService;

    @Autowired
    private IssueAnswerDao issueAnswerDao;

    public KnowledgeEsBean knowledge2KnowledgeEsBean(KnowledgeBean knowledge){
        KnowledgeEsBean esBean = new KnowledgeEsBean();
        if(knowledge == null){
            return null;
        }
        esBean.setBizId(knowledge.getBizId());
        esBean.setBizTypeId(knowledge.getBizTypeId());
        esBean.setBizTypeName(knowledge.getBizTypeName());
        esBean.setName(knowledge.getName());
        esBean.setSummary(knowledge.getSummary());
        esBean.setKeyword(knowledge.getKeyword());
        esBean.setLabel1Ids(knowledge.getLabel1Ids());
        esBean.setLabel1Names(knowledge.getLabel1Names());
        esBean.setLabel2Ids(knowledge.getLabel2Ids());
        esBean.setLabel2Names(knowledge.getLabel2Names());
        esBean.setUrl(knowledge.getUrl());
        esBean.setPicUrl(knowledge.getPicUrl());
        esBean.setContentType(knowledge.getContentType());
        esBean.setContent(knowledge.getContent());
        esBean.setUv(knowledge.getUv());
        esBean.setPv(knowledge.getPv());
        esBean.setSolveCount(knowledge.getSolveCount());
        esBean.setUnsolveCount(knowledge.getUnsolveCount());
        esBean.setStatus(knowledge.getStatus());
        esBean.setKnowledgeId(knowledge.getId());
        esBean.setCreateTime(knowledge.getCreateTime() == null ? null : knowledge.getCreateTime().getTime());
        esBean.setModifyTime(knowledge.getModifyTime() == null ? null : knowledge.getModifyTime().getTime());
        esBean.setValidTime(knowledge.getValidTime() == null ? null : knowledge.getValidTime().getTime());
        esBean.setExpTime(knowledge.getExpTime() == null ? null : knowledge.getExpTime().getTime());
        esBean.setCreated(knowledge.getCreated() == null ? null : knowledge.getCreated().getTime());
        esBean.setModified(knowledge.getModified() == null ? null : knowledge.getModified().getTime());
        return esBean;
    }

    public KnowledgeBean course2KnowledgeBean(BizcollOndemandCourseBean courseBean){
        KnowledgeBean knowledgeBean = new KnowledgeBean();
        if(courseBean == null){
            return null;
        }
        knowledgeBean.setBizId(courseBean.getCourseId());
        knowledgeBean.setBizTypeId(KnowledgeBizTypesEnum.xue.getCode());
        knowledgeBean.setBizTypeName(KnowledgeBizTypesEnum.xue.getDesc());
        knowledgeBean.setName(courseBean.getCourseName());
        knowledgeBean.setSummary(courseBean.getCourseOutline());
        knowledgeBean.setKeyword(courseBean.getCourseKeyword());
        //todo
//        knowledgeBean.setLabel1Ids(courseBean.get);
//        knowledgeBean.setLabel1Names();
//        knowledgeBean.setLabel2Ids();
//        knowledgeBean.setLabel2Names();
        knowledgeBean.setUrl("//xue.jd.com/ondemandCourse/getOndemandCourse.action?courseId="+courseBean.getCourseId());
        knowledgeBean.setPicUrl(courseBean.getCourseCoverUrl());
        knowledgeBean.setCreateTime(courseBean.getCreated());
        knowledgeBean.setModifyTime(courseBean.getModified());
        knowledgeBean.setContentType(KnowledgeContentTypesEnum.video.getCode());
        knowledgeBean.setUv(0);
//        knowledgeBean.setPv(courseBean.getPlayCount() == null ? 0 : courseBean.getPlayCount().intValue());
        knowledgeBean.setPv(0);
        knowledgeBean.setStatus(0);//0???งน
        knowledgeBean.setCreated(new Date());
        knowledgeBean.setModified(new Date());
        knowledgeBean.setContent(courseBean.getCourseDesc());
        return knowledgeBean;
    }

    public KnowledgeBean issue2KnowledgeBean(Issue issue){
        KnowledgeBean knowledgeBean = new KnowledgeBean();
        if(issue == null){
            return null;
        }
        knowledgeBean.setBizId((long)issue.getId());
        knowledgeBean.setBizTypeId(KnowledgeBizTypesEnum.hc.getCode());
        knowledgeBean.setBizTypeName(KnowledgeBizTypesEnum.hc.getDesc());
        knowledgeBean.setName(issue.getName());
        knowledgeBean.setSummary(issue.getSummary());
        knowledgeBean.setKeyword(issue.getIssueKeyWord());
        knowledgeBean.setLabel1Ids(issue.getLabel1Ids());
        knowledgeBean.setLabel1Names(issue.getLabel1Names());
        knowledgeBean.setLabel2Ids(issue.getIssueLabelId());
        knowledgeBean.setLabel2Names(issue.getIssueLabelContent());
        knowledgeBean.setUrl("//helpcenter.jd.com/vender/issue/"+issue.getCataId()+"-"+issue.getId()+".html");
        knowledgeBean.setCreateTime(issue.getCreated());
        knowledgeBean.setModifyTime(issue.getModified());
        knowledgeBean.setExpTime(issue.getIssueExpireTime());
        knowledgeBean.setContentType(issue.getIssueType());
        knowledgeBean.setUv(0);
        knowledgeBean.setPv(0);

        IssueSuggestQuery suggestQuery = new IssueSuggestQuery();
        suggestQuery.setIssueId(issue.getId());
        suggestQuery.setStatus(1);
        suggestQuery.setSolveStatus(1);
        knowledgeBean.setSolveCount(issueSuggestService.queryIssueSolveCount(suggestQuery));
        suggestQuery.setSolveStatus(0);
        knowledgeBean.setUnsolveCount(issueSuggestService.queryIssueSolveCount(suggestQuery));

        knowledgeBean.setStatus(0);
        knowledgeBean.setCreated(new Date());
        knowledgeBean.setModified(new Date());
        IssueAnswer issueAnswer = issueAnswerDao.queryOneByIssueId(issue.getId());
        knowledgeBean.setContent(issueAnswer == null ? null : HtmlUtil.Html2Text(issueAnswer.getAnswer()));
        return knowledgeBean;
    }


//    //??????scene?????KnowledgeBean
    public KnowledgeBean scene2KnowledgeBean(Scene2Hc scene){
        KnowledgeBean knowledgeBean = new KnowledgeBean();
        if(scene == null){
            return null;
        }
        knowledgeBean.setBizId(scene.getId());
        knowledgeBean.setBizTypeId(KnowledgeBizTypesEnum.karma.getCode());
        knowledgeBean.setBizTypeName(KnowledgeBizTypesEnum.karma.getDesc());
        knowledgeBean.setName(scene.getSceneTitle());
        knowledgeBean.setSummary(scene.getSummary());
        knowledgeBean.setKeyword(scene.getKeywords());
        knowledgeBean.setLabel1Ids(scene.getLabel1Ids());
        knowledgeBean.setLabel1Names(scene.getLabel1Names());
        knowledgeBean.setLabel2Ids(scene.getLabel2Ids());
        knowledgeBean.setLabel2Names(scene.getLabel2Names());
        knowledgeBean.setUrl(scene.getUrl());
        knowledgeBean.setCreateTime(scene.getCreateTime());
        knowledgeBean.setModifyTime(scene.getUpdateTime());
        knowledgeBean.setValidTime(scene.getValidTime());
        knowledgeBean.setContentType(KnowledgeContentTypesEnum.scene.getCode());
        knowledgeBean.setUv(scene.getUv());
        knowledgeBean.setPv(scene.getPv());
        knowledgeBean.setStatus(0);//0???งน
        knowledgeBean.setCreated(new Date());
        knowledgeBean.setModified(new Date());
        knowledgeBean.setContent(HtmlUtil.Html2Text(scene.getSceneContent()));
        return knowledgeBean;
    }

}
