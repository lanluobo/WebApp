package com.jia.train.po;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <dl>
 * <dt>U12306</dt>
 * <dd>Description:TODO</dd>
 * <dd>Copyright: Copyright (C) 2016</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2016年12月26日</dd>
 * </dl>
 *
 * @author 贾学雷
 */

/**
 * 登录相关参数
 */
public class U12306 {
    private String session;
    private String bIGipServerotn;
    private String user;
    private String psd;
    private String code="";
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getbIGipServerotn() {
        return bIGipServerotn;
    }

    public void setbIGipServerotn(String bIGipServerotn) {
        this.bIGipServerotn = bIGipServerotn;
    }

    public String getCookie(){
        return this.session+"; "+this.bIGipServerotn+"; current_captcha_type=Z";
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }


    @Override
    public String toString() {
        return "U12306{" +
                "session='" + session + '\'' +
                ", bIGipServerotn='" + bIGipServerotn + '\'' +
                ", user='" + user + '\'' +
                ", psd='" + psd + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
