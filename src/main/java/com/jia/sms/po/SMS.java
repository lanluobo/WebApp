package com.jia.sms.po;

/**
 * Created by jiaxl on 2016/11/5.
 */
public class SMS {
    //发送验证码的地址
    private String url;
    //发送验证码的参数
    private String param;
    //请求方式 0:get 1:post
    private Integer method;
    //发送成功标识
    private String result;
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SMS{" +
                "url='" + url + '\'' +
                ", param='" + param + '\'' +
                ", method=" + method +
                ", result='" + result + '\'' +
                '}';
    }
}
