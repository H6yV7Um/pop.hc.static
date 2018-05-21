package com.jd.help.es.domain;

import java.util.List;

/**
 * @author haoming1
 * @Description: 文章查询对象
 * @Date Created in 15:30 2018/1/16
 * @Modified By:
 */
public class IssueEsQuery {

    private String keyWords;
    private Integer type;
    //三级类目id
    private Long categoryId;

    //可变查询内容
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
