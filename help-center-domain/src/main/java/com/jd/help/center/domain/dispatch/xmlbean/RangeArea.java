package com.jd.help.center.domain.dispatch.xmlbean;

import java.io.Serializable;
import java.util.List;

/**
 * �������,��ҵ�,211,���� xmlbean
 * <br>author: chenbaogang@jd.com
 * <br>DateTime: May 13, 2013 7:49:00 PM
 * <br>Version 1.0
 */
public class RangeArea extends DispatchEntity implements Serializable {

	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = -1360557727412752687L;

	/**
	 * �����б�
	 */
	private List<Integer> areas;

	/**
	 * �����б�
	 */
	private List<Integer> towns;

	public List<Integer> getAreas() {
		return areas;
	}

	public void setAreas(List<Integer> areas) {
		this.areas = areas;
	}

	public List<Integer> getTowns() {
		return towns;
	}

	public void setTowns(List<Integer> towns) {
		this.towns = towns;
	}

}
