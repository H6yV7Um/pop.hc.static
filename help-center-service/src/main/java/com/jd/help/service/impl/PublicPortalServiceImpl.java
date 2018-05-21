package com.jd.help.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jd.common.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jd.help.dao.publicportal.HelpMenuKnowledgeMapper;
import com.jd.help.dao.publicportal.HelpMenuLabelMapper;
import com.jd.help.domain.knowledge.KnowledgeEsBean;
import com.jd.help.domain.knowledge.KnowledgeQueryDTO;
import com.jd.help.domain.publicportal.HelpMenuKnowledge;
import com.jd.help.domain.publicportal.HelpMenuLabel;
import com.jd.help.enums.KnowledgeContentTypesEnum;
import com.jd.help.es.dao.EsClientGeter;
import com.jd.help.jimdb.JimdbConstants;
import com.jd.help.service.PublicPortalService;
import com.jd.jim.cli.Cluster;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * Created by zhaojianhong on 2018/4/19.
 */
@Service("publicPortalService")
public class PublicPortalServiceImpl implements PublicPortalService {
    private final String INDEX = "knowledge_index";//es表index
    private final String TYPE = "knowledgeindextype";

    @Resource
    private EsClientGeter geter;
    @Resource
    private HelpMenuLabelMapper helpMenuLabelMapper;
    @Resource
    private HelpMenuKnowledgeMapper helpMenuKnowledgeMapper;
    @Resource(name = "jimClient")
    private Cluster jimClient;
    private  final Logger logger = LoggerFactory.getLogger(PublicPortalServiceImpl.class);

