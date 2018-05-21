package com.jd.help.jmq;

import java.io.Serializable;

/**
 * Created by yfxialiang on 2018/4/13.
 */
public class UpdateSceneMessage implements Serializable {
    private static final long serialVersionUID = 4398744300624945725L;

    //sceneId
    private Long sceneId;

    //操作类型，对应 SceneOpTypeEnum中的值
    private int opType;

    public Long getSceneId() {
        return sceneId;
    }

    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }
}
