package com.jd.help.domain.knowledge;

/**
 * @author wangyu1099
 * @date 2018/05/09
 */
public class KnowledgeQueryDTO {

    /**
     * 精确查询的知识id
     */
    private Long knowledgeId;

    /**
     * 模糊查询的知识标题
     */
    private String name;

    /**
     * 知识类型
     */
    private Integer typeId;

    public Long getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "KnowledgeQueryDTO{" +
                "knowledgeId=" + knowledgeId +
                ", name='" + name + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}
