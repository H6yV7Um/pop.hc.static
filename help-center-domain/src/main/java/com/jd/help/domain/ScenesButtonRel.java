package com.jd.help.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * ��������ť
 * Created by lining7 on 2017/10/10.
 */
public class ScenesButtonRel implements Serializable{
    /**
     * ����
     */
    private Integer id;
    /**
     * �������� 1--��Ŀ  2--���⡢����
     */
    private int type;
    /**
     * ����id
     */
    private Integer relId;
    /**
     * ��ť����
     */
    private String buttonTitle;
    /**
     * ��ť����
     */
    private String buttonLink;
    /**
     * ����ʱ��
     */
    private Date created;
    /**
     * �޸�ʱ��
     */
    private Date modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getRelId() {
        return relId;
    }

    public void setRelId(Integer relId) {
        this.relId = relId;
    }

    public String getButtonTitle() {
        return buttonTitle;
    }

    public void setButtonTitle(String buttonTitle) {
        this.buttonTitle = buttonTitle;
    }

    public String getButtonLink() {
        return buttonLink;
    }

    public void setButtonLink(String buttonLink) {
        this.buttonLink = buttonLink;
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

    @Override
    public String toString() {
        return "ScenesButtonRel{" +
                "id=" + id +
                ", type=" + type +
                ", relId=" + relId +
                ", buttonTitle='" + buttonTitle + '\'' +
                ", buttonLink='" + buttonLink + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
