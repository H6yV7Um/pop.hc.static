package com.jd.help.domain;

import java.util.Date;

/**
 * @author haoming1
 * @Description: �ؼ���
 * @Date Created in 20:25 2018/1/9
 * @Modified By:
 */
public class KeywordBO extends Keyword{

    //��ʼʱ��
    private Date beginTime;

    //����ʱ��
    private Date endTime;

    //����ʽ
    private String orderBy;

    //top����
    private Integer topNum;

    //�����ѯʱ��
    private KeywordQuery keywordQuery;

    public Integer getTopNum() {
        return topNum;
    }

    public void setTopNum(Integer topNum) {
        this.topNum = topNum;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public KeywordQuery getKeywordQuery() {
        return keywordQuery;
    }

    public void setKeywordQuery(KeywordQuery keywordQuery) {
        this.keywordQuery = keywordQuery;
    }

    @Override
    public String toString() {
        return "KeywordBO{" +
                "beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", orderBy='" + orderBy + '\'' +
                ", topNum=" + topNum +
                '}';
    }
}
