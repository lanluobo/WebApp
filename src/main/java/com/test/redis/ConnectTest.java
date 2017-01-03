package com.test.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;
import java.io.IOException;
import java.util.Map;

/**
 * <dl>
 * <dt>ConnectTest</dt>
 * <dd>Description:TODO</dd>
 * <dd>Copyright: Copyright (C) 2016</dd>
 * <dd>Company: 青牛（北京）技术有限公司</dd>
 * <dd>CreateDate: 2016年12月11日</dd>
 * </dl>
 *
 * @author 贾学雷
 */
public class ConnectTest {

    public static Jedis jedis;
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        /**
         * 连接测试
         */
//        jedis=new Jedis("192.168.204.129",6379,0);
//        System.out.println(jedis.ping());

        /**
         *List 左右增加元素练习
         */
//        listLAddString("list1","test2","test3");
//        listRAddString("list1","test1","test2");
//        System.out.println(jedis.lrange("list1",0,-1));
        /**
         * 增加Map元素
         */
//        HashMap<byte[],byte[]> map=new HashMap<byte[],byte[]>();
//        map.put("name1".getBytes(),"test1".getBytes());
//        map.put("name2".getBytes(),"test2".getBytes());
//        map.put("name3".getBytes(), "贾学雷".getBytes());
//        hashAdd("map1", map);
//        System.out.println(jedis.hget("map1","name1"));
        /**
         * 存入序列化对象 (最好是转成json串存入redis)序列化影响性能
         */
//        Student stu=new Student();
//        stu.setId(27);
//        stu.setName("贾学雷");
//        stu.setAge(23);
//        stu.setSex("男");
//        addObject("stu",stu);
//        System.out.println("反序列化结果："+SerializationUtils.deserialize(jedis.get("stu".getBytes())));
        /**
         *List插入元素，在第一个满足条件的位置插入元素（如：List中有多个jiaxuelei）
         * -1:没找到对应元素 ；正常插入后返回list长度  0：list不存在（redis中不存在空列表）
         */
//        System.out.println(jedis.lrange("list1",0,-1));
//        System.out.println(jedis.linsert("list12", BinaryClient.LIST_POSITION.AFTER,"jiaxuelei","贾学雷123"));
//        System.out.println(jedis.lrange("list1",0,-1));
//        BigDecimal d=new BigDecimal(10);
//        System.out.println("BigDecimal:"+d.add(new BigDecimal("1.025")));
        /**
         * 两种订阅,阻塞（底层死循环）
         */

//        new PubSubChannel().proceed(jedis.getClient(),"new1");
//        jedis.subscribe(new PubSubChannel(),"news");//执行不到这行
//        jedis.publish("new1","jiaxl");
        System.out.println(serialize(1));
    }
    public static String serialize(Object t) {
        if(t == null){
            return "";
        }
        if(t.getClass().isPrimitive() ||String.class.isInstance(t)){
            return String.valueOf(t);
        }
        return JSONObject.toJSONString(t);
    }

    /**
     * LPush: list想象成从左向右的链表，L则是插入左侧，取出list的第一个字符串则是最后LPush的字符串
     * @param list
     * @param value
     */
    public static void listLAddString(String list,String... value){
        jedis.lpush(list,value);
    }

    /**
     * 同上，反之亦然
     * @param list
     * @param value
     */
    public static void listRAddString(String list,String ...value){
        jedis.rpush(list,value);
    }

    /**
     *存map 底层存的一定是Map<byte[],byte[]>
     * @param mapName
     * @param value
     */
    public static void hashAdd(String mapName, Map value){
        jedis.hmset(mapName,value);
    }

    /**
     * 存序列化对象
     * @param key
     * @param obj
     */
    public static void addObject(String key,Object obj){
        jedis.set(key.getBytes(),SerializationUtils.serialize(obj));
    }
}
