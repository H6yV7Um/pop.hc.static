package com.jd.help.es.domain;

/**
 * @author haoming1
 * @Description: ��es���������¶���
 * @Date Created in 16:02 2018/1/16
 * @Modified By:
 */
//@Document(indexName = "help_center_vender", type = "article", shards = 5, replicas = 1)
public class IssueEsPO {
    //������articleId+type�����磺articleId=1234 type=2 ������Ϊ1234_2
    private String id;
    //1-֪ʶ��2--����
    private Integer type;

    private String title;

    private String content;
    //������Ŀid
    private Long lastCategoryId;
    //״̬ 1���ѷ��� 0������
    private Integer status;
    //֪ʶ�⡢�������µ����ݿ�id
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
