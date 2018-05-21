package com.jd.help.center.domain.dispatch.xmlbean;

import java.io.Serializable;
import java.util.List;

/**
 * 次日达配送服务xmlbean次日达配送服务类
 * <br>author: dblibin@jd.com
 * <br>DateTime: May 14, 2013 11:08:40 AM
 * <br>Version 1.0
 */
public class CiRiDaRangeArea extends DispatchEntity implements Serializable {

	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 2431481223060240576L;

	/**
	 * 区域列表
	 */
	private List<Area> areas;

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

}
