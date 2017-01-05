package com.jia.train.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jia.train.po.*;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jiaxl on 2016/12/27.
 */
public class Utils {
    /**
     * 计算选择验证码下标
     *
     * @param x
     * @param y
     * @return
     */
    public static int calcIndex(int x, int y) {
        if (x <= 71) {
            if (y <= 104) {
                return 0;
            } else {
                return 4;
            }
        } else if (x > 71 && x < 144) {
            if (y <= 104) {
                return 1;
            } else {
                return 5;
            }

        } else if (x >= 144 && x <= 217) {
            if (y <= 104) {
                return 2;
            } else {
                return 6;
            }
        } else if (x > 217 && x <= 280) {
            if (y <= 104) {
                return 3;
            } else {
                return 7;
            }
        }
        return -1;
    }

    /**
     * 校验验证码
     *
     * @param u12306
     * @return
     */
    public static boolean validateCode(U12306 u12306) {
        try {
            System.out.println("校验验证码...");
            HttpClient client = HttpClientUtil.getProxyHttpClient();
            HttpPost post = new HttpPost("https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn");
            post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            post.setHeader("Origin", "https://kyfw.12306.cn");
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2351.3 Safari/537.36");
            post.setHeader("Cookie", u12306.getCookie());
            post.setEntity(new StringEntity("randCode=" + u12306.getCode() + "&rand=sjrand"));
            HttpResponse resp = client.execute(post);
            String result = EntityUtils.toString(resp.getEntity());

            if (result.contains("result\":\"1\",\"msg\":\"TRUE")) {
                System.out.println("验证码正确...");
                return true;
            } else {
                if(result.contains("msg\":\"FALSE\"")){
                    System.out.println("验证码错误...");
                }else{//系统抽筋的情况，需要刷新session
                    System.out.println("系统抽筋:"+result);
                    Utils.setCookie(u12306);
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 登录12306
     *
     * @param u12306
     * @return
     */
    public static boolean login(U12306 u12306) {
        try {
            HttpClient client = HttpClientUtil.getProxyHttpClient();
            HttpPost post = new HttpPost("https://kyfw.12306.cn/otn/login/loginAysnSuggest");
            post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            post.setHeader("Cookie", u12306.getCookie());
            post.setHeader("Origin", "https://kyfw.12306.cn");
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2351.3 Safari/537.36");
            post.setEntity(new StringEntity("loginUserDTO.user_name=" + u12306.getUser() +
                    "&userDTO.password=" + u12306.getPsd() + "&randCode=" + u12306.getCode()));
            HttpResponse resp = client.execute(post);
            String result = EntityUtils.toString(resp.getEntity());
            while (resp.getStatusLine().getStatusCode() != 200) {
                resp = client.execute(post);
                result = EntityUtils.toString(resp.getEntity());
                System.out.println(result);
            }

            if (result.contains("loginCheck\":\"Y")) {
                System.out.println(u12306.getUser() + ":登录成功...");
                return true;
            } else {
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 加载登录页面获取Cookie
     *
     * @param u12306
     * @throws IOException
     */
    public static void setCookie(U12306 u12306) throws IOException {
        HttpClient client = HttpClientUtil.getProxyHttpClient();
        HttpGet get = new HttpGet("https://kyfw.12306.cn/otn/login/init");
        HttpResponse resp = client.execute(get);
        Header[] headers = resp.getHeaders("Set-Cookie");
        EntityUtils.consume(resp.getEntity());
        u12306.setSession(headers[0].getValue().split(";")[0]);
        u12306.setbIGipServerotn(headers[1].getValue().split(";")[0]);
    }

    /**
     * 查询常用联系人
     *
     * @param u12306
     * @return
     * @throws Exception
     */
    public static List<Passenger> getPassengers(U12306 u12306) throws Exception {

        String result = HttpClientUtil.getDataByPost("https://kyfw.12306.cn/otn/passengers/query", u12306, "pageIndex=1&pageSize=30");
        JSONObject json = JSONObject.parseObject(result);
        List<Passenger> list = JSONArray.parseArray(json.getJSONObject("data").getJSONArray("datas").toString(), Passenger.class);
        return list;
    }

    /**
     * 查询未完成订单
     *
     * @param u12306
     * @return
     * @throws Exception
     */
    public static TrainOrder getNoCompleteOrder(U12306 u12306) throws Exception {
        String result = HttpClientUtil.getDataByPost("https://kyfw.12306.cn/otn/queryOrder/queryMyOrderNoComplete", u12306, (Map) null);
        if (!result.contains("orderDBList")) {
            return null;
        }
        JSONObject json = JSONObject.parseObject(result);
        System.out.println(json.toJSONString(json, true));
        System.out.println(json.getJSONObject("data").getJSONArray("orderDBList").toString());
        TrainOrder order = JSONArray.parseObject(json.getJSONObject("data").getJSONArray("orderDBList").getJSONObject(0).toJSONString(), TrainOrder.class);
        return order;
    }

    public static void initData(U12306 u12306) {

        try {
            getPassengers(u12306);
            TrainOrder order = getNoCompleteOrder(u12306);
            if (order != null) {
                payOrder(u12306, order.getSequence_no());
                cancelNoCompleteOrder(u12306, order.getSequence_no());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 支付未完成订单
     *
     * @param u12306
     * @param sequenceNo
     * @throws Exception
     */
    private static void payOrder(U12306 u12306, String sequenceNo) throws Exception {
        String result;
        Map<String, String> map = new HashMap<String, String>();
        map.put("sequence_no", sequenceNo);
        map.put("pay_flag", "pay");
        map.put("_json_att", "");
        result = HttpClientUtil.getDataByPost("https://kyfw.12306.cn/otn/queryOrder/continuePayNoCompleteMyOrder", u12306, map);
        System.out.println("确认支付订单结果：" + result);

        //校验订单paycheckNew
        result = HttpClientUtil.getDataByPost("https://kyfw.12306.cn/otn/payOrder/paycheckNew", u12306, "batch_nos=&coach_nos=&seat_nos=&passenger_id_types=&passenger_id_nos=&passenger_names=&insure_price_all=&insure_types=&if_buy_insure_only=N&hasBoughtIns=&_json_att=");

        JSONObject json = JSONObject.parseObject(result).getJSONObject("data").getJSONObject("payForm");
        System.out.println("校验订单paycheckNew:" + json);

        //生成支付订单
        map.clear();
        map.put("_json_att", "");
        map.put("interfaceName", json.getString("interfaceName"));
        map.put("interfaceVersion", json.getString("interfaceVersion"));
        map.put("tranData", json.getString("tranData"));
        map.put("merSignMsg", json.getString("merSignMsg"));
        map.put("appId", json.getString("appId"));
        map.put("transType", json.getString("transType"));
        map.put("_json_att", "");
        result = HttpClientUtil.getDataByPost(json.getString("epayurl"), u12306, map);
        TrainPay trainPay = JsoupUtil.formToObject(result, TrainPay.class);

        //构建支付宝订单
        map.clear();
        map.put("tranData", trainPay.getTranData());
        map.put("transType", trainPay.getTransType());
        map.put("channelId", trainPay.getChannelId());
        map.put("appId", trainPay.getAppId());
        map.put("merSignMsg", trainPay.getMerSignMsg());
        map.put("merCustomIp", trainPay.getMerCustomIp());
        map.put("orderTimeoutDate", trainPay.getOrderTimeoutDate());
        map.put("bankId", trainPay.getBankId());

        result = HttpClientUtil.getDataByPost("https://epay.12306.cn/pay/webBusiness", u12306, map);
        System.out.println("跳转支付宝页面：" + result);

        AliPay aliPay = JsoupUtil.formToObject(result, AliPay.class);
        //调用系统默认浏览器支付车票
        HttpPost post = HttpClientUtil.getHttpPost("https://mapi.alipay.com/gateway.do");
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("ord_time_ext", aliPay.getOrd_time_ext()));
        list.add(new BasicNameValuePair("ord_name", aliPay.getOrd_name()));
        list.add(new BasicNameValuePair("ord_desc", aliPay.getOrd_desc()));
        list.add(new BasicNameValuePair("notify_url", aliPay.getNotify_url()));
        list.add(new BasicNameValuePair("sign", aliPay.getSign()));
        list.add(new BasicNameValuePair("ord_amt", aliPay.getOrd_amt()));
        list.add(new BasicNameValuePair("service", aliPay.getService()));
        list.add(new BasicNameValuePair("partner", aliPay.getPartner()));
        list.add(new BasicNameValuePair("dispatch_cluster_target", aliPay.getDispatch_cluster_target()));
        list.add(new BasicNameValuePair("ord_cur", aliPay.getOrd_cur()));
        list.add(new BasicNameValuePair("ord_pmt_timeout", aliPay.getOrd_pmt_timeout()));
        list.add(new BasicNameValuePair("req_access_type", aliPay.getReq_access_type()));
        list.add(new BasicNameValuePair("_input_charset", aliPay.get_input_charset()));
        list.add(new BasicNameValuePair("ord_id_ext", aliPay.getOrd_id_ext()));
        list.add(new BasicNameValuePair("sign_type", aliPay.getSign_type()));
        list.add(new BasicNameValuePair("return_url", aliPay.getReturn_url()));
        list.add(new BasicNameValuePair("req_client_ip_ext", aliPay.getReq_client_ip_ext()));
        post.setEntity(new UrlEncodedFormEntity(list));
        post.setHeader("Cookie", u12306.getCookie());
        HttpResponse resp = HttpClientUtil.getHttpClient().execute(post);
        result = EntityUtils.toString(resp.getEntity());
        System.out.println("支付" + result);
        String payUrl = resp.getFirstHeader("Location").getValue();
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + payUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询车次信息
     *
     * @param info
     * @return
     * @throws Exception
     */
    public static List<TrainInfo> queryTicket(QueryInfo info) throws Exception {


        String url = "https://kyfw.12306.cn/otn/leftTicket/queryA?leftTicketDTO.train_date=" + info.getDate()
                + "&leftTicketDTO.from_station=" + info.getStartId() + "&leftTicketDTO.to_station=" + info.getEndId() +
                "&purpose_codes=" + info.getType();
        String result = HttpClientUtil.getDataByGet(url);
        if (result.contains("选择的查询日期不在预售日期范围内")) {
            throw new Exception("选择的查询日期不在预售日期范围内");
        }

        JSONObject json = JSONObject.parseObject(result);
        JSONArray array = json.getJSONArray("data");
        if (array == null || array.size() == 0) {
            throw new Exception("没有车次信息");
        }
        System.out.println(JSONObject.toJSONString(array, true));
        return JSONArray.parseArray(array.toJSONString(), TrainInfo.class);

    }

    /**
     * 取消未完成的订单
     *https://kyfw.12306.cn/otn/queryOrder/cancelNoCompleteMyOrder
     * @param u12306
     * @param sequence_no
     * @return
     * @throws Exception
     */
    public static boolean cancelNoCompleteOrder(U12306 u12306, String sequence_no) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sequence_no", sequence_no);
        map.put("cancel_flag", "cancel_order");
        map.put("_json_att", "");
        String result = HttpClientUtil.getDataByPost("https://kyfw.12306.cn/otn/queryOrder/cancelNoCompleteMyOrder", u12306, map);
        System.out.println(result);
        return true;
    }
    /**
     *
     */

    /**
     *查询未出行订单
     * @param u12306
     * @return
     */
    public static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    @SuppressWarnings("deprecation")
    public static List<TrainOrder> getMyOrder(U12306 u12306) throws Exception {
        Date startTime=new Date();
        startTime.setMonth(startTime.getMonth()-2);
        List<TrainOrder> list = new ArrayList<TrainOrder>();
        Map<String,String>map=new HashMap<String, String>();
        map.put("queryType","1");
        map.put("queryStartDate",format.format(startTime));
        map.put("queryEndDate",format.format(new Date()));
        map.put("come_from_flag","my_order");
        map.put("pageSize","20");
        map.put("pageIndex","0");
        map.put("query_where","G");
        map.put("sequeue_train_name","");
        HttpClientUtil.getDataByPost("https://kyfw.12306.cn/otn/queryOrder/queryMyOrder",u12306,map);
        return list;
    }

}
