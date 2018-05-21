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
 * ����֪ʶservice
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
     * ֪ʶ�Ĺ���֪ʶ���뵽redis
     * ʧЧʱ��7��
     * ����֪ʶֻ��֪ʶid���ܷ�չʾ���忴֪ʶ�ĵ�ʱ״̬
     *
     * @param knowledgeId ֪ʶid
     * @param relationIds ��knowledgeId������֪ʶids
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
            // ���key�����ڣ�redisԭ��expire�᷵��0��jimdb��Ϊ��װ��ܣ�Ӧ�û᷵��false
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
     * ����֪ʶid��ѯǿ����֪ʶ
     *
     * @param knowledgeId ֪ʶid
     * @return List<RelationKnowledge>
     */
    @Override
    public List<RelationKnowledge> listStrongRelationKnowledge(Long knowledgeId) {
        return relationKnowledgeDao.listRelationKnowledge(knowledgeId);
    }

    /**
     * ��redis�в�ѯ������֪ʶids
     *
     * @param knowledgeId ֪ʶid
     * @return
     */
    public Set<String> queryInRedis(Long knowledgeId){
        String key = RedisKeyContants.RELATION_KNOWLEDGE_LIST + knowledgeId;
        return jimClient.sMembers(key);
    }
}
