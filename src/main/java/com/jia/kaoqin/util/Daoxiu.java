package com.jia.kaoqin.util;

import com.jia.common.util.HttpClientUtil;
import com.jia.kaoqin.po.DaoxiuEntity;
import org.apache.http.client.HttpClient;

import com.alibaba.fastjson.JSONObject;

public class Daoxiu {

//	测试方法
	public static void main(String[] args) throws Exception {

		DaoxiuEntity entity = new DaoxiuEntity("xiaodi", "942634");
//
		daoXiu(entity);
		cheXiao(entity);
	}

	public static JSONObject daoXiu(DaoxiuEntity entity) {

		HttpClient client = HttpClientUtil.getHttpClient();
		JSONObject json = new JSONObject();
		try {
			if (DaoxiuUtil.login(client, entity.username, entity.password)) {
				System.out.println(DaoxiuUtil.getSystemTime() + entity.username
						+ DaoxiuUtil.LOGINSUCCESS);
			} else {
				System.out.println(DaoxiuUtil.getSystemTime() + entity.username
						+ DaoxiuUtil.LOGINERROR);
				json.put("code", -1);
				json.put("message", Contants.LOGINFAIL);
				json.put("data", null);
				return json;
			}
			/* 获取倒休页面动态相关参数 */
			DaoxiuUtil.setEntity(client, entity);
			/* 模拟计算日期请求 */
			DaoxiuUtil.calculate(client, entity);
			/* 正式提交倒休单前一个无聊请求 */
			DaoxiuUtil.doSomeThing(client, entity);
			/* 设置倒休起止时间 */
			DaoxiuUtil.setStartEndTime(entity);
			/* 设置倒休原因 */
			entity.reason = "堵车";
			/* 提交倒休 */
			if (DaoxiuUtil.submit(client, entity)) {
				System.out.println(DaoxiuUtil.getSystemTime() + entity.username
						+ DaoxiuUtil.SUCCESSMSG);
				// 相关参数入库，撤销时使用，防止错误撤销其他正常倒休
				json.put("code", 0);
				json.put("message", Contants.DXSUCCESS);
				json.put("data", null);
				return json;
			} else {
				System.out.println(DaoxiuUtil.getSystemTime() + entity.username
						+ DaoxiuUtil.FAILMSG);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("code", -1);
		json.put("message", Contants.DXFAIL);
		json.put("data", null);
		return json;
	}

	public static JSONObject cheXiao(DaoxiuEntity entity) {

		HttpClient client = HttpClientUtil.getHttpClient();
		JSONObject json = new JSONObject();
		try {
			if (DaoxiuUtil.login(client, entity.username, entity.password)) {
				System.out.println(DaoxiuUtil.getSystemTime() + entity.username
						+ DaoxiuUtil.LOGINSUCCESS);
			} else {
				System.out.println(DaoxiuUtil.getSystemTime() + entity.username
						+ DaoxiuUtil.LOGINERROR);
				json.put("code", -1);
				json.put("message", Contants.LOGINFAIL);
				json.put("data", null);
				return json;
			}
			/*
			 * 获取倒休单id
			 */
			DaoxiuUtil.getSummaryIdAndAffairId(entity);
			/* 发送撤销请求 */
			if (DaoxiuUtil.repalSubmit(client, entity)) {
				json.put("code", 0);
				json.put("message", Contants.CXSUCCESS);
				json.put("data", null);
			} else {
				json.put("code", -1);
				json.put("message", Contants.CXERROR);
				json.put("data", null);
			}
			DBUtil.chexiaoUpdateStatus(entity);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("code", -1);
		json.put("message", Contants.CXFAIL);
		json.put("data", null);
		DBUtil.chexiaoUpdateStatus(entity);
		return json;
	}
}
