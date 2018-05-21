package com.jd.help.service;

import com.jd.help.domain.knowledge.KnowledgeEsBean;
import com.jd.help.domain.knowledge.KnowledgeQueryDTO;
import com.jd.help.domain.publicportal.HelpMenuKnowledge;
import com.jd.help.domain.publicportal.HelpMenuLabel;

import java.util.List;
import java.util.Set;

/**
 * Created by zhaojianhong on 2018/4/19.
 */
public interface PublicPortalService {
    void insert(HelpMenuLabel helpMenuLabel);
    List<KnowledgeEsBean> getReturnSource(String key);
    List<KnowledgeEsBean> getNewKnowledgeList(HelpMenuKnowledge key, KnowledgeEsBean knowledgeEsBean);
    KnowledgeEsBean getEsSourceById(Integer id);

    /**
     * 根据标签id匹配关联知识
     *
     * @param set         需要排除的知识集合
     * @param labelId     标签id
     * @param knowledgeId 当前操作的数据id
     * @return List<KnowledgeEsBean>
     */
    List<KnowledgeEsBean> getRelationKnowledgeFromEs(Set<Long> set, String labelId, Long knowledgeId);

    /**
     * 在es中in查询知识id
     *
     * @param set 知识id集合
     * @return List<KnowledgeEsBean>
     */
    List<KnowledgeEsBean> getKnowledgeFromEsInIds(Set<Long> set);

    /**
     * 根据条件查询所有知识
     *
     * @param knowledgeQueryDTO 知识查询条件
     * @param set               替换过的知识，不能再出现
     * @return List<KnowledgeEsBean>
     */
    List<KnowledgeEsBean> getAllKnowledgeList(KnowledgeQueryDTO knowledgeQueryDTO, Set<Long> set) ;
}
