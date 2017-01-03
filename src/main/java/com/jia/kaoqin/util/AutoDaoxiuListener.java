package com.jia.kaoqin.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 贾学雷 on 2016/7/5.
 */
public class AutoDaoxiuListener implements ServletContextListener {
    public static void main(String[] args) {
        Date t=new Date();
        t.setHours(8);
        t.setMinutes(59);
        t.setSeconds(10);
        System.out.println(t.toLocaleString());
        if(t.before(new Date())){
            t=new Date(t.getTime()+24 * 60 * 60 * 1000);
            System.out.println(t.toLocaleString());
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("test");
        long daySpan = 24 * 60 * 60 * 1000;
        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd 13:47:22");
        SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start= null;
        try {
            String temp=format1.format(new Date());
            start = format2.parse(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 每天08:59:10 运行
        if(start.before(new Date())){
            start=new Date(start.getTime()+daySpan);
        }
        System.out.println(start.toLocaleString());
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                // 要执行的代码
                System.err.println(KaoqinUtil.getCurrTime()+"xxxxxxxxx");
            }
        };

        new Timer().scheduleAtFixedRate(task,start,daySpan);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
