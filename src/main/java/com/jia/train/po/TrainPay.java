package com.jia.train.po;

/**
 * Created by jiaxl on 2016/12/31.
 * 官网支付参数信息（bankId固定选择支付宝）
 */
public class TrainPay {

    private String tranData;
    private String transType;
    private String channelId;
    private String appId;
    private String merSignMsg;
    private String merCustomIp;
    private String orderTimeoutDate;
    private String bankId="33000010";


    public String getTranData() {
        return tranData;
    }

    public void setTranData(String tranData) {
        this.tranData = tranData;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMerSignMsg() {
        return merSignMsg;
    }

    public void setMerSignMsg(String merSignMsg) {
        this.merSignMsg = merSignMsg;
    }

    public String getMerCustomIp() {
        return merCustomIp;
    }

    public void setMerCustomIp(String merCustomIp) {
        this.merCustomIp = merCustomIp;
    }

    public String getOrderTimeoutDate() {
        return orderTimeoutDate;
    }

    public void setOrderTimeoutDate(String orderTimeoutDate) {
        this.orderTimeoutDate = orderTimeoutDate;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    @Override
    public String toString() {
        return "TrainPay{" +
                "tranData='" + tranData + '\'' +
                ", transType='" + transType + '\'' +
                ", channelId='" + channelId + '\'' +
                ", appId='" + appId + '\'' +
                ", merSignMsg='" + merSignMsg + '\'' +
                ", merCustomIp='" + merCustomIp + '\'' +
                ", orderTimeoutDate='" + orderTimeoutDate + '\'' +
                ", bankId='" + bankId + '\'' +
                '}';
    }
}
