package com.jia.sms.service;

/**
 * Created by jiaxl on 2016/11/5.
 */
public interface ISMSService {
    boolean doSendMsg(String phone,int type) throws Exception;
    String doSendMsg(String phone) throws Exception;
}
