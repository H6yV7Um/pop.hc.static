package com.jd.help.center.domain.dispatch.xmlbean;

import java.io.Serializable;
import java.util.List;

/**
 * 货到付款 xmlbean之支持区域
 * <br>author: chenbaogang@jd.com
 * <br>DateTime: May 14, 2013 1:21:25 PM
 * <br>Version 1.0
 */
public class SupCheckArea extends DispatchEntity implements Serializable {

	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 3228293143884249892L;

	/**
	 * 省列表
	 */
	private List<Integer> provinces;

	/**
	 * 市列表
	 */
	private List<Integer> citys;

	/**
	 * 区列表
	 */
	private List<Integer> areas;

	/**
	 * 镇列表
	 */
	private List<Integer> towns;

	public List<Integer> getProvinces() {
		return provinces;
	}

	public void setProvinces(List<Integer> provinces) {
		this.provinces = provinces;
	}

	public List<Integer> getCitys() {
		return citys;
	}

	public void setCitys(List<Integer> citys) {
		this.citys = citys;
	}

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
