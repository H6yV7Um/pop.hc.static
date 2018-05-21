package com.jd.help.es.domain;

/**
 * @author haoming1
 * @Description: 与es交互的文章对象
 * @Date Created in 16:02 2018/1/16
 * @Modified By:
 */
//@Document(indexName = "help_center_vender", type = "article", shards = 5, replicas = 1)
public class IssueEsPO {
    //主键（articleId+type）例如：articleId=1234 type=2 则主键为1234_2
    private String id;
    //1-知识库2--规则
    private Integer type;

    private String title;

    private String content;
    //三级类目id
    private Long lastCategoryId;
    //状态 1—已发布 0—下线
    private Integer status;
    //知识库、规则文章的数据库id
    private Long articleId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getLastCategoryId() {
        return lastCategoryId;
    }

    public void setLastCategoryId(Long lastCategoryId) {
        this.lastCategoryId = lastCategoryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}
