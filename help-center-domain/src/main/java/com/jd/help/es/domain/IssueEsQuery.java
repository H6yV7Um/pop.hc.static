package com.jd.help.es.domain;

import java.util.List;

/**
 * @author haoming1
 * @Description: ���²�ѯ����
 * @Date Created in 15:30 2018/1/16
 * @Modified By:
 */
public class IssueEsQuery {

    private String keyWords;
    private Integer type;
    //������Ŀid
    private Long categoryId;

    //�ɱ��ѯ����
    private List extra;



    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List getExtra() {
        return extra;
    }

    public void setExtra(List extra) {
        this.extra = extra;
    }
}
