package com.jd.help.domain;

import java.util.Date;

/**
 * @author haoming1
 * @Description: ʱ���ѯ
 * @Date Created in 21:35 2018/1/10
 * @Modified By:
 */
public class KeywordQuery {

    //��ʼʱ��
    private String queryBeginTime;

    //����ʱ��
    private String queryEndTime;

    public String getQueryBeginTime() {
        return queryBeginTime;
    }

    public void setQueryBeginTime(String queryBeginTime) {
        this.queryBeginTime = queryBeginTime;
    }

    public String getQueryEndTime() {
        return queryEndTime;
    }

    public void setQueryEndTime(String queryEndTime) {
        this.queryEndTime = queryEndTime;
    }
}
