package com.jd.help.domain.knowledge;

/**
 * Created by yfxialiang on 2018/5/15.
 * �����ӿڷ��ص�contentType
 */
public class KnowledgeContentTypeBean {

    /**
     * contentType��ID
     */
    private Integer id;

    /**
     * contentType��name
     */
    private String Name;

    /**
     * ��������а�����contentType������
     */
    private int count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
