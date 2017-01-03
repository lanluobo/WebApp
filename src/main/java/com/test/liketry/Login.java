package com.test.liketry;

import com.jia.kaoqin.util.DBUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <dl>
 * <dt>Login</dt>
 * <dd>Description:TODO</dd>
 * <dd>Copyright: Copyright (C) 2016</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2016年11月27日</dd>
 * </dl>
 *
 * @author 贾学雷
 */
public class Login {
    static Logger logger = Logger.getLogger(Login.class);
    public static final Header header = new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2351.3 Safari/537.36");

    public static void main(String[] args) throws Exception {

        final List<LikeTry> list = DBUtil.getLiketryPhones(2550);
//        for(LikeTry l:list){
//            setAddress(l.getCookie());
//        }

        System.out.println(list.size());
        new Thread(){
            @Override
            public void run() {
                while (true){
                    for  (LikeTry likeTry : list) {
                        try {
                            doTry(likeTry);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
        while (true){
            for(int i=list.size()-1;i>=0;i--){
                doTry(list.get(i));
            }
        }

    }

    public static void doTry(LikeTry likeTry) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://www.liketry.com/daytry-activity-detail/parti-activity");
        post.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        post.setHeader("X-Requested-With", "XMLHttpRequest");
        post.setHeader("Cookie", likeTry.getCookie());
        post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        post.setHeader(header);
        post.setEntity(new StringEntity("activity_id=116"));
        HttpResponse resp = client.execute(post);
        String result = EntityUtils.toString(resp.getEntity());
        if (result.contains("flag\":true")) {
            logger.info("参与成功!"+likeTry.getPhone());
        }else{
            System.out.println(result+likeTry.getPhone());
        }
    }

    private static void addCart(HttpClient client) throws IOException {
        HttpGet get = new HttpGet("http://www.liketry.com/goodsdetail/index?product_id=50000341");
        HttpResponse resp = client.execute(get);
        String result = EntityUtils.toString(resp.getEntity());


    }

    private static void setAddress(String cookie) {
        HttpClient client=new DefaultHttpClient();
        HttpPost post = new HttpPost("http://www.liketry.com/member/user_pay_address.php");
        post.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        post.setHeader("X-Requested-With", "XMLHttpRequest");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        post.setHeader("Cookie",cookie);
        post.setHeader(header);
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
//        list.add(new BasicNameValuePair("consignee", "陈艳杰"));
//        list.add(new BasicNameValuePair("s1_uid", "15"));
//        list.add(new BasicNameValuePair("s2_uid", "216"));
//        list.add(new BasicNameValuePair("s3_uid", "1811"));
//        list.add(new BasicNameValuePair("address", "实验中学高中部"));
//        list.add(new BasicNameValuePair("ceil", "18743473473"));
//        list.add(new BasicNameValuePair("tel1", ""));
//        list.add(new BasicNameValuePair("tel2", ""));
//        list.add(new BasicNameValuePair("zipcode", ""));
//        list.add(new BasicNameValuePair("is_default", "0"));
        list.add(new BasicNameValuePair("receiver", "王立山"));
        list.add(new BasicNameValuePair("op", "1"));
        list.add(new BasicNameValuePair("aid", ""));
        list.add(new BasicNameValuePair("s1", "北京"));
        list.add(new BasicNameValuePair("s2", "北京"));
        list.add(new BasicNameValuePair("s3", "海淀区"));
        list.add(new BasicNameValuePair("province", "2"));
        list.add(new BasicNameValuePair("city", "52"));
        list.add(new BasicNameValuePair("area", "502"));
        list.add(new BasicNameValuePair("address", "阜成路73号裕惠大厦A7层"));
        list.add(new BasicNameValuePair("ceil", "13296737704"));
        list.add(new BasicNameValuePair("tel_qu", ""));
        list.add(new BasicNameValuePair("tel", ""));
        list.add(new BasicNameValuePair("tel_fenji", ""));
        try {
            post.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
            HttpResponse resp = client.execute(post);
            String result = EntityUtils.toString(resp.getEntity());
            if (result.contains("13296747704")) {
                logger.info("增加地址成功");
                return;
            }
            logger.info(result);
        } catch (Exception e) {
            logger.error("增加地址异常", e);
        }
    }

    /*

     */
    public static boolean login(HttpClient client, String phone, String captcha) {
        logger.info(phone + ":" + captcha + " 开始登录...");
        HttpPost post = new HttpPost("http://passport.liketry.com/login/check-login");
        post.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        post.setHeader("X-Requested-With", "XMLHttpRequest");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        post.setHeader(header);
        try {
            post.setEntity(new StringEntity("user=" + phone + "&password=jia123&captcha=" + captcha + "&auto_login=true"));
            HttpResponse resp = client.execute(post);
            String result = EntityUtils.toString(resp.getEntity());
            if (result.contains("flag\":true")) {
                logger.info(phone + ",登录成功...");
                return true;
            }
            logger.warn(result);
        } catch (Exception e) {
            logger.error("登录异常", e);
        }
        return false;
    }
}
