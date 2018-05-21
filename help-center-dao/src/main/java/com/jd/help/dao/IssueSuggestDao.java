package com.jd.help.dao;

import com.jd.help.domain.IssueSuggest;
import com.jd.help.domain.IssueSuggestQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lipengfei5 on 2016/9/26.
 */
public interface IssueSuggestDao {
    /**
     * ��������
     * @param issueSuggest
     * @return
     */
    long insert(IssueSuggest issueSuggest);

    /**
     * ��������
     * @param issueSuggest
     * @return
     */
    int update(IssueSuggest issueSuggest);

    /**
     * ����id��ѯ����
     * @param id
     * @return
     */
    IssueSuggest queryForObject(Integer id);

    /**
     * ��ѯ�����������б�
     * @param issueSuggest
     * @return
     */
    List<IssueSuggest> queryForList(IssueSuggest issueSuggest,int page,int pageSize);

    /**
     * ��ѯ����������
     * @param issueId
     * @return
     */
    Long queryAllNumber(Integer issueId);

    /**
     * ����������ѯ����
     * @param issueSuggest
     * @return
     */
    IssueSuggest queryOne(IssueSuggest issueSuggest);

    /**
     * ����������ѯ�����Ƿ�������
     * @param issueSuggestQuery
     * @return
     */
    Integer queryIssueSolveCount(IssueSuggestQuery issueSuggestQuery);

    /**
     * ����������ѯ����δ���ԭ����ϸ
     * @param issueSuggestQuery
     * @param page
     * @param pageSize
     * @return
     */
    List<IssueSuggest> queryIssueUnSolveList(IssueSuggestQuery issueSuggestQuery,int page, int pageSize);

    /**
     * ����������ѯ����δ���ԭ������
     * @param issueSuggestQuery
     * @return
     */
    int queryUnSolveSuggestsCount(IssueSuggestQuery issueSuggestQuery);
}
