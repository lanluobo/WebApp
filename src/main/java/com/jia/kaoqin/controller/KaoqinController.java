package com.jia.kaoqin.controller;

import com.alibaba.fastjson.JSONArray;
import com.jia.common.BaseController;
import com.jia.common.po.JSONData;
import com.jia.kaoqin.po.KaoqinEntity;
import com.jia.kaoqin.po.SMSNotifyUser;
import com.jia.kaoqin.service.IKaoqinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.jia.sms.controller.SMSController.errorMap;

/**
 * Created by jiaxl on 2016/10/2.
 */
@Controller
public class KaoqinController extends BaseController {

    @Autowired
    IKaoqinService kaoqinService;


    @RequestMapping("/kaoqin")
    public String index() {
        return "kaoqin";
    }

    @RequestMapping("/")
    public String index1() {
        return "kaoqin";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public ModelAndView getData(@RequestParam(value = "name", required = false) String name, HttpSession session) {
        ModelAndView model = new ModelAndView();
        model.setViewName("kaoqin");
        logger.debug(name);
        if (name == null) {
            return model;
        }
        model.getModel().put("name", name);
        List<KaoqinEntity> data = null;
        try {
            data = kaoqinService.getDatas(session, name);
            model.getModel().put("data", data);
            logger.debug("共" + data.size() + "天记录");
        } catch (Exception e) {
            logger.error(e);
        }
        return model;
    }

    @RequestMapping("/getDataByWeiXin")
    public JSONArray getDataByWeiXin(@RequestParam(value = "name") String name) {
        JSONArray jsonArray = new JSONArray();
        if (name == null) {
            return jsonArray;
        }
        logger.debug(name);
        if (name != null && !("".equals(name))) {
            List<KaoqinEntity> data = null;
            try {
//                data = Kaoqin.get10Time(name);
            } catch (Exception e) {
                logger.error(e);
            }
            if (data != null) {
                logger.debug("共" + data.size() + "天记录");
                jsonArray.addAll(data);
            }
        }
        return jsonArray;
    }


    @RequestMapping("/bindPhone")
    public String bindPhone() {
        return "bindPhone";
    }

    @ResponseBody
    @RequestMapping("/bind")
    public JSONData bind(SMSNotifyUser user, HttpSession session) {
        logger.info("进入绑定bind()..."+user);
        JSONData jsonData = new JSONData();
        if (!user.checkParam()) {
            jsonData.setCode(-1);
            jsonData.setMessage("参数不能为空！");
            logger.info(" 参 数不能为空！"+user);
            return jsonData;
        }
        String code = (String) session.getAttribute(user.getPhone());
        if (code == null) {
            jsonData.setCode(-1);
            jsonData.setMessage("请先获取验证码！");
            logger.info(" 请 先获取验证码！"+user);
            return jsonData;
        }
        if (code.equals(user.getCode())) {
            try {
                session.removeAttribute(user.getPhone());
                user.setEmployeeId(1 + user.getEmployeeId());
                kaoqinService.addSMSNotifyUser(user);
                jsonData.setCode(0);
                jsonData.setMessage("开通服务成功！");
                logger.info("开通服务成功！"+user);
            } catch (Exception e) {

                if (e.getMessage().contains("SMS_USER_EMPLOYEE_ID_PHONE_UK")) {
                    jsonData.setCode(-1);
                    jsonData.setMessage("该工号、手机号已开通服务！");
                    logger.info(" 该工号、手机号已开通服务！"+user);
                } else {
                    logger.error("开通服务异常", e);
                    jsonData.setCode(-1);
                    jsonData.setMessage("系统异常，已通知管理员请稍等..");
                    logger.info("系统异常，已通知管理员请稍等.."+user);
                }
            }
        } else {
            jsonData.setCode(-1);
            jsonData.setMessage("验证码错误！");
            logger.info("验 证码错误！"+user);
            Integer errorCount=errorMap.get(user.getPhone());
            if(errorCount!=null){
                if (errorCount>=3){
                    jsonData.setCode(-1);
                    jsonData.setMessage("验证码失效，请重新获取！");
                    logger.info("验 证码失效，请重新获取！"+user);
                    session.removeAttribute(user.getPhone());
                }else {
                    errorMap.put(user.getPhone(),++errorCount);
                }
            }else{
                errorMap.put(user.getPhone(),1);
            }

        }
        return jsonData;
    }

    @ResponseBody
    @RequestMapping("/unbind")
    public JSONData unbind(SMSNotifyUser user, HttpSession session) {
        logger.info("进入解绑unbind()..."+user);
        JSONData jsonData = new JSONData();
        if (!user.checkParam()) {
            jsonData.setCode(-1);
            jsonData.setMessage("参数不能为空！");
            logger.info(" 参数不能为空！"+user);
            return jsonData;
        }
        String code = (String) session.getAttribute(user.getPhone());
        if (code == null) {
            jsonData.setCode(-1);
            jsonData.setMessage("请先获取验证码！");
            logger.info(" 请先获取验证码！"+user);
            return jsonData;
        }
        if (code.equals(user.getCode())) {
            try {
                session.removeAttribute(user.getPhone());
                user.setEmployeeId(1 + user.getEmployeeId());
                if (kaoqinService.delSMSNotifyUser(user) == 0) {
                    jsonData.setCode(-1);
                    jsonData.setMessage("没有开通服务，让我如何关闭？");
                    logger.info("没有开通服务，让我如何关闭？"+user);
                }else{
                    jsonData.setCode(0);
                    jsonData.setMessage("关闭服务成功！");
                    logger.info("关闭服务成功！"+user);
                }
            } catch (Exception e) {
                logger.error("关闭服务异常", e);
                jsonData.setCode(-1);
                jsonData.setMessage("系统异常，已通知管理员请稍等..");

            }
        } else {
            jsonData.setCode(-1);
            jsonData.setMessage("验证码错误！");
            logger.info("验证码错误！"+user);
            Integer errorCount=errorMap.get(user.getPhone());
            if(errorCount!=null){
                if (errorCount>=3){
                    jsonData.setCode(-1);
                    jsonData.setMessage("验证码失效，请重新获取！");
                    logger.info("验证码失效,请重新获取！"+user);
                    session.removeAttribute(user.getPhone());
                }else {
                    errorMap.put(user.getPhone(),++errorCount);
                }
            }else{
                errorMap.put(user.getPhone(),1);
            }
        }
        return jsonData;
    }
}