    private final LoadingCache<String, List<KnowledgeEsBean>> guavaCache = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .softValues()
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<String, List<KnowledgeEsBean>>() {
                @Override
                public List<KnowledgeEsBean> load(String key) throws Exception {
                    return getReturnSource(key);
                }
            });

    /**
     * @param helpMenuLabel
     */
    @Override
    public void insert(HelpMenuLabel helpMenuLabel) {
        try {
            //写到jimDB里
            jimClient.setEx(JimdbConstants.KEY_PREFIX_FIVE + helpMenuLabel.getMenuId(), JSONObject.toJSONString(getReturnSource(String.valueOf(helpMenuLabel.getMenuId()))), 6, TimeUnit.SECONDS);
            //写到guava缓存里
            guavaCache.get(JimdbConstants.KEY_PREFIX_FIVE + helpMenuLabel.getMenuId());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    public List<KnowledgeEsBean> getReturnSource(String key) {
        List<KnowledgeEsBean> listResult = new ArrayList<KnowledgeEsBean>();
        //先查缓存
        String redisResult = jimClient.get(key);
        //如果缓存可以到相关信息,则直接返回查询结果
        if (StringUtils.isNotBlank(redisResult)) {
            listResult = JSON.parseArray(redisResult, KnowledgeEsBean.class);
        } else {
            logger.info("没有从jimdb中查到相关数据 key={}",key);
            Integer menuId = Integer.valueOf(key.replace(JimdbConstants.KEY_PREFIX_FIVE, ""));
            HelpMenuLabel item = new HelpMenuLabel();
            //从缓存查询该shop端页面对应的标签
            String redisItem = jimClient.get(JimdbConstants.KEY_PREFIX_ONE_LABLE + menuId);
            if (StringUtils.isNotBlank(redisItem)) {
                HelpMenuLabel bean = JSON.parseObject(redisItem, HelpMenuLabel.class);
                if (bean != null) {
                    item = bean;
                }
            }else {
                logger.info("没有从缓存中查到该页面关联的标签信息 key={},menuId={}",JimdbConstants.KEY_PREFIX_ONE_LABLE + menuId,menuId);
                return listResult;
            }
            //先从缓存查询该shop端页面对应知识的人为修改信息
            HelpMenuKnowledge helpMenuKnowledge1 = new HelpMenuKnowledge();
            String redisBean = jimClient.get(JimdbConstants.KEY_PREFIX_ONE + menuId);
            if (StringUtils.isNotBlank(redisBean)) {
                HelpMenuKnowledge bean = JSON.parseObject(redisBean, HelpMenuKnowledge.class);
                if (bean != null) {
                    helpMenuKnowledge1 = bean;
                }
            }
            Set<Long> set = new HashSet<Long>();
            Integer size = 0;
            if (helpMenuKnowledge1 != null) {
                String[] arrNew = helpMenuKnowledge1.getNewKnowledgeId() == null ? new String[0] : helpMenuKnowledge1.getNewKnowledgeId().trim().split(" ");
                for (String str : arrNew) {
                    if (StringUtils.isNotEmpty(str)) {
                        listResult.add(getEsSourceById(Integer.valueOf(str)));
                    }
                }
                String[] arChange = helpMenuKnowledge1.getChangeKnowledgeId() == null ? new String[0] : helpMenuKnowledge1.getChangeKnowledgeId().trim().split(" ");
                for (String str : arrNew) {
                    if (StringUtils.isNotEmpty(str)) {
                        set.add(Long.valueOf(str));
                    }
                }
                for (String str : arChange) {
                    if (StringUtils.isNotEmpty(str)) {
                        set.add(Long.valueOf(str));
                    }
                }
                size = size + set.size() + 5;
            } else {
                size = size + 5;

            }
            List<KnowledgeEsBean> listEs = new ArrayList<KnowledgeEsBean>();
            String[] arry = item.getLabelNames() == null ? new String[0] : item.getLabelNames().trim().split(" ");
            BoolQueryBuilder builder = boolQuery();
            for (String str : arry) {
                builder.should(QueryBuilders.termQuery("label2Names", str));
            }
            builder.minimumNumberShouldMatch(1);
            builder.must(termQuery("status", 0));
            SearchResponse sResponse = geter.getClient()
                    .prepareSearch(INDEX)
                    .setTimeout(TimeValue.timeValueMillis(5000L))
                    .setSize(size)
                    .setTypes(TYPE)
                    .addSort("modifyTime", SortOrder.DESC) //TODO 暂时按修改时间降序排序,待埋点上线一个月后,再按照pv排序降序
                    .setQuery(builder)
                    .execute()
                    .actionGet();

            SearchHits hits = sResponse.getHits();
            if (null != hits && hits.getTotalHits() > 0) {
                for (SearchHit hit : hits.getHits()) {
                    listEs.add(JSON.parseObject(hit.getSourceAsString(), KnowledgeEsBean.class));
                }
            }
            if (set.size() > 0) {
                for (KnowledgeEsBean knowledge : listEs) {
                    if (!set.contains(knowledge.getKnowledgeId())) {
                        listResult.add(knowledge);
                    }
                    if (listResult.size() == 5) {
                        break;
                    }
                }
            } else {
                listResult = listEs;
            }
            for (KnowledgeEsBean bean : listResult) {
                bean.setContent(KnowledgeContentTypesEnum.fromCode(bean.getContentType()) == null ? "" : KnowledgeContentTypesEnum.fromCode(bean.getContentType()).getDesc());
            }
            //反写至redis TODO 时间修改
            jimClient.setEx(JimdbConstants.KEY_PREFIX_FIVE + menuId, JSONObject.toJSONString(listResult), 6, TimeUnit.SECONDS);
        }
        return listResult;
    }

    @Override
    public List<KnowledgeEsBean> getNewKnowledgeList(HelpMenuKnowledge bean, KnowledgeEsBean knowledgeEsBean) {
        List<KnowledgeEsBean> list = new ArrayList<KnowledgeEsBean>();
        HelpMenuLabel item = new HelpMenuLabel();
        item.setMenuId(bean.getMenuId());
        item = helpMenuLabelMapper.selectByMenuId(item);
        BoolQueryBuilder builder = boolQuery();
        if (null != knowledgeEsBean.getKnowledgeId()) {
            builder.must(QueryBuilders.termQuery("knowledgeId", knowledgeEsBean.getKnowledgeId()));
        }
        if (StringUtils.isNotEmpty(knowledgeEsBean.getName())) {
            builder.must(matchQuery("name", knowledgeEsBean.getName()));
        }
        if (null != knowledgeEsBean.getBizTypeId()) {
            builder.must(QueryBuilders.termQuery("bizTypeId", knowledgeEsBean.getBizTypeId()));
        }
        builder.must(QueryBuilders.termQuery("status", 0));
        if (item != null) {
            SearchResponse sResponse = geter.getClient()
                    .prepareSearch(INDEX)
                    .setTimeout(TimeValue.timeValueMillis(5000L))
                    .setSize(80)
                    .setTypes(TYPE)
                    .addSort("modifyTime", SortOrder.DESC) //TODO 暂时按修改时间降序排序,待埋点上线一个月后,再按照pv排序降序
                    .setQuery(builder)
                    .execute()
                    .actionGet();
            SearchHits hits = sResponse.getHits();
            if (null != hits && hits.getTotalHits() > 0) {
                for (SearchHit hit : hits.getHits()) {
                    list.add(JSON.parseObject(hit.getSourceAsString(), KnowledgeEsBean.class));
                }
            }
            return list;
        }
        return null;
    }

    @Override
    public KnowledgeEsBean getEsSourceById(Integer id) {
        BoolQueryBuilder builder = boolQuery();
        builder.must(QueryBuilders.termQuery("knowledgeId", id));
        builder.must(QueryBuilders.termQuery("status", 0));
        SearchResponse sResponse = geter.getClient()
                .prepareSearch(INDEX)
                .setTimeout(TimeValue.timeValueMillis(5000L))
                .setTypes(TYPE)
                .setQuery(builder)
                .execute()
                .actionGet();
        SearchHits hits = sResponse.getHits();
        if (hits.getTotalHits() > 0) {
            KnowledgeEsBean knowledge = JSON.parseObject(hits.getHits()[0].getSourceAsString(), KnowledgeEsBean.class);
            return knowledge;
        }
        return null;
    }

    public static void main(String[] args) {
        int[] arr = {5, 4, 6, 7, 2, 9, 8, 3, 15, 1, 18, 55, 21, 19};
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        for (Integer num : arr) {
            System.out.print(num + ",");
        }

        int num = 15;
        int start = 0;
        int end = arr.length - 1;
      /*  while (start <= end) {
            int mid = (start + end) / 2;
            if (num == arr[mid]) {
                System.out.println(mid);
                return;
            } else if (num < arr[mid]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }*/

        TreeMap<Integer, Integer> map2 = new TreeMap<Integer, Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        map2.put(1, 2);
        map2.put(9, 4);
        map2.put(7, 1);
        map2.put(5, 2);
        Map<String, String> map = new TreeMap<String, String>();
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        List<KnowledgeEsBean> listEs = new ArrayList<KnowledgeEsBean>();
        KnowledgeEsBean bean = new KnowledgeEsBean();
        bean.setBizId(1L);
        Long knowledgeId = 1L;
        List<KnowledgeEsBean> listEsNew = new ArrayList<KnowledgeEsBean>();
        for (KnowledgeEsBean knowledgeEsBean : listEs) {
            if (knowledgeEsBean != null && !knowledgeId.equals(knowledgeEsBean.getBizId())) {
                listEsNew.add(knowledgeEsBean);
            }
        }
        listEs = listEsNew;
        System.out.println(listEs);

    }

    /**
     * 根据标签id在es中匹配关联知识
     * 与该知识同属同一标签的，按照不同知识类型系统匹配知识更新时间靠前的其他知识
     * 循环知识类型，查多次
     *
     * @param set         需要排除的知识集合
     * @param labelId     标签id
     * @param knowledgeId 当前操作的数据id
     * @return List<KnowledgeEsBean>
     */
    @Override
    public List<KnowledgeEsBean> getRelationKnowledgeFromEs(Set<Long> set, String labelId, Long knowledgeId) {
        if (org.apache.commons.lang.StringUtils.isEmpty(labelId)) {
            logger.error(" getRelationKnowledge from es error!, labelId is illegal, labelId: " + labelId);
            return null;
        }
        logger.info("getRelationKnowledgeFromEs() param: 排除的set:" + JSON.toJSONString(set) + " labelId: " + labelId + " knowledgeId: " + knowledgeId);
        List<KnowledgeEsBean> listResult = new ArrayList<KnowledgeEsBean>();
        // tomcat本地调试
//        forDev(listResult);

        // 多个知识标签的处理
        String[] arry = labelId.split(" ");
        BoolQueryBuilder builder = null;
        List<KnowledgeEsBean> listEs = null;
        for (KnowledgeContentTypesEnum typeEnum : KnowledgeContentTypesEnum.values()) {
            builder = boolQuery();
            builder.must(termQuery("status", 0));
            // 构建es的模糊查询
            for (String str : arry) {
                builder.should(termQuery("label2Ids", str));
            }
            builder.minimumNumberShouldMatch(1);
            // 指定知识类型查询维度
            builder.must(termQuery("contentType", typeEnum.getCode()));
            // 每个知识类型查20个，如果这20个都是以前被替换的，该类型下就没有数据了，就不管了。
            SearchResponse sResponse = geter.getClient()
                    .prepareSearch(INDEX)
                    .setTimeout(TimeValue.timeValueMillis(5000L))
                    .setSize(20)
                    .setTypes(TYPE)
                    .addSort("modifyTime", SortOrder.DESC) //TODO 暂时按修改时间降序排序,待埋点上线一个月后,再按照pv排序降序
                    .setQuery(builder)
                    .execute()
                    .actionGet();

            listEs = new ArrayList<KnowledgeEsBean>();
            SearchHits hits = sResponse.getHits();
            if (null != hits && hits.getTotalHits() > 0) {
                for (SearchHit hit : hits.getHits()) {
                    listEs.add(JSON.parseObject(hit.getSourceAsString(), KnowledgeEsBean.class));
                }
            }
            // 删除自己
            listEs = removeSelf(knowledgeId, listEs);
            int number = 0;
            logger.info("getRelationKnowledgeFromEs result: " + JSON.toJSONString(listEs));
            // 去重
            // 每个标签类型最多显示3个
            if (CollectionUtils.isNotEmpty(set)) {
                for (KnowledgeEsBean knowledge : listEs) {
                    if (!set.contains(knowledge.getKnowledgeId()) && number < 3) {
                        listResult.add(knowledge);
                        number++;
                    }
                }
            } else {
                if (CollectionUtils.isNotEmpty(listEs)) {
                    if (listEs.size() > 3) {
                        listResult.addAll(listEs.subList(0, 3));
                    } else {
                        listResult.addAll(listEs);
                    }
                }
            }
        }

        return listResult;
    }

    /**
     * 从查询结果集中排除自己的数据
     *
     * @param knowledgeId 正在操作的知识id
     * @param listEs      从es查询出的数据结果集
     */
    private List<KnowledgeEsBean> removeSelf(Long knowledgeId, List<KnowledgeEsBean> listEs) {
        if (knowledgeId == null || knowledgeId == 0L || CollectionUtils.isEmpty(listEs)) {
            return listEs;
        }
        List<KnowledgeEsBean> listEsNew = new ArrayList<KnowledgeEsBean>();
        for (KnowledgeEsBean knowledgeEsBean : listEs) {
            if (knowledgeEsBean != null && !knowledgeId.equals(knowledgeEsBean.getBizId())) {
                listEsNew.add(knowledgeEsBean);
            }
        }
        return listEsNew;
    }

    /**
     * dev环境使用的假数据
     * @param listResult listResult
     */
    private void forDev(List<KnowledgeEsBean> listResult){
        KnowledgeEsBean knowledgeEsBean = null;
        for(int i = 0 ; i < 12; i++){
            knowledgeEsBean = new KnowledgeEsBean();
            knowledgeEsBean.setName("测试使用" + (i+1));
            knowledgeEsBean.setContentType(1);
            knowledgeEsBean.setKnowledgeId((long) (i + 1));
            knowledgeEsBean.setStatus(1);
            knowledgeEsBean.setLabel2Ids("23 25");
            knowledgeEsBean.setLabel2Names("test1 test2");
            knowledgeEsBean.setCreateTime(1525426393000L);
            listResult.add(knowledgeEsBean);
        }
    }

    /**
     * dev环境使用的假数据
     * @param listResult listResult
     */
    private void forDev1(List<KnowledgeEsBean> listResult){
        KnowledgeEsBean knowledgeEsBean = null;
        for(int i = 10 ; i < 32; i++){
            knowledgeEsBean = new KnowledgeEsBean();
            knowledgeEsBean.setName("测试使用" + (i+1));
            knowledgeEsBean.setContentType(1);
            knowledgeEsBean.setKnowledgeId((long) (i + 1));
            knowledgeEsBean.setStatus(1);
            knowledgeEsBean.setLabel2Ids("23 25");
            knowledgeEsBean.setLabel2Names("test1 test2");
            knowledgeEsBean.setCreateTime(1525426393000L);
            listResult.add(knowledgeEsBean);
        }
    }

    /**
     * 在es中in查询知识id
     *
     * @param set 知识id集合
     * @return List<KnowledgeEsBean>
     */
    @Override
    public List<KnowledgeEsBean> getKnowledgeFromEsInIds(Set<Long> set) {
        logger.info("getKnowledgeFromEsInIds() param: " + JSON.toJSONString(set));
        if (CollectionUtils.isEmpty(set)) {
            return null;
        }
        List<KnowledgeEsBean> listResult = new ArrayList<KnowledgeEsBean>();
        BoolQueryBuilder builder = boolQuery();
        builder.must(termQuery("status", 0));

        // 构建es的in 查询
        for (Long knowledgeId : set) {
            builder.should(termQuery("knowledgeId", knowledgeId));
        }
        builder.minimumNumberShouldMatch(1);
        // 一个知识最多关联12个知识
        SearchResponse sResponse = geter.getClient()
                .prepareSearch(INDEX)
                .setTimeout(TimeValue.timeValueMillis(5000L))
                .setSize(12)
                .setTypes(TYPE)
                .setQuery(builder)
                .execute()
                .actionGet();

        SearchHits hits = sResponse.getHits();
        if (null != hits && hits.getTotalHits() > 0) {
            for (SearchHit hit : hits.getHits()) {
                listResult.add(JSON.parseObject(hit.getSourceAsString(), KnowledgeEsBean.class));
            }
        }
        return listResult;
    }

    /**
     * 根据条件查询所有知识
     *
     * @param knowledgeQueryDTO 知识查询条件
     * @param set               查询结果需要排除的知识
     * @return List<KnowledgeEsBean>
     */
    @Override
    public List<KnowledgeEsBean> getAllKnowledgeList(KnowledgeQueryDTO knowledgeQueryDTO, Set<Long> set) {
        logger.info("getAllKnowledgeList() param: " + knowledgeQueryDTO.toString());
        if (knowledgeQueryDTO == null) {
            return null;
        }
        List<KnowledgeEsBean> list = new ArrayList<KnowledgeEsBean>();
        BoolQueryBuilder builder = boolQuery();
        builder.must(termQuery("status", 0));
        // 知识id精确查询
        if (null != knowledgeQueryDTO.getKnowledgeId()) {
            builder.must(termQuery("knowledgeId", knowledgeQueryDTO.getKnowledgeId()));
        } else {
            // 知识标题模糊查询
            if (StringUtils.isNotEmpty(knowledgeQueryDTO.getName())) {
                builder.should(QueryBuilders.matchQuery("name", knowledgeQueryDTO.getName())).minimumNumberShouldMatch(1);
            }
            // 知识类型查询
            if (null != knowledgeQueryDTO.getTypeId()) {
                builder.must(termQuery("contentType", knowledgeQueryDTO.getTypeId()));
            }
        }
        // tomcat本地调试
//        forDev1(list);

        SearchResponse sResponse = geter.getClient()
                .prepareSearch(INDEX)
                .setTimeout(TimeValue.timeValueMillis(5000L))
                .setSize(50)
                .setTypes(TYPE)
                .addSort("modifyTime", SortOrder.DESC) //TODO 暂时按修改时间降序排序,待埋点上线一个月后,再按照pv排序降序
                .setQuery(builder)
                .execute()
                .actionGet();
        SearchHits hits = sResponse.getHits();
        if (null != hits && hits.getTotalHits() > 0) {
            KnowledgeEsBean kesBean = null;
            for (SearchHit hit : hits.getHits()) {
                kesBean = JSON.parseObject(hit.getSourceAsString(), KnowledgeEsBean.class);
                if (!set.contains(kesBean.getKnowledgeId())) {
                    list.add(kesBean);
                }
            }
        }
        if (list.size() > 10) {
            return list.subList(0, 10);
        } else {
            return list;
        }
    }

}
