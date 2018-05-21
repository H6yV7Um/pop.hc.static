package com.jd.help;

/**
 * Created by lipengfei5 on 2016/11/15.
 */
public enum IssueSuggestEnum {
    SOLVE(1,"已解决"),
    UN_SOLVE(0,"未解决"),
    SUGGESTED(1,"已提意见"),
    UN_SUGGEST(0,"未提意见");

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
