package com.jia.train.po;

/**
 * Created by jiaxl on 2016/12/31. @bjb|北京北|VAP|beijingbei|bjb|0
 * 站点信息
 */
public class StationNameId {

    private String abbr;
    private String cnName;
    private String id;
    private String usName;
    private int order;

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsName() {
        return usName;
    }

    public void setUsName(String usName) {
        this.usName = usName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return cnName;
    }
}
