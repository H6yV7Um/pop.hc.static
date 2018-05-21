package com.jd.help.domain.knowledge;

/**
 * @author wangyu1099
 * @date 2018/05/09
 */
public class KnowledgeQueryDTO {

    /**
     * ��ȷ��ѯ��֪ʶid
     */
    private Long knowledgeId;

    /**
     * ģ����ѯ��֪ʶ����
     */
    private String name;

    /**
     * ֪ʶ����
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
