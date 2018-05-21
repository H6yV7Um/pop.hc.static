package com.jd.help.enums;

/**
 * Created by yfxialiang on 2018/5/15.
 */
public enum KnowledgeSearchSortEnum {
    //�ۺ����������+pv+����ʱ��
    sort_vender_help_center_insert_desc(1,"sort_vender_help_center_insert_desc"),

    //��Ʒ���������+����ʱ��
    sort_vender_modify_time_desc(2,"sort_vender_modify_time_desc");

    private int code;
    private String desc;

    KnowledgeSearchSortEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static KnowledgeSearchSortEnum fromCode(int code){
        KnowledgeSearchSortEnum[] enums = KnowledgeSearchSortEnum.values();
        for(KnowledgeSearchSortEnum e : enums){
            if(e.getCode() == code){
                return e;
            }
        }
        return null;
    }

    public static boolean rightCode(int code){
        KnowledgeSearchSortEnum[] enums = KnowledgeSearchSortEnum.values();
        for(KnowledgeSearchSortEnum e : enums){
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
