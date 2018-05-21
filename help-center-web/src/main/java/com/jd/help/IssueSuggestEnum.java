package com.jd.help;

/**
 * Created by lipengfei5 on 2016/11/15.
 */
public enum IssueSuggestEnum {
    SOLVE(1,"�ѽ��"),
    UN_SOLVE(0,"δ���"),
    SUGGESTED(1,"�������"),
    UN_SUGGEST(0,"δ�����");

    IssueSuggestEnum(int status, String statusDes) {
        this.status = status;
        this.statusDes = statusDes;
    }

    private int status;
    private String statusDes;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }
}
