package com.jd.help.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author haoming1
 * @Description: 关键词对象
 * @Date Created in 16:40 2018/1/8
 * @Modified By:
 */
public class Keyword implements Serializable {

    //主键
    private Long id;

    //关键词
    private String keyword;

    //搜索人pin
    private String createPin;

    //搜索时间
    private Date createTime;

    //若为商家，其id
    private Long venderId;

    //关键词总量
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCreatePin() {
        return createPin;
    }

    public void setCreatePin(String createPin) {
        this.createPin = createPin;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", createPin='" + createPin + '\'' +
                ", createTime=" + createTime +
                ", venderId=" + venderId +
                '}';
    }
}
