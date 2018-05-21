package com.jd.help.center.domain;

import java.io.Serializable;
import java.util.List;

/**
 * ����������ർ��
 * User: xuxianjun
 * Date: 13-7-11
 * Time: ����3:42
 * To change this template use File | Settings | File Templates.
 */
public class HelpCenterLeftNavigate implements Serializable {

    /**
     * ��ർ������Ŀ
     */
    private List<HelpCenterCategory> categoryList;

    /**
     * ����ϵͳID
     */
    private int id;
    /**
     * ����ϵͳӢ����
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
