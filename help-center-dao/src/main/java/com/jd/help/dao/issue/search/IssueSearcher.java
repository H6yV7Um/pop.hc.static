package com.jd.help.dao.issue.search;

import com.jd.common.util.PaginatedList;
import com.jd.common.util.base.PaginatedArrayList;
import com.jd.help.dao.util.Page;
import com.jd.help.domain.Issue;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 问题搜索器
 *
 * @author laichendong
 * @since 2014/12/10 13:14
 */
@Repository("issueSearcher")
public class IssueSearcher {
    static final Log log = LogFactory.getLog(IssueSearcher.class);
    public static final String AND = " AND ";
    public static final String OR = " OR ";
    @Resource
    private HttpSolrServer solrServer;


    public PaginatedList<Issue> search(SolrQuery solrQuery, int pageNo, int pageSize) {
        PaginatedList<Issue> issues = new PaginatedArrayList<Issue>(pageNo, pageSize);
        solrQuery.setStart(new Page(pageNo, pageSize).getStartRow());
        solrQuery.setRows(pageSize);
        try {
            log.debug(solrQuery);
            QueryResponse queryResponse = solrServer.query(solrQuery);
            issues.setTotalItem((int) queryResponse.getResults().getNumFound());
            List<IssueIndex> issueIndexList = queryResponse.getBeans(IssueIndex.class);
            for (IssueIndex issueIndex : issueIndexList) {
                issues.add(issueIndex.toIssue());
            }
        } catch (SolrServerException e) {
            log.error("query solr error!", e);
        }
        return issues;
    }
    
    public PaginatedList<Issue> searchWithHighlighting(SolrQuery solrQuery, int pageNo, int pageSize) {
        PaginatedList<Issue> issues = new PaginatedArrayList<Issue>(pageNo, pageSize);
        solrQuery.setStart(new Page(pageNo, pageSize).getStartRow());
        solrQuery.setRows(pageSize);
        try {
            log.debug(solrQuery);
            QueryResponse queryResponse = solrServer.query(solrQuery);
            issues.setTotalItem((int) queryResponse.getResults().getNumFound());
            List<IssueIndex> issueIndexList = queryResponse.getBeans(IssueIndex.class);
         // 处理高亮
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            
            for (IssueIndex issueIndex : issueIndexList) {
            	Issue issue = issueIndex.toIssue();
            	Map<String, List<String>> map = highlighting.get(String.valueOf(issue.getId()));
            	 if (MapUtils.isNotEmpty(map)) {
            		 List<String> name = map.get("name");
                     if (CollectionUtils.isNotEmpty(name)) {
                         issue.setName(name.get(0));
                     } 
            	 }
                issues.add(issue);
            }
        } catch (SolrServerException e) {
            log.error("query solr error!", e);
        }
        return issues;
    }
    
    public PaginatedList<Issue> search4Customer(SolrQuery solrQuery, int pageNo, int pageSize) {
        PaginatedList<Issue> issues = new PaginatedArrayList<Issue>(pageNo, pageSize);
        solrQuery.setStart(new Page(pageNo, pageSize).getStartRow());
        solrQuery.setRows(pageSize);
        try {
            log.debug(solrQuery);
            QueryResponse queryResponse = solrServer.query(solrQuery);
            SolrDocumentList result = queryResponse.getResults();
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            if (CollectionUtils.isNotEmpty(result)) {
                for (SolrDocument doc : result) {
                    Issue issue = new Issue();
                    Integer id = (Integer) doc.getFieldValue("id");
                    // id
                    issue.setId(id);
                    // catalogId
                    String cataId = (String) doc.getFieldValue("cataId");
                    if (StringUtils.isNotBlank(cataId) && StringUtils.containsAny(cataId, " ")) {
                        String[] ids = cataId.split(" ");
                        if (ids != null && ids.length > 0) {
                            try {
                                issue.setCataId(Integer.valueOf(ids[ids.length - 1]));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    // 处理高亮
                    Map<String, List<String>> map = highlighting.get(String.valueOf(id));
                    if (MapUtils.isNotEmpty(map)) {
                        // 名称
                        List<String> name = map.get("name");
                        if (CollectionUtils.isNotEmpty(name)) {
                            issue.setName(name.get(0));
                        } else {
                            issue.setName((String) doc.getFieldValue("name"));
                        }
                        // summary
                        List<String> summary = map.get("summary");
                        if (CollectionUtils.isNotEmpty(summary)) {
                            issue.setSummary(summary.get(0));
                        } else {
                            issue.setSummary((String) doc.getFieldValue("summary"));
                        }
                    } else {
                        issue.setName((String) doc.getFieldValue("name"));
                        issue.setSummary((String) doc.getFieldValue("summary"));
                    }
                    issues.add(issue);
                }

            }
            issues.setTotalItem((int) result.getNumFound());
        } catch (SolrServerException e) {
            log.error("query solr error!", e);
        }
        return issues;
    }

    public List<String> searchSuggest(SolrQuery solrQuery){
        QueryResponse rsp = null;
        List<String>  wordList=new ArrayList<String>();
        try {
            rsp = solrServer.query(solrQuery);
            //  System.out.println("直接命中:"+rsp.getResults().size());
            //…上面取结果的代码
            SpellCheckResponse re=rsp.getSpellCheckResponse();//获取拼写检查的结果集
            if (re != null) {
                for(SpellCheckResponse.Suggestion s:re.getSuggestions()){
                    List<String> list=s.getAlternatives();//获取所有 的检索词
                    for(String spellWord:list){
                        //System.out.println(spellWord);
                        wordList.add(spellWord);
                    }
                    return wordList;//建议词汇
                }
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
