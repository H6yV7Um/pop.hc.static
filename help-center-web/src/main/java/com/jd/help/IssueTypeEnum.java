package com.jd.help;

/**
 * ֪ʶ����ö��
 * ��Լ���������á���ö�ٵĶ���ֵΪ������ƽ̨Լ���ģ����ɸ���
 *
 * @author wangyu1099
 * @date 2018/04/04
 */
public enum IssueTypeEnum {
    COMMON_PROBLEM(1, "��������"),
    OPERATION_GUIDE(3, "����ָ��");

    IssueTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
