package com.jd.help.service;

import com.jd.common.web.result.Result;
import com.jd.help.domain.IssueTemp;

/**
 * IssueTemp service �ӿ�
 * generated by bud
 *
 * @author @laichendong
 */
public interface IssueTempService {
    Result list(IssueTemp issueTemp, int page, int pageSize);

    Result detail(IssueTemp issueTemp);

    Result insert(IssueTemp issueTemp);

    Result update(IssueTemp issueTemp);

    Result delete(IssueTemp issueTemp);
}