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
     * ���ݱ�ǩidƥ�����֪ʶ
     *
     * @param set         ��Ҫ�ų���֪ʶ����
     * @param labelId     ��ǩid
     * @param knowledgeId ��ǰ����������id
     * @return List<KnowledgeEsBean>
     */
    List<KnowledgeEsBean> getRelationKnowledgeFromEs(Set<Long> set, String labelId, Long knowledgeId);

    /**
     * ��es��in��ѯ֪ʶid
     *
     * @param set ֪ʶid����
     * @return List<KnowledgeEsBean>
     */
    List<KnowledgeEsBean> getKnowledgeFromEsInIds(Set<Long> set);

    /**
     * ����������ѯ����֪ʶ
     *
     * @param knowledgeQueryDTO ֪ʶ��ѯ����
     * @param set               �滻����֪ʶ�������ٳ���
     * @return List<KnowledgeEsBean>
     */
    List<KnowledgeEsBean> getAllKnowledgeList(KnowledgeQueryDTO knowledgeQueryDTO, Set<Long> set) ;
}
