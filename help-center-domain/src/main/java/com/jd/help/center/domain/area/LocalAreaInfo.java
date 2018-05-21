package com.jd.help.center.domain.area;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhaoyanan
 * Date: 13-12-10
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 */
public class LocalAreaInfo {
    // 父节点ID
    private int id;

    // 地区list
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
