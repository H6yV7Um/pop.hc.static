package com.jd.help.enums;

/**
 * Created by yfxialiang on 2018/4/8.
 */
public enum KnowledgeContentTypesEnum {
    question(1,"常见问题"),scene(2,"平台规则"),operation(3,"操作指南"),video(4,"官方视频");

    private int code;
    private String desc;

    KnowledgeContentTypesEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static KnowledgeContentTypesEnum fromCode(int code){
        KnowledgeContentTypesEnum[] enums = KnowledgeContentTypesEnum.values();
        for(KnowledgeContentTypesEnum e : enums){
            if(e.getCode() == code){
                return e;
            }
        }
        return null;
    }

    public static boolean rightCode(int code){
        KnowledgeContentTypesEnum[] enums = KnowledgeContentTypesEnum.values();
        for(KnowledgeContentTypesEnum e : enums){
            if(e.getCode() == code){
                return true;
            }
        }
        return false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
