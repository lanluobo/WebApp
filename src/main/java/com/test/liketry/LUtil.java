package com.test.liketry;

import com.jia.kaoqin.util.DBUtil;
import com.jia.ocr.util.Util;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiaxl on 2016/12/2.
 */
public class LUtil {

    public static final Header header = new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2351.3 Safari/537.36");
    private static ExecutorService executorService = Executors.newFixedThreadPool(30);

    public static void main(String[] args) {
//        setCookie(DBUtil.getLiketryPhones(0));
        updateShidou(DBUtil.getLiketryPhones(2550));

    }

    public static void updateShidou(List<LikeTry> list){
        for (LikeTry l:list){
            l.setShidou(getShidou(l.getCookie()));
            System.out.println(l.getPhone() + "试豆:" + l.getShidou());
            DBUtil.updateShidou(l);
        }
    }
    public static Integer getShidou(String cookie){

        HttpClient client=new DefaultHttpClient();
        HttpGet get=new HttpGet("http://www.liketry.com/user-center/index");
        get.setHeader("Cookie",cookie);
        try {
            HttpResponse resp=client.execute(get);
            String result=EntityUtils.toString(resp.getEntity());
            Pattern p=Pattern.compile("user-try-coin'>\\d*");
            Matcher m=p.matcher(result);
            if(m.find()){
                return Integer.valueOf(result.substring(m.start()+15,m.end()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void setCookie(List<LikeTry> list) {

            for(LikeTry likeTry:list){
                executorService.execute(new LoginThread(likeTry));
            }
            executorService.shutdown();
    }


    public static String login(LikeTry likeTry) {
        HttpClient client;
        HttpResponse resp;
        HttpGet get;
        String cookie=null;
        int i=0;
        while (i++<3) {
            try {
                client = new DefaultHttpClient();
                get = new HttpGet("http://passport.liketry.com/site/captcha?type=mix&length=4");
                resp = client.execute(get);
                cookie = resp.getFirstHeader("Set-Cookie").getValue().split(";")[0] + ";";
                System.out.println(cookie);
                File file = new File(UUID.randomUUID()+"yzm.jpg");
                FileOutputStream f = new FileOutputStream(file);
                resp.getEntity().writeTo(f);
                f.close();
                String code = Util.getCode(file);
                file.delete();
                HttpPost post = new HttpPost("http://passport.liketry.com/login/check-login");
                post.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
                post.setHeader("X-Requested-With", "XMLHttpRequest");
                post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                post.setHeader(header);
                post.setEntity(new StringEntity("user=" + likeTry.getPhone() + "&password=" + likeTry.getPassword() + "&captcha=" + code + "&auto_login=true"));
                resp = client.execute(post);
                String result = EntityUtils.toString(resp.getEntity());
                if (result.contains("flag\":true")) {
                    System.out.println(likeTry.getPhone()+",登录成功...");
                    cookie = cookie + resp.getFirstHeader("Set-Cookie").getValue().split(";")[0];
                    break;
                }else {
                    System.out.println(likeTry.getPhone()+",登录失败..."+result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cookie;
    }

    public static File getCaptcha(HttpClient client) throws Exception {

        HttpGet get = new HttpGet("http://passport.liketry.com/site/captcha?type=mix&length=4");
        HttpResponse resp = client.execute(get);
        resp.getFirstHeader("Set-Cookie");
        FileOutputStream f = new FileOutputStream("yzm.jpg");
        resp.getEntity().writeTo(f);
        f.close();
        return new File("yzm.jpg");
    }
}

class LoginThread implements Runnable {
    LikeTry likeTry;

    public LoginThread(LikeTry likeTry) {
        this.likeTry = likeTry;
    }

    @Override
    public void run() {
        String cookie=LUtil.login(likeTry);
        likeTry.setCookie(cookie);
        DBUtil.updateCookie(likeTry);
    }
}