package com.jia.common.po;

import com.alibaba.fastjson.JSONObject;

/**
 * <dl>
 * <dt>JSONData</dt>
 * <dd>Description:接口返回JSON数据PO</dd>
 * <dd>Copyright: Copyright (C) 2016</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2016年08月06日</dd>
 * </dl>
 *
 * @author 贾学雷
 */
public class JSONData {

    private JSONObject json=new JSONObject();

    public JSONData(){
        json.put("message","未知异常");
        json.put("code",-1);
        json.put("data",null);
    }
    public JSONData(int code,String message){
        json.put("code",code);
        json.put("message",message);
    }
    public int getCode() {
        return this.json.getIntValue("code");
    }

    /*
    * code=0操作成功 code=-1操作失败
    * */
    public void setCode(int code) {
        this.json.put("code",code);
    }

    public Object getData() {
        return json.get("data");
    }

    /*
    * 设置响应的数据可以是任何对象，反射自动转换对象为JSON、JSONArray格式字符串
    * */
    public void setData(Object data) {
        this.json.put("data",data);
    }

    public String getMessage() {
        return json.getString("message");
    }
    /*
    * 设置提示信息尤其在code=-1时，必须注明原因
    * */
    public void setMessage(String message) {
        this.json.put("message",message);
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(json,true);
    }
}
