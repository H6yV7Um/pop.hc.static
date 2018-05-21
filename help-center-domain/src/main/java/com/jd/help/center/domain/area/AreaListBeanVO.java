package com.jd.help.center.domain.area;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: zhaoyanan
 * Date: 13-12-10
 * Time: обнГ4:10
 * To change this template use File | Settings | File Templates.
 */
public class AreaListBeanVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
