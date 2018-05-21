package com.jd.help.center.domain.recommend;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: liushiyao
 * Date: 14-4-8
 * Time: ÉÏÎç11:25
 * To change this template use File | Settings | File Templates.
 */
public class RecommendDebug implements Serializable {

    private String[] msg_stack;
    private String latency;
    private int recommender_latency;
    private int predictor_latency;

    public String[] getMsg_stack() {
        return msg_stack;
    }

    public void setMsg_stack(String[] msg_stack) {
        this.msg_stack = msg_stack;
    }

    public String getLatency() {
        return latency;
    }

    public void setLatency(String latency) {
        this.latency = latency;
    }

    public int getRecommender_latency() {
        return recommender_latency;
    }

    public void setRecommender_latency(int recommender_latency) {
        this.recommender_latency = recommender_latency;
    }

    public int getPredictor_latency() {
        return predictor_latency;
    }

    public void setPredictor_latency(int predictor_latency) {
        this.predictor_latency = predictor_latency;
    }
}
