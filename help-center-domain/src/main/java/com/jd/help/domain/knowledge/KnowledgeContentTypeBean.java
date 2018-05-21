package com.jd.help.domain.knowledge;

/**
 * Created by yfxialiang on 2018/5/15.
 * 搜索接口返回的contentType
 */
public class KnowledgeContentTypeBean {

    /**
     * contentType的ID
     */
    private Integer id;

    /**
     * contentType的name
     */
    private String Name;

    /**
     * 搜索结果中包涵此contentType的数量
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
