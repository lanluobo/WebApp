package com.jia.train.ui;
import com.jia.train.listener.LoginActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by jiaxl on 2016/12/27.
 */
public class UI extends JFrame{
    public JTextField usertext=new JTextField("jiaxueleijia10",10);
    public JLabel userlabel=new JLabel("12306账号:");
    public JPanel panel =new JPanel();
    public JPasswordField psdtext=new JPasswordField("jiaxuelei158.0");
    public JLabel psdlabel=new JLabel("密码:");
    public JButton loginButton=new JButton("登录");
    public LoginActionListener listen=new LoginActionListener(this);
    public UI() throws IOException {
        initUI();
        this.add(panel);
        this.setTitle("简易12306助手");
        this.setIconImage(ImageIO.read(this.getClass().getResourceAsStream("/12306.png")));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(200,100,800,600);
        this.setVisible(true);
    }

    private void initUI() {
        panel.setLayout(null);
        usertext.setFont(new Font("宋体",Font.PLAIN,16));
        userlabel.setFont(new Font("宋体",Font.PLAIN,14));
        usertext.setBounds(100,30,150,25);
        userlabel.setBounds(20,33,100,20);
        panel.add(userlabel);
        panel.add(usertext);
        psdlabel.setBounds(260,30,150,25);
        psdlabel.setFont(new Font("宋体",Font.PLAIN,14));
        psdtext.setBounds(300,30,150,26);
        psdtext.setFont(new Font("宋体",Font.PLAIN,10));

        loginButton.setFont(new Font("宋体",Font.PLAIN,15));
        loginButton.setBounds(500,30,80,30);
        loginButton.addActionListener(listen);
        panel.add(loginButton);
        panel.add(psdlabel);
        panel.add(psdtext);

    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new UI();
    }
}
