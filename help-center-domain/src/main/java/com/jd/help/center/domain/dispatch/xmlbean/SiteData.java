package com.jd.help.center.domain.dispatch.xmlbean;

import java.io.Serializable;
import java.util.List;

/**
 * �ص���Ϣʵ��
 * @author: dbliuxiaoting
 * @date: May 21, 2013 3:50:48 PM
 * @version: 1.0
 */
public class SiteData extends DispatchEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4201415665968036911L;
	//�ص��б�
	private List<Integer> sites;

	public List<Integer> getSites() {
		return sites;
	}

	public void setSites(List<Integer> sites) {
		this.sites = sites;
	}

}
