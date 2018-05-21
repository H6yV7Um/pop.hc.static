package com.jd.help.center.domain.dispatch.xmlbean;

import java.io.Serializable;
import java.util.List;

/**
 * 特色服务描述
 * @author: dbliuxiaoting
 * @date: May 15, 2013 1:45:16 PM
 * @version: 1.0
 */
public class ServiceNotes implements Serializable {

	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 5370117465223788484L;

	/**
	 * 特色服务描述的对象列表
	 */
	List<ServiceNoteInfo> notes;

	public List<ServiceNoteInfo> getNotes() {
		return notes;
	}

	public void setNotes(List<ServiceNoteInfo> notes) {
		this.notes = notes;
	}

}
