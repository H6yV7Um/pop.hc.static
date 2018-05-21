package com.jd.help.enums;

/**
 * Created by xialiang on 2018/4/4.
 * 商家学习中心中点播课程的操作类型
 */
public enum OndemandCourseOpTypeEnum {

    ADD(1, "add"), DEL(2, "del"),COUNT(3, "playCount");

    private int code;

    private String desc;


    OndemandCourseOpTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OndemandCourseOpTypeEnum fromCode(int code) {
        OndemandCourseOpTypeEnum[] enums = OndemandCourseOpTypeEnum.values();
        for (OndemandCourseOpTypeEnum e : enums) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }

    public static boolean rightCode(int code){
        OndemandCourseOpTypeEnum[] enums = OndemandCourseOpTypeEnum.values();
        for (OndemandCourseOpTypeEnum e : enums) {
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
