package com.jd.help.service;

import com.jd.common.web.result.Result;
import com.jd.help.domain.Issue;
import com.jd.help.domain.IssueSuggest;
import com.jd.help.domain.IssueSuggestQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lipengfei5 on 2016/9/26.
 */
public interface IssueSuggestService {
    /**
     * ��������
     * @param issueSuggest
     * @return
     */
    boolean insert(IssueSuggest issueSuggest);

    /**
     * ��������
     * @param issueSuggest
     * @return
     */
    boolean update(IssueSuggest issueSuggest);

    /**
     * ���һ����������������
     * @param id
     * @return
     */
    IssueSuggest selectOneById(int id);

    /**
     * ����������list
     * @param issueSuggest
     * @return
     */
    List<IssueSuggest> list(IssueSuggest issueSuggest,int page,int pageSize);

    /**
     * ��ѯ����������
     * @param issueSuggest
     * @return
     */
    Long findAllNumber(IssueSuggest issueSuggest);

    /**
     * ����������ѯ����Ҫ�����
     * @param issueSuggest
     * @return
     */
    IssueSuggest findOne(IssueSuggest issueSuggest);

    /**
     * ����������ѯ�ѽ����δ���������
     * @param issueSuggestQuery
     * @return
     */
    int queryIssueSolveCount(IssueSuggestQuery issueSuggestQuery);


    /**
     * ��ѯδ���ԭ���б�
     * @param issueSuggestQuery
     * @param page
     * @param pageSize
     * @return
     */
    List<IssueSuggest> listForUnSolve(IssueSuggestQuery issueSuggestQuery, int page , int pageSize);
}
