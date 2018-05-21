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
     * ������������
     * @param reviewFormId
     * @param loginUser
     * @param dataDetail
     */
    Result saveReviewContent(Long reviewFormId, String loginUser, Map<String, Object> dataDetail);

    /**
     * ����������ѯ�����б�
     * @param query
     * @param loginUser
     * @param pageNo
     * @param pageSize
     * @return
     */
    PaginatedList<FormDTO> getReviewList(FormQuery query, String loginUser, int pageNo, int pageSize);

    /**
     * ����������ѯ��������
     * @param query
     * @param loginUser
     * @return
     */
    public Long getReviewTotalNumber(FormQuery query, String loginUser);

    /**
     * ��ȡ�������������б�
     * @param reviewFormId
     * @return
     */
    List<FormElementDTO> getReviewElements(Long reviewFormId,String loginUser);
    /**
     *  ��ȡ��������
     * @param reviewFormId
     * @param loginUser
     * @return
     */
    public Map<String, Object> getReviewContent(Long reviewFormId, String loginUser);

    /**
     * �Ƿ��Ѿ������
     * @param reviewFormId
     * @param loginUser
     * @return
     */
    public boolean hasReviewed(Long reviewFormId, String loginUser);

    /**
     * ��ȡ����
     * @param reviewFormId
     * @param loginUser
     * @return
     */
    public FormDTO getReview(Long reviewFormId, String loginUser);

    /**
     * ��ȡͶƱ������
     * @param reviewFormId
     * @return
     */
    public long getReviewVoteNumber(Long reviewFormId);

    /**
     * ��ȡ������Ŀ��ͶƱ����
     * @param formId
     * @param reviewContent
     * @return
     */
    public Map<String,Long> getReviewElementValueNumber(Long formId, Map<String, Object> reviewContent);

    /**
     * �Ƿ�������Ȩ��
     * @param reviewFormId
     * @param loginUser
     * @return
     */
    boolean hasReviewAuth(Long reviewFormId,String loginUser);

    boolean markRead(Long reviewFormId,String loginUser);
}
