package com.jd.help.center.domain.dispatch.xmlbean;

import java.io.Serializable;

/**
 * ���մ����ͷ���xmlbean֮������
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
	 * ����id
	 */
	private int areaId = 0;

	/**
	 * ����id
	 */
	private int townId = 0;

	/**
	 * ����ʱ��
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
