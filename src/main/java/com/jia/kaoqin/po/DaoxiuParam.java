package com.jia.kaoqin.po;

/**
 * Created by jiaxl on 2016/10/13.
 */
public class DaoxiuParam {
    private String name;
    private String psd;
    private String action;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "DaoxiuParam{" +
                "name='" + name + '\'' +
                ", psd='" + psd + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
