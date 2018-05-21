package com.jd.help.service;

import com.jd.common.web.result.Result;
import com.jd.help.domain.Notice;

public interface NoticeService {
	Result list(Notice notice, int page, int pageSize);

    Result detail(Notice notice, boolean needRemoveHttp);

    Result insert(Notice notice);

    Result update(Notice notice);

    Result delete(Notice notice);

    /**
     * 后台查询列表
     * @param notice
     * @param page
     * @param pageSize
     * @return
     */
    Result listForAdmin(Notice notice, int page,int pageSize);

    /**
     * 交换顺序
     * @param notice
     * @param noticeRef
     * @return
     */
    Result move(Notice notice,Notice noticeRef);

    /**
     * 下线
     * @param notice
     * @return
     */
    Result offline(Notice notice);

    /**
     * 上线
     * @param notice
     * @return
     */
    Result online(Notice notice);
}
