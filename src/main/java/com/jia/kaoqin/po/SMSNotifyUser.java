package com.jia.kaoqin.po;

/**
 * Created by jiaxl on 2016/11/6.
 */
public class SMSNotifyUser {

    private String employeeId;
    private String phone;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "SMSNotifyUser{" +
                "employeeId='" + employeeId + '\'' +
                ", phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public boolean checkParam(){
        return this.code!=null&&this.phone!=null&&this.employeeId!=null;
    }
}
