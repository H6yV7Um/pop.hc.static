package com.jd.help.domain;

import java.util.Date;

public class IssueOldNewMapping {
	/**
     * ��ϵͳ����Id
     */
	private Integer issueId;
	/**
     * ��ϵͳ����url
     * 
     */
	private String oldUrl;
	/**
	 * ״̬
	 */
	private Integer status;
	
	 /**
     * 
     */
    private Date created;

    /**
     * 
     */
    private Date modified;

	public Integer getIssueId() {
		return issueId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}

	public String getOldUrl() {
		return oldUrl;
	}

	public void setOldUrl(String oldUrl) {
		this.oldUrl = oldUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}



    
}
