package com.jia.sms.controller;

import com.jia.common.BaseController;
import com.jia.common.po.JSONData;
import com.jia.sms.service.ISMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiaxl on 2016/11/5.
 */
@Controller
public class SMSController extends BaseController {
    public static Map<String, Integer> map = new ConcurrentHashMap<String, Integer>();
    public static Map<String, Integer> errorMap = new ConcurrentHashMap<String, Integer>();
    @Autowired
    ISMSService service;

    @ResponseBody
    @RequestMapping("/sendMsg")
    public JSONData sendMessage(@RequestParam String phone) {
        JSONData jsonData = new JSONData();
        logger.info(phone);
        try {
            if (service.doSendMsg(phone, 1)) {
                jsonData.setCode(0);
                jsonData.setMessage("发送成功");
            }
        } catch (Exception e) {
            logger.error("手动发信息异常", e);
        }
        return jsonData;
    }

    @ResponseBody
    @RequestMapping("/sendCode")
    public JSONData sendCode(@RequestParam String phone, HttpSession session) {
        JSONData jsonData = new JSONData();
        Integer count = map.get(phone);
        if (count == null) {
            count = 0;
        }
        if (count >= 3) {
            jsonData.setCode(-1);
            jsonData.setMessage("该号码已超过当日次数限制！");
            logger.warn(phone + "，该号码已超过当日次数限制！");
        } else {
            try {
                String code = service.doSendMsg(phone);
                if (code != null) {
                    session.setAttribute(phone, code);
                    jsonData.setCode(0);
                    jsonData.setMessage("发送成功");
                    map.put(phone, ++count);
                    errorMap.remove(phone);
                }

            } catch (Exception e) {
                logger.error("手动发信息异常", e);
                jsonData.setCode(-1);
                jsonData.setMessage("发送失败，系统异常");
            }
        }

        return jsonData;
    }
}
