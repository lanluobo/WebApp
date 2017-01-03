package com.jia.kaoqin.service.impl;

import com.jia.common.BaseService;
import com.jia.common.util.HttpClientUtil;
import com.jia.kaoqin.mapper.KaoqinMapper;
import com.jia.kaoqin.po.SMSNotifyUser;
import com.jia.kaoqin.util.Kaoqin;
import com.jia.sms.controller.SMSController;
import com.jia.sms.service.ISMSService;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jiaxl on 2016/11/6.
 */
public class KaoqinSMSService extends BaseService{

    @Autowired
    KaoqinMapper mapper;

    @Autowired
    Kaoqin kaoqin;

    ExecutorService executors=Executors.newFixedThreadPool(10);
    @Autowired
    ISMSService smsService;

    public void checkAndSendMsg() {
        logger.debug("进入checkAndSendMsg（）...");
        List<SMSNotifyUser>list = mapper.selectSMSNotifyUsers();
        try {
            checkUser(list);
            sendMsg(list);
        } catch (Exception e) {
            logger.error("过滤用户打卡记录异常",e);
            try {
                smsService.doSendMsg("18712855776",2);
                smsService.doSendMsg("13296747704",2);
            } catch (Exception e1) {
                logger.error("通知管理员系统异常失败",e1);
            }
        }
    }

    private void sendMsg(List<SMSNotifyUser> list) {
        for(SMSNotifyUser user:list){
            final SMSNotifyUser u=user;
            executors.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        smsService.doSendMsg(u.getPhone(),0);
                    } catch (Exception e) {
                        logger.error("发送信息异常重试一次",e);
                        try {
                            smsService.doSendMsg(u.getPhone(),0);
                        } catch (Exception e1) {
                        }
                    }
                }
            });
        }
    }

    private void checkUser(List<SMSNotifyUser> list) throws Exception {
        logger.info("开始过滤打卡用户："+list);
        HttpClient client= HttpClientUtil.getHttpClient();
        List<SMSNotifyUser>removeList=new ArrayList<SMSNotifyUser>();
        for (SMSNotifyUser user : list) {
            try {
                kaoqin.login(user.getEmployeeId(),client);
                List <Date>dates=kaoqin.get1Data(client);
                logger.info(user.getEmployeeId()+":"+dates);
                if(dates.size()>=1){
                    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd 18:00:00");
                    SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String today=format.format(new Date());
                    if((dates.get(dates.size()-1)).after(format1.parse(today))){
                        removeList.add(user);
                    }
                }else {
                    removeList.add(user);
                }
            } catch (Exception e) {
                removeList.add(user);
                logger.error(user+"获取当天考勤记录异常",e);
            }

        }
        list.removeAll(removeList);
    }

    public void clearSMSCount(){
        SMSController.map.clear();
        SMSController.errorMap.clear();
        logger.info("清空号码短信次数限制...");
        logger.info("清空号码短信错误次数限制...");
    }
}
