package com.jd.help.dao.issue.search;

import com.jd.help.dao.CatalogDao;
import com.jd.help.dao.IssueAnswerDao;
import com.jd.help.dao.IssueDao;
import com.jd.help.domain.Catalog;
import com.jd.help.domain.Issue;
import com.jd.help.domain.IssueAnswer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 问题索引器
 *
 * @author laichendong
 * @since 2014-12-8 17:23
 */
@Component("issueIndexer")
public class IssueIndexer {

    static final Log log = LogFactory.getLog(IssueIndexer.class);

    @Resource
    private CatalogDao catalogDao;
    @Resource
    private IssueDao issueDao;
    @Resource
    private IssueAnswerDao issueAnswerDao;
    @Resource
    private HttpSolrServer solrServer;

    public IssueIndex issueToIssueIndex(Issue issue) {
        if (issue == null) {
            return null;
        }
        IssueIndex issueIndex = new IssueIndex();
        issueIndex.setId(issue.getId());
        if (issue.getCataId() != null && issue.getCataId() > 0) {
            issueIndex.setCataId(makeCatalogIdString(issue.getCataId()));
        }
        issueIndex.setSiteId(issue.getSiteId());
        issueIndex.setName(issue.getName());
        issueIndex.setStatus(issue.getStatus());
        issueIndex.setModified(issue.getModified());
        issueIndex.setSummary(issue.getSummary());
        issueIndex.setOrderPay(issue.getOrderPay());
        issueIndex.setOrderShipment(issue.getOrderShipment());
        issueIndex.setOrderStatus(issue.getOrderStatus());
        issueIndex.setOrderType(issue.getOrderType());
        issueIndex.setCreator(issue.getCreator());
        issueIndex.setSuggestName(issue.getName());
        return issueIndex;
    }

    private String makeCatalogIdString(Integer cataId) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Integer> cataIds = new ArrayList<Integer>();
        make(cataIds, cataId);
        for (int i = cataIds.size() - 1; i >= 0; i--) {
            stringBuilder.append(cataIds.get(i)).append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private void make(List<Integer> cataIds, Integer cataId) {
        Catalog param = new Catalog();
        param.setId(cataId);
        cataIds.add(cataId);
        Catalog obj = catalogDao.queryForObject(param);
        if (obj != null && obj.getPid() != null && obj.getPid() > 0) {
            make(cataIds, obj.getPid());
        }
    }

    /**
     * 建立全量索引
     */
    public void indexAll() {
        log.info("create all of issue's index start.");
        Issue queryParam = new Issue();
        int numberIssueToIndex = issueDao.queryForCount(queryParam); // 将要被索引的问题数
        log.info(String.format("%d issue will be indexed!", numberIssueToIndex));
        int pageSize = 50;
        int largestPageNumber = (numberIssueToIndex + pageSize - 1 )/ pageSize;
        for (int pageNumber = 1; pageNumber <= largestPageNumber; pageNumber++) {
            List<Issue> issues = issueDao.queryForList(queryParam, pageNumber, pageSize);
            List<IssueIndex> issueIndexList = new ArrayList<IssueIndex>(pageSize);
            for (int i = 0; i < issues.size(); i++) {
                IssueIndex issueIndex = issueToIssueIndex(issues.get(i));
                IssueAnswer param = new IssueAnswer();
                param.setIssueId(issueIndex.getId());
                IssueAnswer issueAnswer = issueAnswerDao.queryForObject(param);
                if (issueAnswer != null && StringUtils.isNotBlank(issueAnswer.getAnswer())) {
                    issueIndex.setAnswer(issueAnswer.getAnswer());
                }
                log.debug(String.format("index data : %s", issueIndex));
                issueIndexList.add(issueIndex);
            }
            if (CollectionUtils.isNotEmpty(issueIndexList)) {
                try {
                    solrServer.addBeans(issueIndexList);
                } catch (SolrServerException e) {
                    log.fatal("create issue's index error!", e);
                } catch (IOException e) {
                    log.fatal("create issue's index error!", e);
                }
            }
        }

        log.info("create all of issue's index end.");
    }

    /**
     * 创建单个索引
     *
     * @param issueId
     */
    public void indexOne(long issueId) {
        log.info(String.format("issue[%s] will be indexed.", issueId));
        Issue param = new Issue();
        param.setId((int) issueId);
        Issue issue = issueDao.queryForObject(param);
        if (issue != null) {
            IssueIndex issueIndex = issueToIssueIndex(issue);
            IssueAnswer answerParam = new IssueAnswer();
            answerParam.setIssueId(issueIndex.getId());
            IssueAnswer issueAnswer = issueAnswerDao.queryForObject(answerParam);
            if (issueAnswer != null && StringUtils.isNotBlank(issueAnswer.getAnswer())) {
                issueIndex.setAnswer(issueAnswer.getAnswer());
            }
            log.debug(String.format("index data : %s", issueIndex));
            try {
                solrServer.addBean(issueIndex);
            } catch (SolrServerException e) {
                log.fatal("create issue's index error!", e);
            } catch (IOException e) {
                log.fatal("create issue's index error!", e);
            }
        }
        log.info(String.format("issue[%s] is indexed.", issueId));
    }


}
