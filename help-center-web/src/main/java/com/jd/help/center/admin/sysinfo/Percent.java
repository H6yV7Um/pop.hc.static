package com.jd.help.center.admin.sysinfo;

/**
 * User: yangsiyong
 * Date: 12-6-20
 * Time: ÉÏÎç9:56
 */
public class Percent {
    String name;
    String totalValue;
    String freeValue;
    String usedValue;
    int freePercent;
    int usedPercent;

    public Percent(String name, String totalValue, String usedValue, String freeValue) {
        this.name = name;
        this.totalValue = totalValue;
        this.usedValue = usedValue;
        this.freeValue = freeValue;
    }

    public String getName() {
        return name;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public String getFreeValue() {
        return freeValue;
    }

    public String getUsedValue() {
        return usedValue;
    }

    public int getFreePercent() {
        return freePercent;
    }

    public int getUsedPercent() {
        return usedPercent;
    }

    @Override
    public String toString() {
        return "Percent{" +
                "name='" + name + '\'' +
                ", totalValue='" + totalValue + '\'' +
                ", freeValue='" + freeValue + '\'' +
                ", usedValue='" + usedValue + '\'' +
                ", freePercent=" + freePercent +
                ", usedPercent=" + usedPercent +
                '}';
    }
}