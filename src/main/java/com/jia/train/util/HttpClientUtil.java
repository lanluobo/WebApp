package com.jia.train.util;

import com.jia.train.po.U12306;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jiaxl on 2016/10/14.
 */
public class HttpClientUtil {

    private static HttpHost proxy = new HttpHost("localhost", 8888);

    public static HttpPost getHttpPost(String url) {
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        post.setHeader("Origin", "https://kyfw.12306.cn");
        post.setHeader("Accept", "*/*");
        post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/;537.36 (KHTML, like Gecko) Chrome/43.0.2351.3 Safari/537.36");
        return post;
    }

    /**
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public static String getDataByPost(String url, U12306 u12306, Map<String, String> map) throws Exception {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        String result;
        HttpClient client = getProxyHttpClient();
        HttpPost post = getHttpPost(url);
        post.setHeader("Cookie", u12306.getCookie());
        if (map != null) {
            for (String s : map.keySet()) {
                list.add(new BasicNameValuePair(s, map.get(s)));
            }
            post.setEntity(new UrlEncodedFormEntity(list));
        }
        try {
            HttpResponse resp = client.execute(post);
            result = EntityUtils.toString(resp.getEntity());
        } catch (IOException e) {
            throw new Exception("Post请求异常，URL=" + url, e);
        }
        if (result.contains("用户未登录")) {
            throw new Exception("用户未登录");
        }
        return result;

    }

    public static String getDataByPost(String url, U12306 u12306, String param) throws Exception {
        String result;
        HttpClient client = getProxyHttpClient();
        HttpPost post = getHttpPost(url);
        post.setHeader("Cookie", u12306.getCookie());
        post.setEntity(new StringEntity(param));
        try {
            HttpResponse resp = client.execute(post);
            result = EntityUtils.toString(resp.getEntity());
        } catch (IOException e) {
            throw new Exception("Post请求异常，URL=" + url, e);
        }
        if (result.contains("用户未登录")) {
            throw new Exception("用户未登录");
        }
        return result;

    }

    public static DefaultHttpClient getHttpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
//		设置信任所有证书
        X509TrustManager xtm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            ctx.init(null, new TrustManager[]{xtm}, null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

        httpClient.getConnectionManager().getSchemeRegistry()
                .register(new Scheme("https", 443, socketFactory));

        return httpClient;
    }


    public static HttpClient getProxyHttpClient() {
        HttpClient client = null;
        try {
            client = getHttpClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        return client;
    }

    public static String getDataByGet(String url) throws Exception {
        try {
            HttpResponse resp = getHttpClient().execute(new HttpGet(url));
            return EntityUtils.toString(resp.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Get请求异常，URL=" + url, e);
        }

    }

    /**
     *  keytool  -import -alias "my alipay cert" -file www.alipay.com.cert  -keystore my.store
     * @return
     * @throws Exception
     */
    public static HttpClient createSSLClientDefault() throws Exception {
        //获得密匙库
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        //密匙库的密码
        trustStore.load(HttpClientUtil.class.getResourceAsStream("/12306pay.store"), "jia123".toCharArray());
        trustStore.load(HttpClientUtil.class.getResourceAsStream("/alipay.store"), "jia123".toCharArray());
        trustStore.load(HttpClientUtil.class.getResourceAsStream("/12306.store"), "jia123".toCharArray());
        SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore).build();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        return httpclient;
    }




}
