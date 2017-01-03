package com.jia.train.listener;
import com.jia.train.po.U12306;
import com.jia.train.ui.CaptchaUI;
import com.jia.train.ui.UI;
import com.jia.train.util.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jiaxl on 2016/12/27.
 */
public class LoginActionListener implements ActionListener {
    private final UI frame;
    public LoginActionListener(UI frame){
        this.frame=frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        if (e.getActionCommand().equals("登录")){
            new Thread(){
                @Override
                public void run() {
                    try {
                        U12306 u12306=new U12306();
                        U12306.list.add(u12306);
                        u12306.setUser(frame.usertext.getText().trim());
                        u12306.setPsd(frame.psdtext.getText().trim());
                        Utils.setCookie(u12306);
                        new CaptchaUI(u12306);
                    }catch (Exception e){
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(frame,"请求超时");
                    }

                }
            }.start();
        }
    }
}
