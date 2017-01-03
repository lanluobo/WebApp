package com.jia.sms.po;

import java.util.Date;

/**
 * Created by jiaxl on 2016/11/6.
 */
public class SMSRecord {
    //发送验证码的手机号
    private String phone;
    //发送验证码的响应
    private String content;
    //是否发送成功0：成功 1：失败
    private Integer success;
    private Integer type;
    private Date insertTime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SMSRecord{" +
                "phone='" + phone + '\'' +
                ", content='" + content + '\'' +
                ", success=" + success +
                ", type=" + type +
                ", insertTime=" + insertTime +
                '}';
    }
}
