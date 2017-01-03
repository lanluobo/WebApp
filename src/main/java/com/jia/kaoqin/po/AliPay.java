package com.jia.kaoqin.po;

/**
 * <dl>
 * <dt>AliPay</dt>
 * <dd>Description:TODO</dd>
 * <dd>Copyright: Copyright (C) 2016</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2016年11月20日</dd>
 * </dl>
 *
 * @author 贾学雷
 */
public class AliPay {

    private String sig;
    private String tradeNo;
    private String desc;
    private String time;
    private String username;
    private String userid;
    private String amount;
    private String status;

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AliPay{" +
                "sig='" + sig + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", desc='" + desc + '\'' +
                ", time='" + time + '\'' +
                ", username='" + username + '\'' +
                ", userid='" + userid + '\'' +
                ", amount='" + amount + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
