package com.test.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by jiaxl on 2016/10/28.
 */
public class ServerClient extends ServerSocket{
    public ServerClient(int port) throws IOException {
        super(port);
        System.out.println("服务器启动，监听端口："+port);
    }

    public static void main(String[] args) throws IOException {
        ServerClient serverClient=new ServerClient(6666);
        serverClient.start();
    }

    private void start() throws IOException {

        final Socket socket=this.accept();
        System.out.println("客户端已连接："+socket.getInetAddress());
        final InputStream dis=socket.getInputStream();
        final OutputStream dos=socket.getOutputStream();

        new Thread(){
            @Override
            public void run() {
                byte[]data=new byte[1024];
                while (true){
                    try {
                        int len=dis.read(data);
                        String content=new String(data,0,len);
                        if(content.trim().equals("bye")){
                            dos.write("bye".getBytes());
                            System.out.println(socket.getInetAddress().getHostAddress()+":"+content);
                            System.exit(0);
                        }
                        System.out.println(socket.getInetAddress().getHostAddress()+":"+content);
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                System.out.println("小伙纸，现在可以聊天了，输入bye结束聊天");
                Scanner scanner=new Scanner(System.in);
                while (true){
                    try {
                        String content=scanner.nextLine();
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
}
