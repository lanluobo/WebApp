package com.jia.train.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiaxl on 2016/12/31.
 */
public class JsoupUtil {


    public static <T> T formToObject(String html,Class<T>typeClass) throws  Exception {
        T t=typeClass.newInstance();
        Element form=Jsoup.parse(html).getElementsByTag("form").first();
        for(Element e:form.getElementsByTag("input")){
            Field field=typeClass.getDeclaredField(e.attr("name"));
            field.setAccessible(true);
            field.set(t,e.attr("value"));
        }
        System.out.println(t);
        return t;
    }

    public static void main(String[] args) throws  Exception {

        Date startTime=new Date();
        startTime.setMonth(startTime.getMonth()-2);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(startTime));
    }
}
