package com.jd.help.center.domain.dispatch;

import java.io.Serializable;

/**
 * 配送服务实体
 * <br>author: dblibin@jd.com
 * <br>DateTime: May 14, 2013 11:07:36 AM
 * <br>Version 1.0
 */
public class HelpDispatch implements Serializable {

	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 7585230658634147052L;

	/**
	 * 配送服务id
	 */
	private int id;

	/**
	 * 配送服务类型
	 */
	private int typeId;

	/**
	 * 配送服务名称
	 */
	private String typeName;

	/**
	 * 配送服务内容
	 */
	private String xmlContent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getXmlContent() {
		return xmlContent;
	}

	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}

}
