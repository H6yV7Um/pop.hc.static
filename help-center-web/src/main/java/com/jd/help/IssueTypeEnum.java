package com.jd.help;

/**
 * 知识类型枚举
 * 【约定大于配置】该枚举的定义值为与其他平台约定的，不可更改
 *
 * @author wangyu1099
 * @date 2018/04/04
 */
public enum IssueTypeEnum {
    COMMON_PROBLEM(1, "常见问题"),
    OPERATION_GUIDE(3, "操作指南");

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
