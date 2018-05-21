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
     * 插入数据
     * @param issueSuggest
     * @return
     */
    boolean insert(IssueSuggest issueSuggest);

    /**
     * 更新数据
     * @param issueSuggest
     * @return
     */
    boolean update(IssueSuggest issueSuggest);

    /**
     * 查出一条满足条件的数据
     * @param id
     * @return
     */
    IssueSuggest selectOneById(int id);

    /**
     * 满足条件的list
     * @param issueSuggest
     * @return
     */
    List<IssueSuggest> list(IssueSuggest issueSuggest,int page,int pageSize);

    /**
     * 查询所有评论数
     * @param issueSuggest
     * @return
     */
    Long findAllNumber(IssueSuggest issueSuggest);

    /**
     * 根据条件查询满足要求对象
     * @param issueSuggest
     * @return
     */
    IssueSuggest findOne(IssueSuggest issueSuggest);

    /**
     * 根据条件查询已解决、未解决的数量
     * @param issueSuggestQuery
     * @return
     */
    int queryIssueSolveCount(IssueSuggestQuery issueSuggestQuery);


    /**
     * 查询未解决原因列表
     * @param issueSuggestQuery
     * @param page
     * @param pageSize
     * @return
     */
    List<IssueSuggest> listForUnSolve(IssueSuggestQuery issueSuggestQuery, int page , int pageSize);
}
