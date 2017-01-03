package com.test.liketry;

import com.jia.common.util.HttpClientUtil;
import com.jia.kaoqin.util.DBUtil;
import com.jia.ocr.util.Util;
import com.test.jiuma.JiuMa;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Scanner;

/**
 * <dl>
 * <dt>Register</dt>
 * <dd>Description:TODO</dd>
 * <dd>Copyright: Copyright (C) 2016</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2016年11月27日</dd>
 * </dl>
 *
 * @author 贾学雷
 */
public class Register {

    public static String session;
    static String phone;
    static String captcha;
    static JiuMa jiuMa;
    public static final Header header = new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2351.3 Safari/537.36");

    public static void main(String[] args) {
        jiuMa = new JiuMa("ts5776", "jiaxuelei");
        jiuMa.setPid("19191");
        if (!jiuMa.login()) {
            return;
        }
        int i=0;
        while (i++<100){
            start();
        }

    }

    public static void start() {
        HttpClient client = HttpClientUtil.getHttpClient();
        HttpGet get = new HttpGet("http://passport.liketry.com/reg");
        get.setHeader(header);
        try {
            HttpResponse resp = client.execute(get);
            EntityUtils.consume(resp.getEntity());
            session = resp.getFirstHeader("Set-Cookie").getValue().split(" ")[0];
            Scanner scanner = new Scanner(System.in);
            captcha = Util.getCode(LUtil.getCaptcha(client));
            phone = jiuMa.getPhone();
            System.out.println("手机号："+phone+"图片验证码："+captcha );
            HttpPost post = new HttpPost("http://passport.liketry.com/site/get-code-reg");
            post.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            post.setHeader("X-Requested-With", "XMLHttpRequest");
            post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            post.setHeader(header);
            post.setEntity(new StringEntity("cell=" + phone + "&captcha=" + captcha));
            resp = client.execute(post);
            String result = EntityUtils.toString(resp.getEntity());
            System.out.println(result);
            if (!result.contains("flag\":true")) {
                jiuMa.releasePhones();
                return;
            }
            post = new HttpPost("http://passport.liketry.com/reg/register");
            post.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            post.setHeader("X-Requested-With", "XMLHttpRequest");
            post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            post.setHeader(header);
            post.setEntity(new StringEntity("cell=" + phone + "&captcha=" + captcha + "&smscode=" + jiuMa.getCode(phone) + "&password=jia123&password1=jia123&accept=true"));
            resp = client.execute(post);
            result = EntityUtils.toString(resp.getEntity());

            if (result.contains("\"flag\":true")) {
                System.out.println("注册成功....");
                LikeTry likeTry=new LikeTry();
                likeTry.setPhone(phone);
                likeTry.setPassword("jia123");
                DBUtil.insertLiketry(likeTry);
                return;
            }
//            for (int i = 100000; i < 999999; i = i + 30000) {
//                new MyThread(i, i + 30000).start();
//            }

        } catch (Exception e) {
            e.printStackTrace();
            jiuMa.releasePhones();
        }


    }

    static class MyThread extends Thread {

        int start, end;
        HttpClient client = HttpClientUtil.getHttpClient();

        public MyThread(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread() + "start....");
            HttpPost post = new HttpPost("http://passport.liketry.com/reg/register");
            post.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            post.setHeader("X-Requested-With", "XMLHttpRequest");
            post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            post.setHeader("Cookie", session);
            post.setHeader(header);
            HttpResponse resp;
            String result;
            for (; start <= end; start++) {

                try {
                    post.setEntity(new StringEntity("cell=" + phone + "&captcha=" + captcha + "&smscode=" + start + "&password=jia123&password1=jia123&accept=true"));
                    resp = client.execute(post);
                    result = EntityUtils.toString(resp.getEntity());
                    System.out.println(start + result);
                    if (result.contains("\"flag\":true")) {
                        System.exit(0);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
