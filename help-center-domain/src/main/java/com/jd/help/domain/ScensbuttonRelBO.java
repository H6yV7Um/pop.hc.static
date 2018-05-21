package com.jd.help.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lining7 on 2017/10/11.
 */
public class ScensbuttonRelBO implements Serializable {

    private Integer cataId;

    private Integer siteId;

    private List<ScenesButtonRel> scenesButtonRelList;

    public Integer getCataId() {
        return cataId;
    }

    public void setCataId(Integer cataId) {
        this.cataId = cataId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public List<ScenesButtonRel> getScenesButtonRelList() {
        return scenesButtonRelList;
    }

    public void setScenesButtonRelList(List<ScenesButtonRel> scenesButtonRelList) {
        this.scenesButtonRelList = scenesButtonRelList;
    }
}
