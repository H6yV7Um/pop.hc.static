package com.jd.help.es.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jd.help.domain.knowledge.KnowledgeEsBean;
import com.jd.help.es.dao.EsClientGeter;
import com.jd.help.es.dao.KnowledgeEsDao;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by yfxialiang on 2018/4/24.
 */
@Component("knowledgeEsDao")
public class KnowledgeEsDaoImpl implements KnowledgeEsDao {

    private static final Log logger = LogFactory.getLog(KnowledgeEsDaoImpl.class);

    private final String INDEX = "knowledge_index";//es表index
    private final String TYPE = "knowledgeindextype";

    @Resource
    private EsClientGeter geter;
    @Override
    public boolean updateKnowledge(KnowledgeEsBean knowledge) throws Exception{
//        logger.info("addKnowledge--start->"+ JSONObject.toJSONString(knowledge));
        Boolean result = true;
        CallerInfo info = Profiler.registerInfo("vender.help.dao.KnowledgeEsDaoImpl.addKnowledge", "vender_help_center", false, true);
        if (knowledge == null) {
            logger.error("KnowledgeEsDaoImpl->addKnowledge knowledge is null knowledge="+ JSONObject.toJSONString(knowledge));
            Profiler.registerInfoEnd(info);
            return false;
        }

        try {
            geter.getClient().prepareIndex(INDEX, TYPE, knowledge.getKnowledgeId()+"")
                    .setTimeout(TimeValue.timeValueMillis(60000L))
                    .setSource(JSON.toJSONString(knowledge))
                    .execute()
                    .actionGet();
        } catch (Exception e) {
            logger.error("KnowledgeEsDaoImpl->updateKnowledge error vo=" + JSONObject.toJSONString(knowledge), e);
            Profiler.functionError(info);
            throw e;
        }
        Profiler.registerInfoEnd(info);
        return result;
    }

    @Override
    public boolean deleteById(Long knowledgeId) throws Exception {
        logger.info("delKnowledge--start->knowledgeId="+ knowledgeId);
        Boolean result = true;
        CallerInfo info = Profiler.registerInfo("vender.help.dao.KnowledgeEsDaoImpl.deleteById", "vender_help_center", false, true);
        if (knowledgeId == null) {
            logger.error("KnowledgeEsDaoImpl->deleteById id is null");
            Profiler.registerInfoEnd(info);
            return false;
        }
        try {
            DeleteResponse deleteResponse = geter.getClient().prepareDelete(INDEX, TYPE, knowledgeId+"")
                    .setTimeout(TimeValue.timeValueMillis(60000L))
                    .execute().actionGet();
            if (!deleteResponse.isFound()) {
                logger.info("KnowledgeEsDaoImpl->deleteById not found knowledgeId=" + knowledgeId);
                return false;
            }
        } catch (Exception e) {
            logger.error("KnowledgeEsDaoImpl->deleteById error knowledgeId=" + knowledgeId, e);
            Profiler.functionError(info);
            throw e;
        }
        Profiler.registerInfoEnd(info);
        return result;
    }

    @Override
    public boolean deleteAll() throws Exception {
        logger.info("deleteAll--start->");
        Boolean result = true;
        CallerInfo info = Profiler.registerInfo("vender.help.dao.KnowledgeEsDaoImpl.deleteAll", "vender_help_center", false, true);
        try {
            ArrayList<KnowledgeEsBean> kl = new ArrayList<KnowledgeEsBean>();
            BoolQueryBuilder qb = QueryBuilders.boolQuery();
            //拼装查询条件
            qb.must(QueryBuilders.termQuery("status", 0));
//            qb.minimumNumberShouldMatch(1);
            SearchRequestBuilder srb = geter.getClient().prepareSearch(INDEX).setTypes(TYPE);
            srb.setQuery(qb);
            srb.setTrackScores(true);
            SearchResponse response = srb.setSize(10000).execute().actionGet();
            //获取结果进行处理
            SearchHits hits = response.getHits();
            if(hits.getTotalHits()>0){
                for (SearchHit hit : hits.getHits()) {
                    KnowledgeEsBean k = JSON.parseObject(hit.getSourceAsString(), KnowledgeEsBean.class);
                    kl.add(k);
                }
            }
            if(CollectionUtils.isNotEmpty(kl)){
                logger.info("deleteAll knowledgeList.size========="+kl.size());
                for(KnowledgeEsBean k : kl){
                    deleteById(k.getKnowledgeId());
                }
            }
        } catch (Exception e) {
            logger.error("KnowledgeEsDaoImpl->deleteAll error", e);
            Profiler.functionError(info);
            throw e;
        }
        Profiler.registerInfoEnd(info);
        return result;
    }

    @Override
    public KnowledgeEsBean queryById(Long knowledgeId) throws Exception {
        logger.info("queryById--start->knowledgeId==="+knowledgeId);
        KnowledgeEsBean bean = null;
        CallerInfo info = Profiler.registerInfo("vender.help.dao.KnowledgeEsDaoImpl.queryById", "vender_help_center", false, true);
        try {
            GetResponse getResponse = geter.getClient().prepareGet(INDEX, TYPE, knowledgeId + "")
                    .execute().actionGet();
            Map<String, Object> result = getResponse.getSource();
            if(result != null && !result.isEmpty()){
                Object o = JSON.toJSON(result);
                bean = JSON.toJavaObject((JSON)o,KnowledgeEsBean.class);
            }
        } catch (Exception e) {
            logger.error("KnowledgeEsDaoImpl->deleteAll error", e);
            Profiler.functionError(info);
            throw e;
        }
        Profiler.registerInfoEnd(info);
        return bean;

    }

}
