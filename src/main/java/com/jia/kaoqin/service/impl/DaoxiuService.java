package com.jia.kaoqin.service.impl;

import com.jia.common.BaseService;
import com.jia.common.po.JSONData;
import com.jia.common.util.HttpClientUtil;
import com.jia.kaoqin.mapper.KaoqinMapper;
import com.jia.kaoqin.po.DaoxiuEntity;
import com.jia.kaoqin.service.IDaoxiuService;
import com.jia.kaoqin.util.Contants;
import com.jia.kaoqin.util.DaoxiuUtil;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by jiaxl on 2016/10/14.
 */
@Service
public class DaoxiuService extends BaseService implements IDaoxiuService {

    @Autowired
    private KaoqinMapper mapper;


    public JSONData submitDaoxiu(HttpSession session, DaoxiuEntity entity) {

        JSONData json=new JSONData();
        /* 设置倒休起止时间 */
        DaoxiuUtil.setStartEndTime(entity);
        //检查是否已经提交过
        if (mapper.selectTodayRecord(entity)) {
            return Contants.SUBMITED_JSON;
        }
        HttpClient client = (HttpClient) session.getAttribute(entity.username);
        try {
            if (client == null) {
                client = HttpClientUtil.getHttpClient();
                if (!DaoxiuUtil.login(client, entity.username, entity.password)) {
                    return Contants.LOGIN_FAIL_JSON;
                }
//                session.setAttribute(entity.username, client);
            }
        /* 获取倒休页面动态相关参数 */
            DaoxiuUtil.setEntity(client, entity);
            /* 模拟计算日期请求 */
            DaoxiuUtil.calculate(client, entity);
            /* 正式提交倒休单前一个无聊请求 */
            DaoxiuUtil.doSomeThing(client, entity);

			/* 设置倒休原因 */
            entity.reason = "堵车";
            /* 提交倒休 */
            if (DaoxiuUtil.submit(client, entity)) {
                logger.debug(entity.username + DaoxiuUtil.SUCCESSMSG);
                // 相关参数入库，撤销时使用，防止错误撤销其他正常倒休
                DaoxiuUtil.setSummaryIdAndAffairId(client, entity);
                mapper.insertLogRecord(entity);
                mapper.deleteCurrentRecord(entity.username);
                mapper.insertCurrentRecord(entity);
                logger.debug("成功入库" + entity.username);
                return Contants.DXSUCCESS_JSON;
            }
        } catch (Exception e) {
           logger.error(e);
            json.setCode(-1);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    public JSONData chexiaoDaoxiu(HttpSession session, final DaoxiuEntity entity) {
        HttpClient client = (HttpClient) session.getAttribute(entity.username);
        JSONData json = new JSONData();
        try {
            if (client == null) {
                client = HttpClientUtil.getHttpClient();
                if (!DaoxiuUtil.login(client, entity.username, entity.password)) {
                    return Contants.LOGIN_FAIL_JSON;
                }
//            session.setAttribute(entity.username, client);
            }
            /*
             * 获取倒休单id
			 */
            DaoxiuEntity e = mapper.selectSummaryIdAndAffairId(entity.username);
            entity.setAffairId(e.affairId);
            entity.setSummaryId(e.summaryId);
            logger.debug(e.affairId + ":" + e.summaryId);
			/* 发送撤销请求 */
            if (DaoxiuUtil.repalSubmit(client, entity)) {
                json.setCode(0);
                json.setMessage(Contants.CXSUCCESS);

            } else {
                json.setCode(-1);
                json.setMessage(Contants.CXERROR);
            }
            new Thread() {
                @Override
                public void run() {
                    mapper.updateStatus(entity);
                }
            }.start();
            return json;
        } catch (Exception e) {
            logger.error(e);
        }
        json.setCode(-1);
        json.setMessage(Contants.CXFAIL);
        return json;
    }
}
