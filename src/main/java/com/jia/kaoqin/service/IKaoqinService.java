package com.jia.kaoqin.service;

import com.jia.kaoqin.po.KaoqinEntity;
import com.jia.kaoqin.po.SMSNotifyUser;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by jiaxl on 2016/10/19.
 */
public interface IKaoqinService {

    List<KaoqinEntity> getDatas(HttpSession session,String name);

    void addSMSNotifyUser(SMSNotifyUser user);

    int delSMSNotifyUser(SMSNotifyUser user);
}
