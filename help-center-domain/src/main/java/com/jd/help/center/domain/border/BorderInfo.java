package com.jd.help.center.domain.border;

import com.jd.help.domain.HttpsUtil;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 12-10-10
 * Time: 上午9:31
 * To change this template use File | Settings | File Templates.
 */
public class BorderInfo implements Serializable {
    public final static String HELP_BORDER_KEY="key";
    public final static String HELP_BORDER_CONTENT="content";
    public final static String HELP_BORDER_REMARK="remark";
    public final static String HELP_BORDER_TYPE="type";


    //parse 的key
    private String key;

    //parse 的内容
    private String content;

    //1：头文件，2：尾文件，3：系统主页
    private String type;

    //备注
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
     * 移除content里 href的http:
     */
    public void removeHttp() {
        if (this.content != null) {
            this.content = HttpsUtil.removeHttp(this.content);
        }
    }
}
