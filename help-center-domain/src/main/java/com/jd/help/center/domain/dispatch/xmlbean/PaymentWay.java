package com.jd.help.center.domain.dispatch.xmlbean;

import java.io.Serializable;

/**
 * �������� xmlbean֮���ʽ
 * <br>author: chenbaogang@jd.com
 * <br>DateTime: May 14, 2013 1:11:04 PM
 * <br>Version 1.0
 */
public class PaymentWay implements Serializable {

	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 3422902289293450446L;

	/**
	 * ���ʽID
	 */
	private Integer id;

	/**
	 * ���ʽ����
	 */
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
