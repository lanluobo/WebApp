package com.jia.train.po;

/**
 * Created by jiaxl on 2017/1/1.
 * 车次信息查询条件
 */
public class QueryInfo {
    private String startId="";
    private String endId="";
    private String date="";
    private String type="ADULT";

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getEndId() {
        return endId;
    }

    public void setEndId(String endId) {
        this.endId = endId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "QueryInfo{" +
                "startId='" + startId + '\'' +
                ", endId='" + endId + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public boolean check() {
        if(startId.equals("")||endId.equals("")||date.equals("")||type.equals("")){
            return false;
        }
        return true;
    }
}
