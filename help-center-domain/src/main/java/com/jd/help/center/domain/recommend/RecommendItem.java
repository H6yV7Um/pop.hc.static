package com.jd.help.center.domain.recommend;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: liushiyao
 * Date: 14-4-8
 * Time: ÉÏÎç9:54
 * To change this template use File | Settings | File Templates.
 */
public class RecommendItem implements Serializable {
    private double w;
    private String sku;
    private String t;
    private String jp;
    private String mp;
    private String img;
    private String bn;
    private String[] subsku;
    private String c3;
    private String clk;
    private String impr;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getJp() {
        return jp;
    }

    public void setJp(String jp) {
        this.jp = jp;
    }

    public String getClk() {
        return clk;
    }

    public void setClk(String clk) {
        this.clk = clk;
    }

    public String getImpr() {
        return impr;
    }

    public void setImpr(String impr) {
        this.impr = impr;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public String getMp() {
        return mp;
    }

    public void setMp(String mp) {
        this.mp = mp;
    }

    public String getBn() {
        return bn;
    }

    public void setBn(String bn) {
        this.bn = bn;
    }

    public String[] getSubsku() {
        return subsku;
    }

    public void setSubsku(String[] subsku) {
        this.subsku = subsku;
    }

    public String getC3() {
        return c3;
    }

    public void setC3(String c3) {
        this.c3 = c3;
    }
}
