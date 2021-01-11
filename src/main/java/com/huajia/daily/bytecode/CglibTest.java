package com.huajia.daily.bytecode;

import com.huajia.daily.common.UserService;
import jdk.internal.org.objectweb.asm.Type;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/*
 * @description:
 * @author: huajia
 * @create: 2020-11-17 11:20
 **/
public class CglibTest {

    public static void main(String[] args) {
        UserServiceCglibProxy proxy = new UserServiceCglibProxy(new UserService());

        // 创建加强器，创建动态代理类
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallback(proxy);


        UserService userService = (UserService) enhancer.create();
        userService.get();
    }
}
