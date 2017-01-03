package com.jia.kaoqin.po;

/**
 * Created by 贾学雷 on 2016/7/5.
 */
public class DaoxiuUser {

    public int employeeId;
    public String username;
    public String password;
    public int status;

    @Override
    public String toString() {
        return "DaoxiuUser{" +
                "employeeId=" + employeeId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
    }
}
