package com.jd.help.center.domain.dispatch.xmlbean;

import java.io.Serializable;

/**
 * 货到付款 xmlbean之付款方式
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
	 * 付款方式ID
	 */
	private Integer id;

	/**
	 * 付款方式名称
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
