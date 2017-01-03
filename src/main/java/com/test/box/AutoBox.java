package com.test.box;

/**
 * <dl>
 * <dt>AutoBox</dt>
 * <dd>Description:TODO</dd>
 * <dd>Copyright: Copyright (C) 2016</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2016年12月12日</dd>
 * </dl>
 *
 * @author cy
 */
public class AutoBox {
    public static void main(String[] args) {
//        （1）包装类可以与基本数据类型比较
//        （2）因为包装类是引用数据类型，所以只有两个包装类指向用一个对象的时候才会返回true
//        分析：以上程序，当两个2自动装箱后，比较相等，但是两个128自动装箱后就不相等。
//        这是因为系统内部提供了一个缓存功能，把-128~127之间的整数自动装箱成一个Integer时，实际上直接指向对象的数值元素，而-128~127范围外的整数自动装箱成Integer时，总是新创建一个Integer实例。
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3l;
        System.out.println(c==d);
        System.out.println(e==f);
        System.out.println(c==(a+b));
        System.out.println(c.equals(a+b));
        System.out.println(g==(a+b));
        System.out.println(g.equals(a+b));

    }
}