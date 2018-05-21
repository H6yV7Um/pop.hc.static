package com.jd.help.utils;

import java.io.Serializable;

/**
 * Created by yfxialiang on 2018/5/15.
 */
public class DateweekBean implements Serializable {
    private static final long serialVersionUID = 5732144366648342894L;
    private String date;
    private String week;
    private boolean ispast;

    public String getDate() {
        return date;
    }

    /**
     *
     * setter of date
     * @param date
     *
     **/
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * getter of week
     *
     **/
    public String getWeek() {
        return week;
    }

    /**
     *
     * setter of week
     * @param week
     *
     **/
    public void setWeek(String week) {
        this.week = week;
    }

    /**
     *
     * getter of ispast
     *
     **/
    public boolean getIspast() {
        return ispast;
    }

    /**
     *
     * setter of ispast
     * @param ispast
     *
     **/
    public void setIspast(boolean ispast) {
        this.ispast = ispast;
    }
}
