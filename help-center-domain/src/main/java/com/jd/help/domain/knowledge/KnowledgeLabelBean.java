package com.jd.help.domain.knowledge;

/**
 * Created by yfxialiang on 2018/5/15.
 * �����ӿڷ��ص�ɸѡ��label
 */
public class KnowledgeLabelBean {

    //��ǩid
    private Integer id;

    //��ǩ��ID
    private Integer fid;

    //��ǩ�ּ���1-һ����ǩ��2-������ǩ
    private int level;

    //��ǩ����
    private String name;

    //��������а�����label������
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
