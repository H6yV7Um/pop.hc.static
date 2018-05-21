package com.jd.help.domain;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

public class HtmlModule {
	
	private ObjectId _id;
	
	private String id;
	
	/**
	 * վ��ID
	 */
	private Integer siteId;
	/**
	 * htmlģ���������
	 */
	private String key;
	
	/**
	 * ��������
	 */
	private String name;

	/**
	 * ����
	 */
	private String content;
	
	/**
	 * ״̬
	 */
	private Integer status;
	
	/**
	 * ��ע
	 */
	private String notes;
	
	private String creator;
	
	private String modifier;
	
	private Date created;
    
	private Date modified;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		if(StringUtils.isNotBlank(id)){
			this._id = new ObjectId(id);
		}
		
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
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

	public void set_id(ObjectId _id) {
		this._id = _id;
		if(_id != null){
			this.id = _id.toString();
		}
	}

    /**
     * �Ƴ�content�� href��http:
     */
    public void removeHttp(){
        if (this.content != null) {
            this.content = HttpsUtil.removeHttp(this.content);
        }
    }
	
	@Override
	public String toString() {
		return "HtmlModule{" +
				"id=" + id +
				", siteId=" + siteId +
				", key='" + key + '\'' +
				", name='" + name + '\'' +
				", content='" + content + '\'' +
				", status=" + status +
				", notes='" + notes + '\'' +
				", creator='" + creator + '\'' +
				", modifier='" + modifier + '\'' +
				", created=" + created +
				", modified=" + modified +
				'}';
	}
}
