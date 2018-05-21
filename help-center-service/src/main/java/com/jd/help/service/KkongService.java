package com.jd.help.service;

import com.jd.common.util.PaginatedList;
import com.jd.common.web.result.Result;
import com.jd.pop.form.api.domain.client.ClientSource;
import com.jd.pop.form.api.open.dto.FormDTO;
import com.jd.pop.form.api.open.dto.FormElementDTO;
import com.jd.pop.form.api.open.query.FormQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by lipengfei5 on 2017/9/5.
 */
public interface KkongService {
    /**
     * 保存评审内容
     * @param reviewFormId
     * @param loginUser
     * @param dataDetail
     */
    Result saveReviewContent(Long reviewFormId, String loginUser, Map<String, Object> dataDetail);

    /**
     * 根据条件查询评审列表
     * @param query
     * @param loginUser
     * @param pageNo
     * @param pageSize
     * @return
     */
    PaginatedList<FormDTO> getReviewList(FormQuery query, String loginUser, int pageNo, int pageSize);

    /**
     * 根据条件查询评审总数
     * @param query
     * @param loginUser
     * @return
     */
    public Long getReviewTotalNumber(FormQuery query, String loginUser);

    /**
     * 获取评审内容问题列表
     * @param reviewFormId
     * @return
     */
    List<FormElementDTO> getReviewElements(Long reviewFormId,String loginUser);
    /**
     *  获取评审内容
     * @param reviewFormId
     * @param loginUser
     * @return
     */
    public Map<String, Object> getReviewContent(Long reviewFormId, String loginUser);

    /**
     * 是否已经评审过
     * @param reviewFormId
     * @param loginUser
     * @return
     */
    public boolean hasReviewed(Long reviewFormId, String loginUser);

    /**
     * 获取评审
     * @param reviewFormId
     * @param loginUser
     * @return
     */
    public FormDTO getReview(Long reviewFormId, String loginUser);

    /**
     * 获取投票总数量
     * @param reviewFormId
     * @return
     */
    public long getReviewVoteNumber(Long reviewFormId);

    /**
     * 获取传入题目的投票数量
     * @param formId
     * @param reviewContent
     * @return
     */
    public Map<String,Long> getReviewElementValueNumber(Long formId, Map<String, Object> reviewContent);

    /**
     * 是否有评审权限
     * @param reviewFormId
     * @param loginUser
     * @return
     */
    boolean hasReviewAuth(Long reviewFormId,String loginUser);

    boolean markRead(Long reviewFormId,String loginUser);
}
