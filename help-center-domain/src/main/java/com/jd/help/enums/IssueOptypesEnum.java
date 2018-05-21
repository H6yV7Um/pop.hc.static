package com.jd.help.enums;

/**
 * Created by yfxialiang on 2018/4/8.
 */
public enum IssueOptypesEnum {
    ADD(1, "add"), DEL(2, "del");

    private int code;

    private String desc;


    IssueOptypesEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static IssueOptypesEnum fromCode(int code) {
        IssueOptypesEnum[] enums = IssueOptypesEnum.values();
        for (IssueOptypesEnum e : enums) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }

    public static boolean rightCode(int code){
        IssueOptypesEnum[] enums = IssueOptypesEnum.values();
        for (IssueOptypesEnum e : enums) {
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
