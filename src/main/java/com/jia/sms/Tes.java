package com.jia.sms;

import com.jia.common.util.HttpClientUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Created by jiaxl on 2016/11/5.
 */
public class Tes {
    public static void main(String[] args) throws Exception {
//        System.out.println(HttpClientUtil.getDataByPost("http://103.25.23.99/BaikuUserCenterV2/passportService","service=registerAccount&params={\"password\":\"e10adc3949ba59abbe56e057f20f883e\",\"account\":\"18712855779\",\"appType\":\"mobile\",\"nubeNumber\":\"90533375\",\"deviceType\":\"IOS_KESHI\",\"imei\":\"mobileNumber\"}"));
        Process p=Runtime.getRuntime().exec("ls");
        StringBuilder sbCmd = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            sbCmd.append(line + "\n");
        }
        System.out.println(sbCmd.toString());

    }
}
