package com.jd.help.es.dao.impl;

import com.jd.common.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.jd.help.es.dao.EsClientGeter;
import com.jd.help.es.dao.IssueEsDao;
import com.jd.help.es.domain.IssueEsPO;
import com.jd.help.es.domain.IssueEsQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author haoming1
 * @Description:
 * @Date Created in 16:33 2018/1/16
 * @Modified By:
 */
@Component("IssueEsDao")
public class IssueEsDaoImpl  implements IssueEsDao{

    private static final Log log = LogFactory.getLog(IssueEsDaoImpl.class);

    private final String INDEX = "help_center_vender";//es表index
    private final String TYPE = "article";

    @Resource
    private EsClientGeter geter;

    /**
     * 根据关键词查询issue，分页
     * @param query
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<IssueEsPO> searchIssueByKeyword(IssueEsQuery query, Integer page, Integer pageSize) {
        if(StringUtils.isEmpty(query.getKeyWords())){
            log.error("【IssueEsDaoImpl.searchIssueByKeyword】关键词为空");
            return Collections.emptyList();
        }
        String keyWord = query.getKeyWords();
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        //拼装查询条件
        qb.must(QueryBuilders.termQuery("status", 1));
//        if(query.getType() != null){
//            qb.must(QueryBuilders.termQuery("type", query.getType()));
//        }
        qb.should(QueryBuilders.matchQuery("title", keyWord)).boost(5.0f);
        qb.should(QueryBuilders.matchQuery("content", keyWord)).boost(1.0f);
        qb.minimumNumberShouldMatch(1);

        //查询设置
        SearchRequestBuilder srb = geter.getClient().prepareSearch(INDEX).setTypes(TYPE).setTimeout(TimeValue.timeValueSeconds(5));
        srb.setFrom((page-1)*pageSize);
        srb.setSize(pageSize);
        srb.setQuery(qb);
        srb.addSort(new ScoreSortBuilder().order(SortOrder.DESC));
        srb.setExplain(true);
        srb.addHighlightedField("title");
        srb.addHighlightedField("content");
        srb.setHighlighterPreTags("<span style=\"color:red\">");
        srb.setHighlighterPostTags("</span>");
        srb.setHighlighterFragmentSize(80);
        srb.setTrackScores(true);
        SearchResponse response = srb.execute().actionGet();
        //获取结果，进行处理
        SearchHits hits = response.getHits();
        List<IssueEsPO> list = new ArrayList<IssueEsPO>();
        if(hits.getTotalHits()>0){
            for (SearchHit hit : hits) {
                String jsonStr = hit.getSourceAsString();
                IssueEsPO esPO = JSON.parseObject(jsonStr, IssueEsPO.class);
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                //标题高亮处理
                HighlightField highlightTitle = highlightFields.get("title");
                if(null!=highlightTitle){
                    Text[] fragments = highlightTitle.fragments();
                    String title = "";
                    for (Text fragment : fragments) {
                        title+=fragment;
                    }
                    esPO.setTitle(title);
                }
                //文章高亮处理
                HighlightField highlightIssue = highlightFields.get("content");
                if(null!=highlightIssue){
                    Text[] fragments = highlightIssue.fragments();
                    String count = "";
                    for (Text fragment : fragments) {
                        count+=fragment;
                    }
                    esPO.setContent(count);
                }
                list.add(esPO);
            }
        }
        return list;
    }


    /**
     * 根据条件查询issue总数量
     * @param query
     * @return
     */
    @Override
    public int countIssueByParam(IssueEsQuery query) {
        String keyWord = query.getKeyWords();
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        //拼装查询条件
        qb.must(QueryBuilders.termQuery("status", 1));
        if(StringUtils.isNotEmpty(keyWord)) {
            qb.should(QueryBuilders.matchQuery("title", keyWord)).boost(5.0f);
            qb.should(QueryBuilders.matchQuery("content", keyWord)).boost(1.0f);
            qb.minimumNumberShouldMatch(1);
        }

        if(query.getCategoryId() != null) {
            qb.must(QueryBuilders.termQuery("lastCategoryId", query.getCategoryId()));
        }

        if(query.getType() != null) {
            qb.must(QueryBuilders.termQuery("type", query.getType()));
        }

        //查询设置
        SearchRequestBuilder srb = geter.getClient().prepareSearch(INDEX).setTypes(TYPE).setTimeout(TimeValue.timeValueSeconds(5));
        srb.setQuery(qb);
        srb.addSort(new ScoreSortBuilder().order(SortOrder.DESC));
        srb.setTrackScores(true);
        SearchResponse response = srb.execute().actionGet();
        SearchHits hits = response.getHits();
        return (int)hits.getTotalHits();
    }

