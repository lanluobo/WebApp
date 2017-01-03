package com.test.thread;

/**
 * Created by jiaxl on 2016/11/11.
 */
public class WaitTest {
    /**
     * obj.wait()、obj.notify()、obj.notifyAll()方法使用条件：
     * 必须在拥有对象的锁（换句话说就是必须synchronized(obj){obj.wait()、obj.notify()、obj.notifyAll()}）
     * @param args
     */
    public static void main(String[] args) {
        final String s = "123";
        final String s2="234";
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (s) {
                    s.notify();
                    //抛异常
//                      s2.notify();

                }
            }
        }.start();

        try {
            Thread.sleep(200);
            synchronized (s) {
                System.out.println("start");
                s.wait(500);
                System.out.println("end");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
