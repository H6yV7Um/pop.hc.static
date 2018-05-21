package com.jd.help.enums;

/**
 * Created by yfxialiang on 2018/4/13.
 */
public enum SceneOpTypeEnum {
    ADDDBES(1, "adddbes"), //��sceneͬʱ����knowledge��db��es
    DELDBES(2, "deldbes"), //ͬʱɾ��knowledge��db��es��scene
    ADDDB(3, "adddb"),//��sceneֻ����knowledge��db
    DELDB(4, "deldb");//ֻɾ��nowledge��db

    private int code;

    private String desc;


    SceneOpTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SceneOpTypeEnum fromCode(int code) {
        SceneOpTypeEnum[] enums = SceneOpTypeEnum.values();
        for (SceneOpTypeEnum e : enums) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }

    public static boolean rightCode(int code){
        SceneOpTypeEnum[] enums = SceneOpTypeEnum.values();
        for (SceneOpTypeEnum e : enums) {
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
