package com.jd.help.center.domain.border;

import com.jd.help.domain.HttpsUtil;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 12-10-10
 * Time: ����9:31
 * To change this template use File | Settings | File Templates.
 */
public class BorderInfo implements Serializable {
    public final static String HELP_BORDER_KEY="key";
    public final static String HELP_BORDER_CONTENT="content";
    public final static String HELP_BORDER_REMARK="remark";
    public final static String HELP_BORDER_TYPE="type";


    //parse ��key
    private String key;

    //parse ������
    private String content;

    //1��ͷ�ļ���2��β�ļ���3��ϵͳ��ҳ
    private String type;

    //��ע
    private String remark;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * �Ƴ�content�� href��http:
     */
    public void removeHttp() {
        if (this.content != null) {
            this.content = HttpsUtil.removeHttp(this.content);
        }
    }
}
