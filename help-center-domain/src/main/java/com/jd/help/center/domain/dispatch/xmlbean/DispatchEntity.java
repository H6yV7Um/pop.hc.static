package com.jd.help.center.domain.dispatch.xmlbean;

/**
 * ���ͷ����Ӧ��XMLBeanʵ����
 * <br>author: dblibin@jd.com
 * <br>DateTime: May 15, 2013 9:50:01 AM
 * <br>Version 1.0
 */
public abstract class DispatchEntity {

	/**
	 * configs���ж�Ӧ�������ݵ�typeId
	 */
	private int typeId;

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

}
