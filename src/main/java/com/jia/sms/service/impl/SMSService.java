package com.jia.sms.service.impl;

import com.jia.common.util.HttpClientUtil;
import com.jia.sms.mapper.SMSMapper;
import com.jia.sms.po.SMS;
import com.jia.sms.po.SMSRecord;
import com.jia.sms.service.ISMSService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiaxl on 2016/11/5.
 */
@Service
public class SMSService implements ISMSService {
    @Autowired
    SMSMapper mapper;
    Logger logger=Logger.getLogger(this.getClass());
    List<SMS> list;
    List<SMS> listCode;
    @Override
    public boolean doSendMsg(String phone,int type) throws Exception {
        if (list == null) {
            synchronized (this) {
                if (list == null) {
                    list = mapper.getSMSList();
                }
            }
        }
        SMSRecord record = new SMSRecord();

        for (SMS s : list) {
            record.setPhone(phone);
            record.setType(type);
            if (s.getMethod() == 0) {
                try {
                    record.setContent(HttpClientUtil.getDataByGet(s.getUrl(), s.getParam().replaceAll("\\*\\*\\*", phone)));
                }catch (Exception e){
                    logger.error("发送信息异常，换下一个平台发送",e);
                    continue;
                }

                record.setSuccess(0);
                mapper.insertSMSRecord(record);
            } else {
                try {
                    record.setContent(HttpClientUtil.getDataByPost(s.getUrl(), s.getParam().replaceAll("\\*\\*\\*", phone)));
                }catch (Exception e){
                    logger.error("发送信息异常，换下一个平台发送",e);
                    continue;
                }
                record.setSuccess(1);
                mapper.insertSMSRecord(record);
            }
            if (record.getContent().contains(s.getResult())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String doSendMsg(String phone) throws Exception {
        if (listCode == null) {
            synchronized (this) {
                if (listCode == null) {
                    listCode = mapper.getSMSListCode();
                }
            }
        }
        SMSRecord record = new SMSRecord();

        for (SMS s : listCode) {
            record.setPhone(phone);
            record.setType(2);
            if (s.getMethod() == 1) {
                record.setContent(HttpClientUtil.getDataByPost(s.getUrl(), s.getParam().replaceAll("\\*\\*\\*", phone)));
                record.setSuccess(1);
                mapper.insertSMSRecord(record);
            } else {
                record.setContent(HttpClientUtil.getDataByGet(s.getUrl(), s.getParam().replaceAll("\\*\\*\\*", phone)));
                record.setSuccess(0);
                mapper.insertSMSRecord(record);
            }
            if (record.getContent().contains(s.getResult())) {
                Pattern p=Pattern.compile("\"\\d{4,6}");
                Matcher m=p.matcher(record.getContent());
                if(m.find()){
                    return record.getContent().substring(m.start()+1,m.end());
                }
            }
        }
        return null;
    }
}
