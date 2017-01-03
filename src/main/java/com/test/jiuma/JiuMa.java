package com.test.jiuma;

import com.jia.common.util.HttpClientUtil;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiaxl on 2016/11/30.
 */
public class JiuMa {
    private Logger logger = Logger.getLogger(this.getClass());
    private String user;
    private String psd;
    private String token;
    private String pid;

    public JiuMa(String user, String psd) {
        this.user = user;
        this.psd = psd;
    }

    public boolean login() {
        try {
            String result = HttpClientUtil.getDataByGet("http://api.9mli.com/http.aspx?action=loginIn&uid=" + user + "&pwd=" + psd, null);

            if (result.contains(user)) {
                logger.info(user + "，登录成功...");
                token = result.split("\\|")[1];
                return true;
            }
            logger.warn(result);
            if (result.contains("login_error")) {
                logger.error("用户名密码错误");
            }

        } catch (Exception e) {
            logger.error("登录失败", e);
        }
        return false;
    }

    public String getBalance() {
        try {
            String result = HttpClientUtil.getDataByGet("http://api.9mli.com/http.aspx?action=getUserInfos&uid=" + user + "&token=" + token, null);
            if (result.contains(user)) {
                return result.split(";")[2];
            }
        } catch (Exception e) {
            logger.error("获取余额异常", e);
        }
        return null;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public static void main(String[] args) {
        JiuMa jiuMa = new JiuMa("ts5776", "jiaxuelei");
        jiuMa.login();
        System.out.println(jiuMa.getBalance());
        jiuMa.setPid("19191");
        System.out.println(jiuMa.getPhone());
        jiuMa.releasePhones();
    }

    public String getPhone() {
        try {
            String result=HttpClientUtil.getDataByGet("http://api.9mli.com/http.aspx?action=getMobilenum&pid="+pid+"&uid="+user+"&size=1&token="+token,null);

            if(result.contains(token)){
                return result.split("\\|")[0];
            }
            logger.warn(result);
            if(result.contains("max_count_disable")){

            }
        } catch (Exception e) {
            logger.error("获取号码异常",e);
        }
        return null;
    }

    public String getCode(String phone){
        try {
            int i=0;
            while(i++<15){
                Thread.sleep(2000);
                System.out.println("第"+i+"次获取验证码...");
                String result=HttpClientUtil.getDataByGet("http://api.9mli.com/http.aspx?action=getVcodeAndHoldMobilenum&uid="+user+"&token="+token+"&pid="+pid+"&mobile="+phone,null);
                if(result.contains(phone)){
                    Pattern p=Pattern.compile("\\d{6}，");
                    Matcher m=p.matcher(result);
                    if(m.find()){
                        String code=result.substring(m.start(),m.end()-1);
                        logger.info("验证码获取成功："+code);
                        return code;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void releasePhones(){
        new Thread(){
            @Override
            public void run() {
                try {
                    String result=HttpClientUtil.getDataByGet("http://api.9mli.com/http.aspx?action=ReleaseMobile&uid="+user+"&token="+token,null);
                    if(!result.contains("OK")){
                        logger.warn("释放号码失败："+result);
                    }
                } catch (Exception e) {
                    logger.error("释放号码异常",e);
                }
            }
        }.start();

    }
}
