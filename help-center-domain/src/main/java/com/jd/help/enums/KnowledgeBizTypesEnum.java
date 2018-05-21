package com.jd.help.enums;

/**
 * Created by yfxialiang on 2018/4/8.
 */
public enum KnowledgeBizTypesEnum {
    hc(1,"帮助中心"),karma(2,"规则中心"),xue(3,"商家学习中心");

    private int code;
    private String desc;

    KnowledgeBizTypesEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static KnowledgeBizTypesEnum fromCode(int code){
        KnowledgeBizTypesEnum[] enums = KnowledgeBizTypesEnum.values();
        for(KnowledgeBizTypesEnum e : enums){
            if(e.getCode() == code){
                return e;
            }
        }
        return null;
    }

    public static boolean rightCode(int code){
        KnowledgeBizTypesEnum[] enums = KnowledgeBizTypesEnum.values();
        for(KnowledgeBizTypesEnum e : enums){
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
