package com.jia.train.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jia.train.listener.SessionListener;
import com.jia.train.po.U12306;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaxl on 2017/1/4.
 * 检测登录状态，通知观察者登录失效
 */
public class SessionCheck {

    private static volatile boolean isStart;
    private static volatile U12306 u12306;
    /**
     * 启动线程检测登录状态
     */
    static {
        new Thread(){
            @Override
            public void run() {
                doCheck();
            }
        }.start();

    }

    private static List<SessionListener> listeners = new ArrayList<SessionListener>();


    public static synchronized void addSessionListener(SessionListener listener) {
        listeners.add(listener);
    }

    public static synchronized void removeSessionListener(SessionListener listener) {
        listeners.remove(listener);
    }
    /**
     * 启动检测登录状态
     */
    public static void startCheck(U12306 u12306) {
        SessionCheck.u12306=u12306;
        isStart = true;

    }

    /**
     * 停止检测登录状态
     */
    public static void stop() {
        isStart = false;
    }

    private static void doCheck() {
        HttpClient client = HttpClientUtil.getProxyHttpClient();
        HttpPost post = HttpClientUtil.getHttpPost("https://kyfw.12306.cn/otn/login/checkUser");
        HttpResponse resp;
        String result;
        while (true) {
            try {
                Thread.sleep(3000);
                if (isStart) {
                    post.setHeader("Cookie", u12306.getCookie());
                    resp = client.execute(post);
                    result = EntityUtils.toString(resp.getEntity());
                    System.out.println(result);
                    if(!result.contains("flag\":true")){
                        JSONArray array=JSONObject.parseObject(result).getJSONArray("messages");
                        if(array!=null){
                            notifyListener(array.getString(0));
                        }else {
                            notifyListener("登录失效，未知异常");
                        }

                        isStart=false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized static void notifyListener(String msg) {
        System.out.println("登录失效通知观察者,Msg="+msg);
        for(SessionListener l:listeners){
            l.dealSessionExpired(msg);
        }
    }

}
