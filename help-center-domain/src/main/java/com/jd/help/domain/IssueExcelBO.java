package com.jd.help.domain;

import com.jd.help.excel.XExcel;

import java.io.Serializable;

/**
 * Created by lining7 on 2017/10/18.
 */
public class IssueExcelBO implements Serializable {
    /**
     * ����
     */
    @XExcel.XExcelField(order=0)
    private Integer id;

    /**
     * ����
     */
    @XExcel.XExcelField(order=1)
    private String name;
    /**
     * ��������
     */
    @XExcel.XExcelField(order=2)
    private String url;
    
    /**
     * δ�������
     */
    @XExcel.XExcelField(order=3)
    private Integer unSolveCount;
    /**
     * �ѽ������
     */
    @XExcel.XExcelField(order=4)
    private Integer solveCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUnSolveCount() {
        return unSolveCount;
    }

    public void setUnSolveCount(Integer unSolveCount) {
        this.unSolveCount = unSolveCount;
    }

    public Integer getSolveCount() {
        return solveCount;
    }

    public void setSolveCount(Integer solveCount) {
        this.solveCount = solveCount;
    }
}
