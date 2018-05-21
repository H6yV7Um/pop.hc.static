package com.jd.help.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author haoming1
 * @Description:
 * @Date Created in 16:34 2018/1/10
 * @Modified By:
 */
public class KeywordExcelBO implements Serializable {

    //���
    private Integer id;

    //�ؼ���
    private String keyword;

    //�ؼ�������
    private Integer total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "KeywordExcelBO{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", total=" + total +
                '}';
    }
}
