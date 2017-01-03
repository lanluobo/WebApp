package com.jia.ocr.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <dl>
 * <dt>CommonUtil</dt>
 * <dd>Description:TODO</dd>
 * <dd>Copyright: Copyright (C) 2016</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2016年08月07日</dd>
 * </dl>
 *
 * @author 贾学雷
 */
public class MD5Util {

    public static void main(String[] args) {
        String s = getMD5("method=ddsy.user.send.smscode,tel=13296747704,msgType=3,v=1.0,t=2016-11-06 12:18:10,");
        System.out.print(s);
//        E073A2E617F1BC993B548B7671CB8AB5
    }

    public static String getMD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        byte[] btInput = s.getBytes();
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst = null;
        try {
            mdInst = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 使用指定的字节更新摘要
        mdInst.update(btInput);
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
    public static String getMD5(long s) {
        return getMD5(""+s);
    }

}
