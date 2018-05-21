package com.jd.help.es;

import com.jd.common.util.PaginatedList;
import com.jd.help.domain.IssueVO;
import com.jd.help.domain.Issue;
import com.jd.help.es.domain.IssueEsQuery;

import java.util.List;

/**
 * @author haoming1
 * @Description: 通过es操作文章
 * @Date Created in 15:36 2018/1/16
 * @Modified By:
 */
public interface IssueEsService {

    PaginatedList<IssueVO> searchArticle(String keyword, Integer pageIndex , Integer pageSize );

    List<String> searchArticleTitle(String keyword);

    List<Issue> getRel(IssueEsQuery query);

    PaginatedList<IssueVO> searchIssueByCategoryId(Long categoryId, Integer pageIndex , Integer pageSize );
}
