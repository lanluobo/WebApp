package com.jia.kaoqin.service.impl;

import com.jia.common.BaseService;
import com.jia.common.util.HttpClientUtil;
import com.jia.kaoqin.mapper.KaoqinMapper;
import com.jia.kaoqin.po.KaoqinEntity;
import com.jia.kaoqin.po.SMSNotifyUser;
import com.jia.kaoqin.service.IKaoqinService;
import com.jia.kaoqin.util.Kaoqin;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by jiaxl on 2016/10/19.
 */
@Service
public class KaoqinService extends BaseService implements IKaoqinService {

    @Autowired
    Kaoqin kaoqin;
    @Autowired
    KaoqinMapper mapper;
    public List<KaoqinEntity> getDatas(HttpSession session, String name) {

        HttpClient client = (HttpClient) session.getAttribute(name);
        List<KaoqinEntity> data = new ArrayList<KaoqinEntity>();
        try {
            if (client == null) {
                client = HttpClientUtil.getHttpClient();
                if (!kaoqin.login(name, client)) {
                    return data;
                }
                session.setAttribute(name, client);
            }
            List<Date> list = kaoqin.getData(client);
            List<KaoqinEntity> entities = kaoqin.calculate(list);
            for (KaoqinEntity d : entities) {

                int shours = d.startTime.getHours();
                int smin = d.startTime.getMinutes();
                int sday = d.startTime.getDay();
                int ehours = d.endTime.getHours();
                int emin = d.endTime.getMinutes();
                if (sday == 6 || sday == 0) {

                    d.status = "加班(周末)";
                    data.add(d);
                    continue;
                }
                boolean flag = true;
                if (shours > 9 || shours >= 9 && smin > 0) {
                    d.status += "迟到";
                    flag = false;
                }
                if (ehours > 20 || ehours == 20 && emin >= 30) {
                    if(!flag){
                        d.status += "、加班(日常)";
                    }else{
                        d.status += "加班(日常)";
                    }
                    flag = false;
                }
                if (ehours > 16 && ehours <= 17) {
                    if (flag) {
                        d.status += "早退";
                    } else {
                        d.status += "、早退";
                    }

                    flag = false;
                }
                if (ehours >= 9 && ehours <= 16) {
                    if (flag) {
                        d.status += "旷工";
                    } else {
                        d.status += "、旷工";
                    }
                    flag = false;
                }
                if (flag) {
                    d.status = "正常";
                }
                data.add(d);
            }
        } catch (Exception e) {
            logger.error("获取考勤数据异常",e);
        }

        if (data != null) {
            Collections.reverse(data);
        }
        return data;
    }

    @Override
    public void addSMSNotifyUser(SMSNotifyUser user) {
        mapper.insertSMSNotifyUser(user);
    }

    @Override
    public int delSMSNotifyUser(SMSNotifyUser user) {
        return mapper.deleteSMSNotifyUser(user);
    }
}
