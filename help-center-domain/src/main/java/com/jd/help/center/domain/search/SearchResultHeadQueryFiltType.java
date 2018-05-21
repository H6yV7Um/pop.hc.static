package com.jd.help.center.domain.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xuxianjun
 * Date: 13-4-24
 * Time: обнГ3:54
 * To change this template use File | Settings | File Templates.
 */
public class SearchResultHeadQueryFiltType implements Serializable {
    private String FiltType;

    public String getFiltType() {
        return FiltType;
    }

    public void setFiltType(String filtType) {
        FiltType = filtType;
    }
}
