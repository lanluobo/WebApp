package com.jia.train.ui;

import com.jia.common.util.HttpClientUtil;
import com.jia.train.listener.LoginActionListener;
import com.jia.train.po.U12306;
import com.jia.train.util.Utils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiaxl on 2016/12/27.
 */
public class CaptchaUI extends JFrame{

    JButton confirmButton=new JButton("确定");
    JButton updateCaptchaButton=new JButton("眼瞎？");
    JPanel panel=new JPanel();
    public U12306 u12306;
    public int captchaVersion;
    public static Map<Integer,String>selectCode=new HashMap<Integer, String>();
    ImageIcon icon1;
    static Image image;
    JLabel labelImage=new JLabel();
    public Map<Integer,Point>codeXY=new HashMap<Integer, Point>();

    static {
        try {
            image=ImageIO.read(CaptchaUI.class.getResourceAsStream("/captcha.png"));
            selectCode.put(0,"20,40");
            selectCode.put(1,"110,40");
            selectCode.put(2,"190,40");
            selectCode.put(3,"280,40");
            selectCode.put(4,"20,110");
            selectCode.put(5,"110,110");
            selectCode.put(6,"190,110");
            selectCode.put(7,"280,110");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public CaptchaUI(UI ui) throws IOException {
        this.u12306=ui.u12306;
        this.setLayout(null);
        this.setIconImage(ImageIO.read(this.getClass().getResourceAsStream("/12306.png")));
        this.setTitle("请选择验证码....");
        this.setResizable(false);
        this.setBounds(ui.getX()+ui.getWidth()/3, ui.getY()+ui.getHeight()/4, 300, 270);
        confirmButton.setBounds(90,200,100,30);
        updateCaptchaButton.setBounds(210,203,68,25);
        this.add(updateCaptchaButton);
        this.add(confirmButton);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        initListener();
        new Thread(){
            @Override
            public void run() {
                refreshCaptcha(++captchaVersion);
            }
        }.start();
    }

    /**
     * 增加按钮监听器
     */

    private void initListener() {
        /**
         * 刷新验证码按钮监听器
         */
        updateCaptchaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(){
                    @Override
                    public void run() {
                        refreshCaptcha(++captchaVersion);
                    }
                }.start();
            }
        });

        /**
         * 确定验证码按钮
         */
        confirmButton.addActionListener(new LoginActionListener(this));
    }

    /**
     * 眼瞎 刷新验证码，更新UI组件
     */
    public void refreshCaptcha(int version) {
        this.remove(labelImage);
        codeXY.clear();
        HttpClient client= HttpClientUtil.getProxyHttpClient();
        HttpGet get = new HttpGet("https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand&" + System.currentTimeMillis());
        get.setHeader("Cookie",u12306.getCookie());
        HttpResponse resp = null;
        try {
            resp = client.execute(get);
            BufferedImage bufferedImage= ImageIO.read(resp.getEntity().getContent());
            icon1 = new ImageIcon(bufferedImage);
            labelImage=new JLabel();
            labelImage.setBounds(0,0,293,190);
            labelImage.setIcon(icon1);
            labelImage.addMouseListener(listener);
            this.add(labelImage);
            this.repaint();
        } catch (IOException e) {
            e.printStackTrace();
            if (version==captchaVersion){
                JOptionPane.showMessageDialog(this,"请求超时");
                this.dispose();
            }

        }

    }

    /**
     * 刷新验证码图片及选择验证码图标
     */
    private void captchaRepaint() {

        labelImage.getGraphics().drawImage(icon1.getImage(), 0, 0, icon1.getIconWidth(),
                icon1.getIconHeight(), icon1.getImageObserver());
        for (Integer key:codeXY.keySet()){
            Point e=codeXY.get(key);
            if(e!=null){
                labelImage.getGraphics().drawImage(image, (int)e.getX()-2, (int)e.getY()-1,panel);
            }

        }
    }

    /**
     * 鼠标监听
     */
    MouseListener listener=new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println(e.getX()+":"+e.getY());
            if(e.getX()>280||e.getX()<3||e.getY()>178||e.getY()<47){
                return;
            }
            int index=Utils.calcIndex(e.getX(),e.getY());
            System.out.println("选择的是第"+(index+1)+"块图");
            if(codeXY.get(index)==null){
                codeXY.put(index,new Point(e.getX()-10,e.getY()-10));
            }else{
                codeXY.put(index,null);
            }
            captchaRepaint();
        }
    };
}
