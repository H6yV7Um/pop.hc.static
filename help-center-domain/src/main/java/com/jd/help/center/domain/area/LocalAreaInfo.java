package com.jd.help.center.domain.area;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhaoyanan
 * Date: 13-12-10
 * Time: ����4:24
 * To change this template use File | Settings | File Templates.
 */
public class LocalAreaInfo {
    // ���ڵ�ID
    private int id;

    // ����list
    private List<AreaListBeanVO> areaList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<AreaListBeanVO> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaListBeanVO> areaList) {
        this.areaList = areaList;
    }
}
