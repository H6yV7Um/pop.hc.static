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
     * 插入数据
     * @param issueSuggest
     * @return
     */
    long insert(IssueSuggest issueSuggest);

    /**
     * 更新数据
     * @param issueSuggest
     * @return
     */
    int update(IssueSuggest issueSuggest);

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    IssueSuggest queryForObject(Integer id);

    /**
     * 查询满足条件的列表
     * @param issueSuggest
     * @return
     */
    List<IssueSuggest> queryForList(IssueSuggest issueSuggest,int page,int pageSize);

    /**
     * 查询所有评论数
     * @param issueId
     * @return
     */
    Long queryAllNumber(Integer issueId);

    /**
     * 根据条件查询对象
     * @param issueSuggest
     * @return
     */
    IssueSuggest queryOne(IssueSuggest issueSuggest);

    /**
     * 根据条件查询文章是否解决数量
     * @param issueSuggestQuery
     * @return
     */
    Integer queryIssueSolveCount(IssueSuggestQuery issueSuggestQuery);

    /**
     * 根据条件查询文章未解决原因明细
     * @param issueSuggestQuery
     * @param page
     * @param pageSize
     * @return
     */
    List<IssueSuggest> queryIssueUnSolveList(IssueSuggestQuery issueSuggestQuery,int page, int pageSize);

    /**
     * 根据条件查询文章未解决原因数量
     * @param issueSuggestQuery
     * @return
     */
    int queryUnSolveSuggestsCount(IssueSuggestQuery issueSuggestQuery);
}
