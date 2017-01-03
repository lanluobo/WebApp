package com.jia.common.util;

import com.jia.kaoqin.util.DaoxiuUtil;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * Created by jiaxl on 2016/10/14.
 */
public class HttpClientUtil {

    private static Logger logger = Logger.getLogger(HttpClientUtil.class.getClass());

    private static String character = "utf-8";

    private static HttpHost proxy = new HttpHost("localhost",8888);

    public static String getDataByGet(HttpClient client,String url, Map<String, String> map) throws Exception {
        if(client==null){
            client=getHttpClient();
        }
        String param = "";
        if (map != null) {
            for (String key : map.keySet()) {
                param += key + "=" + map.get(key) + "&";
            }
            url = url + "?" + param;
        }
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse resp = client.execute(get);
            return EntityUtils.toString(resp.getEntity(), character);
        } catch (IOException e) {
            logger.error(e);
            throw new Exception("httpclient调用异常", e);
        }
    }

    public static String getDataByPost(HttpClient client, String url, Map<String, String> map) throws Exception {
        if(client==null){
            client=getHttpClient();
        }
        HttpPost post = getHttpPost(url);
        String param = "";
        if (map != null) {
            for (String key : map.keySet()) {
                param += key + "=" + map.get(key) + "&";
            }
            post.setEntity(new StringEntity(param));
        }
        try {
            return EntityUtils.toString(client.execute(post).getEntity(), character);
        } catch (IOException e) {
            throw new Exception("httpclient调用异常", e);
        }
    }

    public static String getDataByPost(HttpClient client, String url, String stringEntity) throws Exception {
        if(client==null){
            client=getHttpClient();
        }
        HttpPost post = getHttpPost(url);
        post.setEntity(new StringEntity(stringEntity));
        try {
            return EntityUtils.toString(client.execute(post).getEntity(), character);
        } catch (IOException e) {
            throw new Exception("httpclient调用异常", e);
        }
    }

    public static HttpPost getHttpPost(String url) {

        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");
        post.addHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2351.3 Safari/537.36");
        return post;
    }

    /**
     * SMS使用
     * @param url
     * @param body
     * @return
     * @throws Exception
     */
    public static String getDataByPost(String url, String body) throws Exception {

        String result;
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
        post.setEntity(new StringEntity(body));
        try {
            HttpResponse resp = client.execute(post);
            result = EntityUtils.toString(resp.getEntity(), "utf-8");
            return result;
        } catch (IOException e) {
            throw new Exception("Post请求异常，URL=" + url, e);
        }
    }

    /**
     * SMS使用
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String getDataByGet(String url, String param) throws Exception {

        String result;
        HttpClient client = getHttpClient();
        HttpGet get;
        if(StringUtils.hasText(param)){
            get = new HttpGet(url+"?"+param);
        }else{
            get = new HttpGet(url);
        }
        try {
            HttpResponse resp = client.execute(get);
            result = EntityUtils.toString(resp.getEntity(), "utf-8");
            return result;
        } catch (IOException e) {
            throw new Exception("Get请求异常，URL=" + url, e);
        }
    }
    public static DefaultHttpClient getHttpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
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

    public static void setCharacter(String character) {
        HttpClientUtil.character = character;
    }

    public static HttpClient getProxyHttpClient(){
        HttpClient client=getHttpClient();
        client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
        return client;
    }
}
