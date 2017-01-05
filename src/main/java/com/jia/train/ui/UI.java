package com.jia.train.ui;
import com.jia.train.listener.LoginActionListener;
import com.jia.train.listener.SessionListener;
import com.jia.train.po.U12306;
import com.jia.train.util.SessionCheck;
import com.jia.train.util.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by jiaxl on 2016/12/27.
 */
public class UI extends JFrame implements SessionListener{
    public JTextField usertext=new JTextField("jiaxueleijia10",10);
    public JLabel userlabel=new JLabel("12306账号:");
    public JPanel loginPanel =new JPanel();
    public JPasswordField psdtext=new JPasswordField("jiaxuelei158.0");
    public JLabel psdlabel=new JLabel("密码:");
    public JButton loginButton=new JButton("登录");
    public LoginActionListener listen=new LoginActionListener(this);
    public JLabel welcomeLabel=new JLabel("，欢迎您");
    public U12306 u12306=new U12306();
    public UI() throws IOException {
        initListener();
        initUI();

    }

    private void initListener() {
        loginButton.addActionListener(listen);
        SessionCheck.addSessionListener(this);
    }

    private void initUI() throws IOException {
        this.setLayout(null);

        this.setTitle("简易12306助手");
        this.setIconImage(ImageIO.read(this.getClass().getResourceAsStream("/12306.png")));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100,20,1100,700);
        loginPanel.setLayout(null);
        loginPanel.setBounds(0,0,this.getWidth(),50);
        usertext.setFont(new Font("宋体",Font.PLAIN,16));
        userlabel.setFont(new Font("宋体",Font.PLAIN,14));
        usertext.setBounds(100,10,150,25);
        userlabel.setBounds(20,13,100,20);
        loginPanel.add(userlabel);
        loginPanel.add(usertext);
        psdlabel.setBounds(260,10,150,25);
        psdlabel.setFont(new Font("宋体",Font.PLAIN,14));
        psdtext.setBounds(300,10,150,26);
        psdtext.setFont(new Font("宋体",Font.PLAIN,10));

        loginButton.setFont(new Font("宋体",Font.PLAIN,15));
        loginButton.setBounds(500,10,80,30);
        loginPanel.add(loginButton);
        loginPanel.add(psdlabel);
        loginPanel.add(psdtext);
        this.add(loginPanel);
        this.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new UI();
    }

    @Override
    public void dealSessionExpired(String msg) {
        try {
            Utils.setCookie(u12306);
            showCaptchaUI();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void showCaptchaUI() {
        try {
            new CaptchaUI(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
