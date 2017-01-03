package com.test.liketry;

/**
 * Created by jiaxl on 2016/12/2.
 */
public class LikeTry {

    private String phone;
    private String password;
    private String cookie;
    private Integer shidou;
    public Integer getShidou() {
        return shidou;
    }

    public void setShidou(Integer shidou) {
        this.shidou = shidou;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    @Override
    public String toString() {
        return "LikeTry{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", cookie='" + cookie + '\'' +
                '}';
    }
}
