package com.jd.help.center.domain.area;

import java.util.List;

import com.jd.fce.upc.area.service.domain.Area;

/**
 * ����/�ļ�������Ϣʵ��
 * @author: dbliuxiaoting
 * @date: May 13, 2013 11:37:18 AM
 * @version: 1.0
 */
public class AreaInfo {

	// ���ڵ�ID
	private int id;

	// ����list
	private List<Area> areaList;

	public AreaInfo(int id, List<Area> areaList) {
		this.id = id;
		this.areaList = areaList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }
}
