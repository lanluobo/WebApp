package com.jia.kaoqin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <dl>
 * <dt>Test</dt>
 * <dd>Description:TODO</dd>
 * <dd>Copyright: Copyright (C) 2016</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2016年09月09日</dd>
 * </dl>
 *
 * @author 贾学雷
 */
public class Test {

    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat format=new SimpleDateFormat("yyyyMM");
//        Date date=new Date();
//        date.setMonth(date.getMonth()-1);
//        String month=format.format(date);
//        System.out.println(month);

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd 18:00:00");
        String today=format.format(new Date());
        System.out.println(today);
        if((new Date()).after(format.parse(today))){
            System.out.println("123");
        }
    }
}
