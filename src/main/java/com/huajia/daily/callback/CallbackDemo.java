package com.huajia.daily.callback;

import io.netty.channel.ChannelFuture;

import java.nio.channels.Channel;

/*
 * @description:
 * @author: huajia
 * @create: 2020-11-20 10:44
 **/
public class CallbackDemo {
    public static void main(String[] args) {
        Teacher teacher = new Teacher();
        teacher.ask(new Student());
    }
}
