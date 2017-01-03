package com.test.thread;

import com.jia.common.util.HttpClientUtil;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jiaxl on 2016/10/22.
 *
 * Atomic下的操作是原子操作（native方法实现），多线程环境下算数操作的结果是正确的
 * 如i=i+1 不是原子性的，先从内存取i值做计算在放回内存
 * ，中间如果其他线程操作i则数据不一致
 */
public class Atomic {
    public static void main(String[] args) throws Exception {
        AtomicInteger atomicInteger=new AtomicInteger(129);
        System.out.println(atomicInteger.get());
        atomicInteger.addAndGet(1);
        Date date=new Date();
        date.setDate(1);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        System.out.println(date.getTime());
        System.out.println(new Date().getTime());
        System.out.println(atomicInteger.byteValue());

    }

}
