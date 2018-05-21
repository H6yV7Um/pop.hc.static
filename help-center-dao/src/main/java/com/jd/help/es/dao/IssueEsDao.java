package com.jd.help.es.dao;

import com.jd.help.es.domain.IssueEsPO;
import com.jd.help.es.domain.IssueEsQuery;

import java.util.List;

/**
 * @author haoming1
 * @Description: 通过es操作文章dao层
 * @Date Created in 16:32 2018/1/16
 * @Modified By:
 */
public interface IssueEsDao {

    List<IssueEsPO> searchIssueByKeyword(IssueEsQuery query, Integer page, Integer pageSize);

    int countIssueByParam(IssueEsQuery query);

    List<IssueEsPO> searchNoPageIssueByKeyword(IssueEsQuery query);

    List<IssueEsPO> getRel(IssueEsQuery query, int page, int pageSize);


    List<IssueEsPO> searchIssueByCategoryId(Long category, Integer page, Integer pageSize);
}
