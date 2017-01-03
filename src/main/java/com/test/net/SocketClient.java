package com.test.net;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by jiaxl on 2016/10/28.
 */
public class SocketClient extends Socket {

    String ip;
    int port;
    Socket socket;

    public SocketClient() throws IOException {
        socket = this;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        SocketClient socketClient = new SocketClient();
        socketClient.setIpAndPort();
        socketClient.start();
    }

    private void start() throws IOException {

        connectServer();
        final InputStream dis = this.getInputStream();
        final OutputStream dos = this.getOutputStream();

        new Thread() {
            @Override
            public void run() {
                byte[] data = new byte[1024];
                while (true) {
                    try {
                        int len = dis.read(data);
                        String content = new String(data, 0, len);
                        if (content.trim().equals("bye")) {
                            dos.write("bye".getBytes());
                            System.out.println(socket.getInetAddress().getHostAddress() + ":" + content);
                            System.exit(0);
                        }
                        System.out.println(socket.getInetAddress().getHostAddress() + ":" + content);
                    } catch (IOException e) {
                        System.exit(0);
                    }
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                System.out.println("小伙纸，现在可以聊天了，输入bye结束聊天");
                Scanner scanner = new Scanner(System.in);
                String content;
                while (true) {
                    try {
                        content = scanner.nextLine();
                        dos.write(content.getBytes());
                        dos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }.start();

    }

    private void connectServer() {
        try {
            this.connect(new InetSocketAddress(ip, port), 1000);
        } catch (IOException e) {
            System.out.println("连接超时，哥们儿请输入正确的地址（给你最后一次机会）");
            try {
                setIpAndPort();
                this.connect(new InetSocketAddress(ip, port), 1000);
            } catch (IOException e1) {
                System.out.println("你没救了...bye..");
                System.exit(0);
            }
        }
    }

    private void setIpAndPort() {
        System.out.print("请输入服务器ip:");
        Scanner scanner = new Scanner(System.in);
        String ip = scanner.nextLine();
        System.out.print("请输入服务器端口:");
        int port = scanner.nextInt();
        this.ip = ip;
        this.port = port;
    }
}
