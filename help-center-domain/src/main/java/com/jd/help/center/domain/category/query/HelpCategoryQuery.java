package com.jd.help.center.domain.category.query;

import com.jd.help.center.domain.category.HelpCategory;

/**
 * Created by gaofei on 14-2-24
 * 帮助中心分类请求对象
 *
 */
public class HelpCategoryQuery extends HelpCategory {
    /**
     * 系统ID
     */
    private Integer sysId;


    public Integer getSysId() {
        return sysId;
    }

    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

}
