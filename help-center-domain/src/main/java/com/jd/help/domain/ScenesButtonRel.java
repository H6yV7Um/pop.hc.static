package com.jd.help.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 场景化按钮
 * Created by lining7 on 2017/10/10.
 */
public class ScenesButtonRel implements Serializable{
    /**
     * 主键
     */
    private Integer id;
    /**
     * 关联类型 1--类目  2--问题、文章
     */
    private int type;
    /**
     * 关联id
     */
    private Integer relId;
    /**
     * 按钮标题
     */
    private String buttonTitle;
    /**
     * 按钮链接
     */
    private String buttonLink;
    /**
     * 创建时间
     */
    private Date created;
    /**
     * 修改时间
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
