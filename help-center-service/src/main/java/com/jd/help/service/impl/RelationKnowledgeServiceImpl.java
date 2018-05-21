package com.jd.help.service.impl;

import com.jd.help.center.domain.constants.RedisKeyContants;
import com.jd.help.dao.RelationKnowledgeDao;
import com.jd.help.domain.RelationKnowledge;
import com.jd.help.service.RelationKnowledgeService;
import com.jd.jim.cli.Cluster;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 关联知识service
 *
 * @author wangyu1099
 * @date 2018/04/17
 */
@Service("relationKnowledgeService")
public class RelationKnowledgeServiceImpl implements RelationKnowledgeService{

    private static Logger logger = LoggerFactory.getLogger(RelationKnowledgeServiceImpl.class);

    @Resource(name = "jimClient")
    private Cluster jimClient;

    @Resource
    private RelationKnowledgeDao relationKnowledgeDao;

    /**
     * 知识的关联知识存入到redis
     * 失效时间7天
     * 关联知识只存知识id，能否展示具体看知识的当时状态
     *
     * @param knowledgeId 知识id
     * @param relationIds 与knowledgeId关联的知识ids
     * @return
     */
    @Override
    public boolean addRelationKnowledgeToRedis(Long knowledgeId, Set<Long> relationIds) {
        if(CollectionUtils.isEmpty(relationIds)){
            logger.info("method addRelationKnowledgeToRedis return, relationIds is empty");
            return false;
        }
        try {
            String key = RedisKeyContants.RELATION_KNOWLEDGE_LIST + knowledgeId;
            String[] values = new String[relationIds.size()];
            Iterator<Long> iterator = relationIds.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                values[i++] = iterator.next().toString();
            }
            jimClient.sAdd(key, values);
            // 如果key不存在，redis原生expire会返回0，jimdb作为封装框架，应该会返回false
            boolean result = jimClient.expire(key, 7, TimeUnit.DAYS);
            if (!result) {
                logger.error("jimDB add error! methodName:addRelationKnowledgeToRedis(), key: " + key + " , value: " + Arrays.toString(values));
            }
            return result;
        } catch (Exception e) {
            logger.error("method addRelationKnowledgeToRedis execute error!  {}", e);
            return false;
        }
    }

    /**
     * 根据知识id查询强关联知识
     *
     * @param knowledgeId 知识id
     * @return List<RelationKnowledge>
     */
    @Override
    public List<RelationKnowledge> listStrongRelationKnowledge(Long knowledgeId) {
        return relationKnowledgeDao.listRelationKnowledge(knowledgeId);
    }

    /**
     * 在redis中查询关联的知识ids
     *
     * @param knowledgeId 知识id
     * @return
     */
    public Set<String> queryInRedis(Long knowledgeId){
        String key = RedisKeyContants.RELATION_KNOWLEDGE_LIST + knowledgeId;
        return jimClient.sMembers(key);
    }
}
