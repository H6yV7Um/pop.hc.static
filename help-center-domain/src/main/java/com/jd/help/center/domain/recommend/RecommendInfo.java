package com.jd.help.center.domain.recommend;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liushiyao
 * Date: 14-4-8
 * Time: ÉÏÎç9:49
 * To change this template use File | Settings | File Templates.
 */
public class RecommendInfo implements Serializable {
    private String encode;
    private boolean success;
    private String error_msg;
    private int latency;
    private String impr;
    private List<RecommendItem> data;
    private RecommendDebug debug_info;

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public String getImpr() {
        return impr;
    }

    public void setImpr(String impr) {
        this.impr = impr;
    }

    public List<RecommendItem> getData() {
        return data;
    }

    public void setData(List<RecommendItem> data) {
        this.data = data;
    }

    public RecommendDebug getDebug_info() {
        return debug_info;
    }

    public void setDebug_info(RecommendDebug debug_info) {
        this.debug_info = debug_info;
    }
}
