package com.jd.help.center.domain.dispatch.xmlbean;

import java.io.Serializable;

/**
 * 次日达配送服务xmlbean之区域类
 * <br>author: dblibin@jd.com
 * <br>DateTime: May 14, 2013 11:08:40 AM
 * <br>Version 1.0
 */
public class Area implements Serializable {

	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 4366413767358950111L;

	/**
	 * 区域id
	 */
	private int areaId = 0;

	/**
	 * 城镇id
	 */
	private int townId = 0;

	/**
	 * 结束时间
	 */
	private String endTime;

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public int getTownId() {
		return townId;
	}

	public void setTownId(int townId) {
		this.townId = townId;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
