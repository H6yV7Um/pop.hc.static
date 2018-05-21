package com.jd.help.domain;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

public class Notice {
	private ObjectId _id;
	
	private String id;
	
	private Integer siteId;
	
	private String name;
	
	private String content;
	
	private Integer status;
	
	private String creator;
	
	private Date created;
	
	private String modifier;
	
	private Date modified;

	private Integer sortOrder;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		if(StringUtils.isNotBlank(id)){
			this._id = new ObjectId(id);
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	
	public void set_id(ObjectId _id) {

		this._id = _id;
		if(_id != null){
			this.id = _id.toString();
		}
	}

    /**
     * ÒÆ³ýcontentÀï hrefµÄhttp:
     */
    public void removeHttp(){
        if (this.content != null) {
            this.content = HttpsUtil.removeHttp(this.content);
        }
    }
	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
}
