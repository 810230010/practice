package com.huajia.daily.bytecode;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.IOException;

/*
 * @description: javaassist动态代理 官方文档 http://www.javassist.org/tutorial/tutorial.html
 * @author: huajia
 * @create: 2020-11-17 11:19
 **/
public class JavaAssistTest {

    public static void changeSonSuperClass() {
        ClassPool classPool = ClassPool.getDefault();
        try {
            CtClass ctClass = classPool.get("com.huajia.daily.common.Son");
            try {
                ctClass.setSuperclass(classPool.get("com.huajia.daily.common.Mother"));
                ctClass.writeFile();
            } catch (IOException | CannotCompileException e) {
                e.printStackTrace();
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成class
     */
    public static void createClass() {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass("com.huajia.daily.bytecode.Test");

        // 为类设置构造器
        // 无参构造器
        CtConstructor constructor = new CtConstructor(null, ctClass);
        constructor.setModifiers(Modifier.PUBLIC);
        try {
            constructor.setBody("{}");
            ctClass.addConstructor(constructor);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        // 设置方法
        CtMethod method = new CtMethod(CtClass.voidType, "say", null, ctClass);
        method.setModifiers(Modifier.PUBLIC);
        try {
            method.setBody("{System.out.println(\"hello world\");}");
            ctClass.addMethod(method);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }

        try {
            Class<?> clazz = ctClass.toClass();
            Object instance = clazz.newInstance();
            clazz.getMethod("say", null).invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCatch() throws Exception{
        CtMethod m = ClassPool.getDefault().getMethod("com.huajia.daily.common.util.DateUtil", "now");
        CtClass etype = ClassPool.getDefault().get("java.io.IOException");
        m.addCatch("{ System.out.println($e); throw $e; }", etype);
    }

    public static void editExpression() throws CannotCompileException, NotFoundException {
        CtMethod cm =  ClassPool.getDefault().getMethod("com.huajia.daily.common.util.DateUtil", "now");;

        cm.instrument(
                new ExprEditor() {
                    public void edit(MethodCall m)
                            throws CannotCompileException
                    {
                        if (m.getClassName().equals("DateUtil")
                                && m.getMethodName().equals("now"))
                            m.replace("{ $1 = 0; $_ = $proceed($$); }");
                    }
                });
    }

    public static void addMethod1() throws Exception{
        ClassPool classPool = ClassPool.getDefault();
        CtClass dateUtil = classPool.get("com.huajia.daily.util.DateUtil");
        CtMethod method = CtNewMethod.make("public void move(int x) { System.out.println($1); }",
                dateUtil);
        /**
         * public void move(int x) {
         *     System.out.println(x);
         * }
         */
        dateUtil.addMethod(method);

        Class<?> aClass = dateUtil.toClass();
        Object instance = aClass.newInstance();
        aClass.getDeclaredMethod("move", int.class).invoke(instance, 5);
    }

    public static void addMethod2() throws Exception{
        ClassPool classPool = ClassPool.getDefault();
        CtClass dateUtil = classPool.get("com.huajia.daily.util.DateUtil");
        // create a abstract method first
        CtMethod method = new CtMethod(CtClass.voidType, "test", new CtClass[]{CtClass.intType}, dateUtil);

        /**
         * public void test(int a) {
         *     System.out.println(a);
         * }
         */
        // set body and modifier
        method.setModifiers(Modifier.PUBLIC);
        method.setBody("{System.out.println($1);}");

        dateUtil.addMethod(method);

        Class<?> aClass = dateUtil.toClass();
        Object instance = aClass.newInstance();
        aClass.getDeclaredMethod("test", int.class).invoke(instance, 10);
    }

    public static void main(String[] args) {
        try {
            addMethod2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
