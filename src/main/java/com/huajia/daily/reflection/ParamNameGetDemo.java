package com.huajia.daily.reflection;

import io.swagger.util.PrimitiveType;
import org.springframework.core.*;
import org.springframework.core.convert.support.DefaultConversionService;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

public class ParamNameGetDemo {

    public static void test(User age) {
        System.out.println(age);
    }

    public static void main(String[] args) throws Exception{
        DefaultParameterNameDiscoverer defaultParameterNameDiscoverer = new DefaultParameterNameDiscoverer();

        Class<ParamNameGetDemo> clazz = ParamNameGetDemo.class;
        try {
            Method testMethod = clazz.getMethod("test", User.class);
            // 获取方法参数名称
            String[] parameterNames = defaultParameterNameDiscoverer.getParameterNames(testMethod);

            // 获取方法参数类型 java.lang.String
            Type[] genericParameterTypes = testMethod.getGenericParameterTypes();
            for(Type type : genericParameterTypes) {
                System.out.println(type);
            }

            // 获取方法参数class  String.class
            Class[] parameterTypeClass = testMethod.getParameterTypes();
            System.out.println(Arrays.toString(parameterNames));
            System.out.println(parameterTypeClass[0].equals(User.class));


            // 类型转变
            DefaultConversionService conversionService = new DefaultConversionService();
            boolean canConvert = conversionService.canConvert(String.class, parameterTypeClass[0]);
            System.out.println("canConvert: " + canConvert);
            System.out.println(conversionService.convert("123", parameterTypeClass[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
