package com.test.thread;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created by jiaxl on 2016/10/22.
 */
public class Volatile {

    private static boolean flag=true;
    public static void main(String[] args) throws InterruptedException {
        new Thread(){
            @Override
             public void run() {
                while (flag){
                    System.out.print("");
                }
                System.out.println(Thread.currentThread().getName()+" is stop");
            }
        }.start();
        Thread.sleep(10);
        System.out.println("test:");
        flag=false;

    }

     private static void test() {



    }
}
