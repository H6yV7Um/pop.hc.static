package com.jd.help.es.impl;

import com.jd.common.util.PaginatedList;
import com.jd.common.util.StringUtils;
import com.jd.common.util.base.PaginatedArrayList;
import com.jd.help.domain.Issue;
import com.jd.help.domain.IssueVO;
import com.jd.help.es.IssueEsService;
import com.jd.help.es.dao.IssueEsDao;
import com.jd.help.es.domain.IssueEsPO;
import com.jd.help.es.domain.IssueEsQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author haoming1
 * @Description: es操作文章实现类
 * @Date Created in 15:37 2018/1/16
 * @Modified By:
 */
@Service("IssueEsService")
public class IssueEsServiceImpl implements IssueEsService {

    private static final Log log = LogFactory.getLog(IssueEsServiceImpl.class);

    @Resource
    private IssueEsDao issueEsDao;

    /**
     * 根据关键词查找文章
     * @param keyword
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PaginatedList searchArticle(String keyword, Integer pageIndex, Integer pageSize) {
        if(StringUtils.isEmpty(keyword)){
            log.error("【IssueEsServiceImpl.searchArticle】关键词为空");
            return (PaginatedList) Collections.emptyList();
        }

        PaginatedList<IssueVO> issues = new PaginatedArrayList<IssueVO>(pageIndex, pageSize);
        IssueEsQuery esQuery = new IssueEsQuery();
        esQuery.setKeyWords(keyword);

        //查询
        List<IssueEsPO> esPOList = issueEsDao.searchIssueByKeyword(esQuery,pageIndex,pageSize);
        //整理结果
        for (IssueEsPO esPO : esPOList) {
            IssueVO issueVO = new IssueVO();
            issueVO.setId(esPO.getArticleId().intValue());
            issueVO.setCataId(esPO.getLastCategoryId().intValue());
            issueVO.setName(esPO.getTitle());
            issueVO.setSummary(esPO.getContent());
            issueVO.setType(esPO.getType());
            issues.add(issueVO);
        }
        issues.setTotalItem(issueEsDao.countIssueByParam(esQuery));
        return issues;
    }

    /**
     * 搜索栏内容联想列表
     * @param keyword
     * @return
     */
    @Override
    public List searchArticleTitle(String keyword) {
        if(StringUtils.isEmpty(keyword)){
            log.error("【IssueEsServiceImpl.searchArticleTitle】关键词为空");
            return Collections.emptyList();
        }
        ArrayList<String> titles = new ArrayList<String>();
        IssueEsQuery esQuery = new IssueEsQuery();
        esQuery.setKeyWords(keyword);
        List<IssueEsPO> list = issueEsDao.searchNoPageIssueByKeyword(esQuery);
        for (IssueEsPO esPO : list) {
            titles.add(esPO.getTitle());
        }
        return titles;
    }

    //进行相关匹配
    @Override
    public List<Issue> getRel(IssueEsQuery query) {
        List<IssueEsPO> list = issueEsDao.getRel(query,0,5);
        ArrayList<Issue> issues = new ArrayList<Issue>();
        for (IssueEsPO esPO : list) {
            Issue issue = new Issue();
            issue.setName(esPO.getTitle());
            issue.setCataId(query.getCategoryId().intValue());
            issue.setId(esPO.getArticleId().intValue());
            issues.add(issue);
        }
        return issues;
    }

    @Override
    public PaginatedList<IssueVO> searchIssueByCategoryId(Long categoryId, Integer pageIndex, Integer pageSize) {
        if(categoryId == null){
            log.error("类目id为空");
            return (PaginatedList)Collections.emptyList();
        }
        PaginatedList<IssueVO> issues = new PaginatedArrayList<IssueVO>(pageIndex, pageSize);
        List<IssueEsPO> esPOList = issueEsDao.searchIssueByCategoryId(categoryId, pageIndex, pageSize);
        //整理结果
        for (IssueEsPO esPO : esPOList) {
            IssueVO issueVO = new IssueVO();
            issueVO.setId(esPO.getArticleId().intValue());
            issueVO.setCataId(esPO.getLastCategoryId().intValue());
            issueVO.setName(esPO.getTitle());
            issueVO.setSummary(esPO.getContent());
            issueVO.setType(esPO.getType());
            issues.add(issueVO);
        }
        IssueEsQuery esQuery = new IssueEsQuery();
        esQuery.setType(1);
        esQuery.setCategoryId(categoryId);
        issues.setTotalItem(issueEsDao.countIssueByParam(esQuery));
        return issues;
    }

    /**
     * 截取高亮第一次出现的前后40个字
     * @param content
     * @return
     */
    private String getHigntlightSummary(String content) {
        int preIndex = content.indexOf("<span style=\"color:red\">");//长度24
        int aftIndex = content.indexOf("</span>");//长度7
        if(preIndex==-1||aftIndex==-1){
            int end = content.length()>80? 80:content.length();
            return content.substring(0,end);
        }
        int start = 0;
        int end = content.length();
        if(preIndex>39){
            start=preIndex-40;
        }
        if(aftIndex+7+40<end){
            end=aftIndex+7+40;
        }
        return content.substring(start,end);
    }
}
