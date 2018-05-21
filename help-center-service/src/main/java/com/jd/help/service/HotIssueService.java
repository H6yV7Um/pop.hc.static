package com.jd.help.service;

import com.jd.common.web.result.Result;
import com.jd.help.domain.HotIssue;

/**
 * HotIssue service �ӿ�
 * generated by bud
 *
 * @author @laichendong
 */
public interface HotIssueService {
    Result list(HotIssue hotIssue, int page, int pageSize);

    Result detail(HotIssue hotIssue);

    Result insert(HotIssue hotIssue);

    Result update(HotIssue hotIssue);

    Result delete(HotIssue hotIssue);
}