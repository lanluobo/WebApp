package com.jia.train;

import com.jia.common.util.HttpClientUtil;
import com.jia.train.po.U12306;
import com.jia.train.util.Utils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

/**
 * <dl>
 * <dt>Main</dt>
 * <dd>Description:TODO</dd>
 * <dd>Copyright: Copyright (C) 2016</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2016年12月26日</dd>
 * </dl>
 *
 * @author 贾学雷
 */
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        HttpClient client = HttpClientUtil.getHttpClient();

        HttpGet get = new HttpGet("https://kyfw.12306.cn/otn/login/init");
        HttpResponse resp = client.execute(get);
        U12306 u12306=new U12306();
        Header[]headers=resp.getHeaders("Set-Cookie");
        u12306.setSession(headers[0].getValue().split(";")[0]);
        u12306.setbIGipServerotn(headers[1].getValue().split(";")[0]);
        EntityUtils.consume(resp.getEntity());
        get = new HttpGet("https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand&" + System.currentTimeMillis());
        resp = client.execute(get);
        System.out.println(u12306.getCookie());
        BufferedImage bufferedImage=ImageIO.read(resp.getEntity().getContent());

        final ImageIcon icon1 = new ImageIcon(bufferedImage.getSubimage(0, 0, 293, 190));
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setBounds(100, 200, 500, 400);
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                Image img = icon1.getImage();
                g.drawImage(img, 0, 0, icon1.getIconWidth(),
                        icon1.getIconHeight(), icon1.getImageObserver());

            }
        };
        System.out.println(icon1.getIconWidth() + ":" + icon1.getIconHeight());
        panel.setBounds(0, 0, 300, 200);
        frame.add(panel);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
        u12306.setUser("jiaxueleijia10");
        u12306.setPsd("jiaxuelei158.0");
        u12306.setCode(new Scanner(System.in).nextLine());
        Utils.validateCode(u12306);
        Utils.login(u12306);

    }


}
