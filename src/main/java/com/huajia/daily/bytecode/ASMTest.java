package com.huajia.daily.bytecode;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

import java.lang.reflect.Method;

/*
 * @description: ASM字节码操作demo
 * @author: huajia
 * @create: 2020-11-17 11:19
 **/
public class ASMTest {
    public static void main(String[] args) {
        byte[] classBytes = generateHelloWorldBytes();
        MyClassLoader myClassLoader = new MyClassLoader(classBytes);
        try {
            Class<?> clazz = myClassLoader.loadClass("com.huajia.daily.bytecode.HelloWorld");
            Method main = clazz.getMethod("main", String[].class);
            main.invoke(null, new Object[]{new String[]{}});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] generateHelloWorldBytes() {
        ClassWriter classWriter = new ClassWriter(0);
        // visit的参数：jdk版本，类的访问权限符，类的全限定名，泛型，父类，继承的类
        classWriter.visit(Opcodes.V1_8,
                Opcodes.ACC_PUBLIC,
                "com/huajia/daily/bytecode/HelloWorld",
                null, "java/lang/Object",
                null);
        // 添加方法：修饰符、方法名、描述符、签名、抛出的异常
        MethodVisitor mv = classWriter.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main",
                "([Ljava/lang/String;)V", null, null);
        // 执行指令：获取静态属性
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        // 加载常量 load constant
        mv.visitLdcInsn("HelloWorld!");
        // 调用方法
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        // 返回
        mv.visitInsn(Opcodes.RETURN);
        // 设置栈大小和局部变量表大小
        mv.visitMaxs(2, 1);
        // 方法结束
        mv.visitEnd();
        // 类完成
        classWriter.visitEnd();
        // 生成字节数组
        return classWriter.toByteArray();
    }

    public static class MyClassLoader extends ClassLoader{
        public MyClassLoader(byte[] classBytes) {
            this.classBytes = classBytes;
        }

        private byte[] classBytes;


        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            Class<?> clazz = defineClass(name, classBytes, 0, classBytes.length);
            if(clazz == null) {
                throw new ClassNotFoundException();
            }

            return clazz;
        }

        public Class<?> defineClass(String name, byte[] classBytes) {
            return defineClass(name, classBytes, 0, classBytes.length);
        }
    }
}
