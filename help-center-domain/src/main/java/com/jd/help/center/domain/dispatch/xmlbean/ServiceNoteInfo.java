package com.jd.help.center.domain.dispatch.xmlbean;

import java.io.Serializable;

/**
 * 特色服务描述对象
 * @author: dbliuxiaoting
 * @date: May 15, 2013 1:53:37 PM
 * @version: 1.0
 */
public class ServiceNoteInfo implements Serializable {

	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 8420315456084364281L;

	/**
	 * 描述ID
	 */
	private int id;

	/**
	 * 描述内容
	 */
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
