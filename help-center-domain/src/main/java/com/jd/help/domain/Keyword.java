package com.jd.help.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author haoming1
 * @Description: �ؼ��ʶ���
 * @Date Created in 16:40 2018/1/8
 * @Modified By:
 */
public class Keyword implements Serializable {

    //����
    private Long id;

    //�ؼ���
    private String keyword;

    //������pin
    private String createPin;

    //����ʱ��
    private Date createTime;

    //��Ϊ�̼ң���id
    private Long venderId;

    //�ؼ�������
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
