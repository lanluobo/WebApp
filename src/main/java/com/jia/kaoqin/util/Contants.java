package com.jia.kaoqin.util;

import com.jia.common.po.JSONData;

public class Contants {

	public static final String INVALID="非法请求";
	public static final String DXSUCCESS="倒休提交成功";
	public static final String DXFAIL="倒休失败";
	public static final String CXSUCCESS="撤销成功";
	
	public static final String CXFAIL="撤销失败,请登陆oa官网撤销";
	public static final String CXERROR="撤销失败:已经结束,不允许撤销";
	public static final String LOGINFAIL = "登录失败，请检查用户名、密码";
	public static final String SUBMITED = "已经成功提交倒休，不能重复提交";
	public static final JSONData LOGIN_FAIL_JSON=new JSONData(-1,LOGINFAIL);
	public static final JSONData DXSUCCESS_JSON=new JSONData(0,DXSUCCESS);
	public static final JSONData INVALID_JSON=new JSONData(-2,INVALID);
	public static final JSONData SUBMITED_JSON=new JSONData(-1,SUBMITED);
}
