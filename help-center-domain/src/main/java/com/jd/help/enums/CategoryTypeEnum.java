package com.jd.help.enums;

/**
 * Created by yfxialiang on 2018/4/28.
 */
public enum CategoryTypeEnum {
    POP(0, "POP商家"),
    SELF_EMPLOYED(1, "自营商家");

    private int code;

    private String desc;


    CategoryTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CategoryTypeEnum fromCode(int code) {
        CategoryTypeEnum[] enums = CategoryTypeEnum.values();
        for (CategoryTypeEnum e : enums) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }

    public static boolean rightCode(int code){
        CategoryTypeEnum[] enums = CategoryTypeEnum.values();
        for (CategoryTypeEnum e : enums) {
            if (e.getCode() == code) {
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
