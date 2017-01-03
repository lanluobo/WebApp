package com.jia.kaoqin.controller;

import com.jia.common.BaseController;
import com.jia.common.po.JSONData;
import com.jia.kaoqin.po.AliPay;
import com.jia.kaoqin.po.DaoxiuEntity;
import com.jia.kaoqin.service.IDaoxiuService;
import com.jia.kaoqin.util.Contants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * Created by jiaxl on 2016/10/13.
 */
@Controller
public class DaoxiuController extends BaseController {

    @Autowired
    IDaoxiuService daoxiuService;

    @ResponseBody
    @RequestMapping("/daoxiu")
    public JSONData daoXiu(DaoxiuEntity entity, HttpSession session) {
        JSONData json = new JSONData();
        if (StringUtils.hasText(entity.username) && StringUtils.hasText(entity.password)) {
            if (entity.getAction().equals("daoxiu")) {
                entity.type = "手动";
                return daoxiuService.submitDaoxiu(session, entity);

            } else if (entity.getAction().equals("chexiao")) {
                return daoxiuService.chexiaoDaoxiu(session, entity);
            } else {
                return Contants.INVALID_JSON;
            }
        }
        json.setMessage("参数错误");
        return json;
    }


    @RequestMapping("/test12345")
    @ResponseBody
    public String test1(AliPay pay) throws IOException {
        logger.info(pay);
        return "success";
    }

    @RequestMapping("/api.php")
    @ResponseBody
    public String test() {
        return "{\"result\":true,\"result_msg\":\"\\u8d26\\u53f7\\u6388\\u6743\\u6210\\u529f\\uff0c\\u6709\\u6548\\u671f[" + new Date().toLocaleString() + "] \u5230 [" + new Date(new Date().getTime() + 30 * 24 * 60 * 60 * 1000l).toLocaleString() + "]\",\"data\":{\"info\":{\"id\":\"1825\",\"account\":\"76638403@qq.com\",\"licence\":\"00bec04e4d8c3d4a52fa1cda5e6b6879\",\"start_time\":" + new Date().getTime() / 1000 + ",\"end_time\":" + (new Date().getTime() / 1000 + 30 * 24 * 60 * 60) + ",\"last_time\":0,\"last_client_ip\":null,\"last_version\":null,\"trade_no\":\"20161120200040011100300083523919\",\"heartbeat_time\":null,\"is_alarm\":\"0\",\"alarm_no\":null,\"alarm_time\":null,\"server_time\":" + new Date().getTime() / 1000 + "}}}";
    }
}
