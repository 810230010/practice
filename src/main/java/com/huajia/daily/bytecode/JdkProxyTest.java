package com.huajia.daily.bytecode;

import com.huajia.daily.common.IUserService;
import com.huajia.daily.common.UserService;
import com.huajia.daily.reflection.User;

import java.lang.reflect.Proxy;

/*
 * @description:
 * @author: huajia
 * @create: 2020-11-17 11:20
 **/
public class JdkProxyTest {
    public static void main(String[] args) {

        IUserService userService = UserServiceJdkProxy.newProxy(new UserService());
        userService.get();
    }
}
