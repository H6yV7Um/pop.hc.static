package com.jd.help.domain;

import java.util.Date;

/**
 * ����ǰ̨չʾ����
 * Created by lining7 on 2018/1/25.
 */
public class IssueVO {

    /**
     * ����
     */
    private Integer id;

    /**
     * ĩ����ĿID
     */
    private Integer cataId;

    /**
     * ����
     */
    private String name;

    /**
     * ״̬
     * 1-���� 0-����
     */
    private Integer status;


    /**
     * ����--��ȡ��������
     */
    private String summary;

    /**
     * ��������
     * 1--֪ʶ��
     * 2--����
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
