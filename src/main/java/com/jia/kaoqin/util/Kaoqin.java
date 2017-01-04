package com.jia.kaoqin.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jia.common.util.HttpClientUtil;
import com.jia.kaoqin.mapper.KaoqinMapper;
import com.jia.kaoqin.po.KaoqinEntity;
import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class Kaoqin {

    @Autowired
    private  KaoqinMapper mapper;

    private  Logger logger = Logger.getLogger(Kaoqin.class);
    private  SimpleDateFormat format = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private  SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

    // 测试方法
    public static void main(String[] args) throws Exception {
//        List<KaoqinEntity> data = new Kaoqin().getTime(null, "贾学雷");
//        System.out.println(data);
    }

//    @SuppressWarnings("deprecation")
//    public  List<KaoqinEntity> getTime(HttpSession session, String name) throws Exception {
//        HttpClient client = (HttpClient) session.getAttribute(name);
//        List<KaoqinEntity> data = new ArrayList<KaoqinEntity>();
//        try {
//            if (client == null) {
//                client = HttpClientUtil.getHttpClient();
//                if (!login(name, client)) {
//                    return data;
//                }
//                session.setAttribute(name,client);
//            }
//            List<Date> list = getData(client);
//            List<KaoqinEntity> entities = calculate(list);
//            for (KaoqinEntity d : entities) {
//
//                int shours = d.startTime.getHours();
//                int smin = d.startTime.getMinutes();
//                int sday = d.startTime.getDay();
//                int ehours = d.endTime.getHours();
//
//                if (sday == 6 || sday == 0) {
//                    d.status = "加班";
//                    data.add(d);
//                    continue;
//                }
//                boolean flag = true;
//                if (shours > 9 || shours >= 9 && smin > 10) {
//                    d.status += "迟到";
//                    flag = false;
//                }
//                if (ehours >= 12 && ehours <= 17) {
//                    if (flag) {
//                        d.status += "早退";
//                    } else {
//                        d.status += "、早退";
//                    }
//
//                    flag = false;
//                }
//                if (flag) {
//                    d.status = "正常";
//                }
//                data.add(d);
//            }
//        } catch (Exception e) {
//            logger.error(e);
//        }
//        return data;
//    }

    @SuppressWarnings("deprecation")
    public List<KaoqinEntity> calculate(List<Date> list) {
        List<List<Date>> data = new ArrayList<List<Date>>();
        // 某一天的打卡集合
        List<Date> onesList = new ArrayList<Date>();
        for (int i = 0; i < list.size(); i++) {
            if (i == 0 || onesList.size() == 0) {
                onesList.add(list.get(i));
                continue;
            }
            Date temp = list.get(i);
            Date d = onesList.get(onesList.size() - 1);

            if (KaoqinUtil.isEqualDate(temp, d)) {
                onesList.add(temp);
            } else {
                data.add(onesList);
                onesList = new ArrayList<Date>();
                i--;
            }
        }
        data.add(onesList);
        return sort(data);
    }

    private  List<KaoqinEntity> sort(List<List<Date>> data) {
        List<KaoqinEntity> l = new ArrayList<KaoqinEntity>();
        for (List<Date> list : data) {
            l.add(new KaoqinEntity(list.get(0), list.get(list.size() - 1)));
        }
        return l;
    }

    public List<Date> getData(HttpClient client) throws Exception {

        List<Date> list = new ArrayList<Date>();
        String uid = getUid(client);
        if (uid == null) {
            return null;
        }

        try {
            String result = HttpClientUtil.getDataByPost(client, "http://kq.channelsoft.com:49527/iclock/staff/transaction/?UserID__id__exact="
                    + uid + "&fromTime=2016-10-28&toTime=2018-01-01", "");
            Pattern p = Pattern
                    .compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
            Matcher m = p.matcher(result);
            while (m.find()) {
                String temp = result.substring(m.start(), m.end());
                list.add(format.parse(temp));
            }
            return list;
        } catch (Exception e) {
            throw new Exception("获取考勤数据异常", e);
        }
    }

    private  String getUid(HttpClient client) {

        try {
            String result = HttpClientUtil.getDataByGet(client, "http://kq.channelsoft.com:49527/iclock/staff/", null);

            Pattern p = Pattern.compile("uid=\"\\d+");
            Matcher m = p.matcher(result);
            if (m.find()) {
                Integer.parseInt(result.substring(m.end() - 4, m.end()));
                return result.substring(m.end() - 4, m.end());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings("deprecation")
    public  boolean login(String name, HttpClient client) throws Exception {
        int id = 0;
        if (name.matches("\\d{5}")) {
            id = Integer.parseInt(name);
        } else if (name.matches("\\d{4}")) {
            id = Integer.parseInt("1" + name);
        } else {
            Integer tem=mapper.selectEmployeeId(name);
            if(tem!=null){
                id = tem;
            }
        }

        String result = null;
        try {
            result = HttpClientUtil.getDataByPost(client, "http://kq.channelsoft.com:49527/iclock/accounts/login/",
                    "username=" + id + "&password=" + id + "&this_is_the_login_form=1&post_data=");

            if (result.contains("result=2")) {
                return true;
            }
        } catch (Exception e) {
            throw new Exception("模拟登录异常", e);
        } finally {
            logger.debug("查询：" + name
                    + "; id=" + id + "; " + result);
        }
        return false;
    }

    public static String InputStreamToString(InputStream in) {

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int length;
        try {
            length = in.read(data, 0, 1024);
            while (length > 0) {
                b.write(data, 0, length);
                length = in.read(data, 0, 1024);
            }
            return new String(b.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  List<KaoqinEntity> get10Time(String name) throws Exception {
        // TODO Auto-generated method stub
        HttpClient client = HttpClientUtil.getHttpClient();
        if (login(name, client)) {
            List<Date> list = get10Data(client);
            List<KaoqinEntity> entities = calculate(list);

            return entities;
        }
        return null;
    }

    /**
     * 获取7天的打卡记录
     * @param client
     * @return
     */
    private  List<Date> get10Data(HttpClient client) {

        List<Date> list = new ArrayList<Date>();
        String uid = getUid(client);
        if (uid == null) {
            return null;
        }

        try {
            String result = HttpClientUtil.getDataByPost(client, "http://kq.channelsoft.com:49527/iclock/staff/transaction/?UserID__id__exact="
                    + uid
                    + "&fromTime="
                    + format2.format(new Date(new Date().getTime() - 7 * 24
                    * 60 * 60 * 1000)) + "&toTime="
                    + format2.format(new Date()), "");

            Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
            Matcher m = p.matcher(result);
            while (m.find()) {
                String temp = result.substring(m.start(), m.end());
                list.add(format.parse(temp));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Date> get1Data(HttpClient client) throws Exception {

        List<Date> list = new ArrayList<Date>();
        String uid = getUid(client);
        if (uid == null) {
            return null;
        }
            String result = HttpClientUtil.getDataByPost(client, "http://kq.channelsoft.com:49527/iclock/staff/transaction/?UserID__id__exact="
                    + uid
                    + "&fromTime="
                    + format2.format(new Date()) + "&toTime="
                    + format2.format(new Date()), "");

            Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
            Matcher m = p.matcher(result);
            while (m.find()) {
                String temp = result.substring(m.start(), m.end());
                list.add(format.parse(temp));
            }
            return list;
    }
}
