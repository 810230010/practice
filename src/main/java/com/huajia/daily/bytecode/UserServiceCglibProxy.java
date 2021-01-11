package com.huajia.daily.bytecode;

import com.huajia.daily.common.UserService;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class UserServiceCglibProxy implements MethodInterceptor {
    private UserService target;

    public UserServiceCglibProxy(UserService target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object target, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        long begin = System.currentTimeMillis();
        Object result = methodProxy.invokeSuper(target, objects);
        System.out.println("花费时间: " + (System.currentTimeMillis() - begin));
        return result;
    }
}
