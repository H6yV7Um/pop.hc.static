package com.jd.help.domain;

import java.util.Date;

/**
 * 用于前台展示搜索
 * Created by lining7 on 2018/1/25.
 */
public class IssueVO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 末级类目ID
     */
    private Integer cataId;

    /**
     * 标题
     */
    private String name;

    /**
     * 状态
     * 1-发布 0-下线
     */
    private Integer status;


    /**
     * 高亮--截取文章内容
     */
    private String summary;

    /**
     * 文章类型
     * 1--知识库
     * 2--规则
     */
    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCataId() {
        return cataId;
    }

    public void setCataId(Integer cataId) {
        this.cataId = cataId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
