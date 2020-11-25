package com.huajia.daily.bootstrap;

import com.huajia.daily.common.Father;
import com.huajia.daily.common.Son;

/*
 * @description:
 * @author: huajia
 * @create: 2020-11-19 17:27
 **/
public class BindBootstrap {
    public static void main(String[] args) {
        Father father = new Son();
        father.say();
    }
}
