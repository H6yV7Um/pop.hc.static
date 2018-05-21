package com.jd.help.center.domain.dispatch.xmlbean;

/**
 * 配送服务对应的XMLBean实体类
 * <br>author: dblibin@jd.com
 * <br>DateTime: May 15, 2013 9:50:01 AM
 * <br>Version 1.0
 */
public abstract class DispatchEntity {

	/**
	 * configs表中对应服务数据的typeId
	 */
	private int typeId;

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

}
