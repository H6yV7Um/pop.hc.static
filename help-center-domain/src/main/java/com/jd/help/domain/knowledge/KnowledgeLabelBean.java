package com.jd.help.domain.knowledge;

/**
 * Created by yfxialiang on 2018/5/15.
 * 搜索接口返回的筛选器label
 */
public class KnowledgeLabelBean {

    //标签id
    private Integer id;

    //标签父ID
    private Integer fid;

    //标签分级：1-一级标签；2-二级标签
    private int level;

    //标签名称
    private String name;

    //搜索结果中包涵此label的数量
    private int count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
