package com.huajia.daily.bytecode;

import jdk.internal.org.objectweb.asm.Type;

/*
 * @description:
 * @author: huajia
 * @create: 2020-11-17 11:20
 **/
public class CglibTest {
    public static void main(String[] args) {
        System.out.println(Type.getDescriptor(String.class));
    }
}
