package com.jd.help.center.domain.search;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xuxianjun
 * Date: 13-4-24
 * Time: обнГ3:54
 * To change this template use File | Settings | File Templates.
 */
public class SearchResultHeadQueryItem implements Serializable {
    private String key;
    private String field;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
