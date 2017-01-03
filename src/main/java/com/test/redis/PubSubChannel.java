package com.test.redis;

import redis.clients.jedis.JedisPubSub;

/**
 * Created by jiaxl on 2016/12/15.
 */

/**
 * redis发布订阅练习
 */
public class PubSubChannel extends JedisPubSub {
    @Override
    public void onMessage(String channel, String message) {

        System.out.println("onMessage(String channel, String message):"+channel+":"+message);
    }


    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println("onMessage(String pattern, String channel, String message):"+pattern+":"+channel+":"+message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println("onSubscribe(String channel, int subscribedChannels):"+channel+":"+subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        super.onUnsubscribe(channel, subscribedChannels);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        super.onPUnsubscribe(pattern, subscribedChannels);
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe(String pattern, int subscribedChannels):"+pattern+":"+subscribedChannels);
    }
}
