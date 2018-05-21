package com.jd.help.center.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 帮助中心左侧导航
 * User: xuxianjun
 * Date: 13-7-11
 * Time: 下午3:42
 * To change this template use File | Settings | File Templates.
 */
public class HelpCenterLeftNavigate implements Serializable {

    /**
     * 左侧导航分类目
     */
    private List<HelpCenterCategory> categoryList;

    /**
     * 导航系统ID
     */
    private int id;
    /**
     * 导航系统英文名
     */
    private String name;


    public List<HelpCenterCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<HelpCenterCategory> categoryList) {
        this.categoryList = categoryList;
    }

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