    /**
     * 根据关键词查询issue，不分页
     * @param query
     * @return
     */
    @Override
    public List<IssueEsPO> searchNoPageIssueByKeyword(IssueEsQuery query) {
        ArrayList<IssueEsPO> esPOS = new ArrayList<IssueEsPO>();
        String keyWord = query.getKeyWords();
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        //拼装查询条件
        qb.must(QueryBuilders.termQuery("status", 1));
//        if(query.getType() != null){
//            qb.must(QueryBuilders.termQuery("type", query.getType()));
//        }
        qb.should(QueryBuilders.matchQuery("title", keyWord)).boost(5.0f);
        qb.should(QueryBuilders.matchQuery("content", keyWord)).boost(1.0f);
        qb.minimumNumberShouldMatch(1);

        SearchRequestBuilder srb = geter.getClient().prepareSearch(INDEX).setTypes(TYPE).setTimeout(TimeValue.timeValueSeconds(5));
        srb.setQuery(qb);
        srb.setTrackScores(true);
        SearchResponse response = srb.execute().actionGet();
        //获取结果进行处理
        SearchHits hits = response.getHits();
        if(hits.getTotalHits()>0){
            for (SearchHit hit : hits) {
                IssueEsPO esPO = JSON.parseObject(hit.getSourceAsString(), IssueEsPO.class);
                esPOS.add(esPO);
            }
        }
        return esPOS;
    }

    @Override
    public List<IssueEsPO> searchIssueByCategoryId(Long category, Integer page, Integer pageSize) {

        if(category == null) {
            return new ArrayList<IssueEsPO>();
        }
        ArrayList<IssueEsPO> esPOS = new ArrayList<IssueEsPO>();
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        //拼装查询条件
        qb.must(QueryBuilders.termQuery("status", 1));
        qb.must(QueryBuilders.termQuery("lastCategoryId", category));

        SearchRequestBuilder srb = geter.getClient().prepareSearch(INDEX).setTypes(TYPE).setTimeout(TimeValue.timeValueSeconds(5));
        srb.setQuery(qb);
        srb.setFrom((page-1)*pageSize);
        srb.setSize(pageSize);
        srb.setTrackScores(true);
        SearchResponse response = srb.execute().actionGet();
        //获取结果进行处理
        SearchHits hits = response.getHits();
        if(hits.getTotalHits()>0){
            for (SearchHit hit : hits) {
                IssueEsPO esPO = JSON.parseObject(hit.getSourceAsString(), IssueEsPO.class);
                esPOS.add(esPO);
            }
        }
        return esPOS;
    }

    /**
     * 进行相关匹配
     * @param query
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<IssueEsPO> getRel(IssueEsQuery query, int page, int pageSize) {
        //拼装查询条件
        ArrayList<IssueEsPO> esPOS = new ArrayList<IssueEsPO>();
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.termQuery("status", 1));
        qb.must(QueryBuilders.termQuery("lastCategoryId",query.getCategoryId()));
        List<String> extra = query.getExtra();
        if(CollectionUtils.isNotEmpty(extra)){
            for (String id : extra) {
                qb.should(QueryBuilders.termQuery("articleId",id));
            }
            qb.minimumNumberShouldMatch(1);
        }
        //开始查询
        SearchRequestBuilder srb = geter.getClient().prepareSearch(INDEX).setTypes(TYPE).setTimeout(TimeValue.timeValueSeconds(5));
        srb.setQuery(qb);
        srb.setTrackScores(true);
        SearchResponse response = srb.execute().actionGet();
        //获取结果进行处理
        SearchHits hits = response.getHits();
        if(hits.getTotalHits()>0){
            for (SearchHit hit : hits) {
                IssueEsPO esPO = JSON.parseObject(hit.getSourceAsString(), IssueEsPO.class);
                esPOS.add(esPO);
            }
        }
        return esPOS;
    }
}
