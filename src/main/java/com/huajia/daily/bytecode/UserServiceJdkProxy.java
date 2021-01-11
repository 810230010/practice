package com.huajia.daily.bytecode;

import com.huajia.daily.common.IUserService;
import org.springframework.util.StopWatch;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UserServiceJdkProxy implements InvocationHandler {
    private IUserService userService;

    public UserServiceJdkProxy(IUserService userService) {
        this.userService = userService;
    }

    public static IUserService newProxy(IUserService userService) {
        return (IUserService) Proxy.newProxyInstance(UserServiceJdkProxy.class.getClassLoader(),
                new Class[] {IUserService.class},
                new UserServiceJdkProxy(userService));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = method.invoke(userService, args);
        stopWatch.stop();
        System.out.println("花费时间: " + stopWatch.getTotalTimeMillis());
        return result;
    }
}
