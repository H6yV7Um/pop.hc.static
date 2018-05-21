package com.jd.help.es.search;


import com.alibaba.fastjson.JSON;
import com.jd.help.dao.IssueAnswerDao;
import com.jd.help.dao.IssueDao;
import com.jd.help.domain.Issue;
import com.jd.help.domain.IssueAnswer;
import com.jd.help.es.dao.EsClientGeter;
import com.jd.help.es.dao.utils.HtmlUtils;
import com.jd.help.es.domain.IssueEsPO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author haoming1
 * @Description: es文章插入类
 * @Date Created in 11:53 2018/1/18
 * @Modified By:
 */
@Component("IssueEsIndexer")
public class IssueEsIndexer {

    private static final Log log = LogFactory.getLog(IssueEsIndexer.class);
    @Resource
    private IssueDao issueDao;

    @Resource
    private IssueAnswerDao issueAnswerDao;

    @Resource
    private EsClientGeter geter;

    private final String ID_TYPE = "1";//数据来源商家帮助中心
    private final String INDEX = "help_center_vender";//es表index
    private final String TYPE = "article";

    private String index;
    /**
     * 将issue对象转化为es存储对象
     * @param issue
     * @return
     */
    private IssueEsPO issueToIssueEsPO(Issue issue) {
        if (issue == null) {
            return null;
        }
        IssueEsPO issueEsPO = new IssueEsPO();
        issueEsPO.setId(issue.getId()+"_"+ID_TYPE);
        if (issue.getCataId() != null && issue.getCataId() > 0) {
            issueEsPO.setLastCategoryId(new Long(issue.getCataId()));
        }
        issueEsPO.setTitle(issue.getName());
        issueEsPO.setArticleId((long)issue.getId());
        issueEsPO.setStatus(issue.getStatus());
        issueEsPO.setType(1);
        return issueEsPO;
    }

    /**
     * 向es整体更新或整体插入数据
     * @param issueId
     */
    public void indexOne(long issueId){
        Issue param = new Issue();
        param.setId((int) issueId);
        // 这里或许不应该再次查询了。
        Issue issue = issueDao.queryForObject(param);
        if (issue != null&&issue.getStatus()!=-1) {
            //转化为es储存对象
            IssueEsPO issueEsIndex = issueToIssueEsPO(issue);
            //查询文本内容
            IssueAnswer answerParam = new IssueAnswer();
            answerParam.setIssueId((int)issueId);
            IssueAnswer issueAnswer = issueAnswerDao.queryForObject(answerParam);
            if (issueAnswer != null && StringUtils.isNotBlank(issueAnswer.getAnswer())) {
                String text = HtmlUtils.Html2Text(issueAnswer.getAnswer());
                issueEsIndex.setContent(text);
            }
            // todo: issue的字段扩展要同步到es中
            IndexResponse indexResponse = geter.getClient().prepareIndex(INDEX, TYPE, issueEsIndex.getId())
                    .setTimeout(TimeValue.timeValueSeconds(5))
                    .setRefresh(true).setSource(JSON.toJSON(issueEsIndex).toString())
                    .execute().actionGet();
        }
    }

    /**
     * 修改文章状态（上、下线）
     * @param issue
     * @return
     */
    public int updateStatus(Issue issue){
        try {
            geter.getClient().prepareUpdate(INDEX, TYPE, issue.getId()+"_"+ID_TYPE)
                    .setTimeout(TimeValue.timeValueSeconds(5))
                    .setDoc(XContentFactory.jsonBuilder()
                            .startObject().field("status",issue.getStatus()).endObject()).get();
        } catch (Exception e) {
            log.error("【IssueEsIndexer.updateStatus】es保存失败"+e.getMessage());
            return -1;
        }
        return 1;
    }

    /**
     * 从es物理删除文章
     * @param issue
     * @return
     */
    public int deleteOne(Issue issue){
        DeleteResponse response = geter.getClient().prepareDelete(INDEX, TYPE, issue.getId()+"_"+ID_TYPE).setTimeout(TimeValue.timeValueSeconds(5)).execute().actionGet();
        boolean found = response.isFound();
        return found?1:-1;
    }

}
