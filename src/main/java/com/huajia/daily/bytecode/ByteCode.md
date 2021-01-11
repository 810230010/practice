## 字节码技术
### ASM
**介绍**
- 在加载类字节码之前修改

**类描述符**
- boolean Z
- char C
- byte B
- short S
- int I
- float F
- long J
- double D
- Object Ljava/lang/Object;
- int[] [I
- Object[][] [[Ljava/lang/Object;

**方法描述符**
- void m(int i, float f) (IF)V
- int m(Object o) (Ljava/lang/Object;)I
- int[] m(int i, String s) (ILjava/lang/String;)[I
- Object m(int[] i) ([I)Ljava/lang/Object;

**访问字节码使用ClassReader**

**生成字节码使用ClassWriter**
- classWriter.visit(int jdkVersion, int modifier, String fullQualifiedName, String genericType, String parentClass, String[] extendClass);举个例子
classWriter.visit(Opcodes.V1_8,
                Opcodes.ACC_PUBLIC,
                "com/huajia/daily/bytecode/HelloWorld",
                null, "java/lang/Object",
                null);
- classWriter.visitField(int modifier, String fieldName, String argType, String genericType, Object value);
classWriter.visit(Opcodes.V1_8,
                Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL,
                "age",
                "I", null,
                new Integer(10));
- classWriter.visitMethod(int modifier, String methodName, String argType, String genericType, String[] throwExceptions)
- 每次调用visit相关方法，需要visitEnd。

**改变字节码**
```java
public byte[] trsformBytes() {
    byte[] b1 = ...;
    ClassWriter cw = new ClassWriter(0);
    // cv forwards all events to cw
    ClassVisitor cv = new ClassVisitor(ASM4, cw) { };
    ClassReader cr = new ClassReader(b1);
    cr.accept(cv, 0);
    byte[] b2 = cw.toByteArray();
    return b2;
}


public class ChangeVersionAdapter extends ClassVisitor {
    public ChangeVersionAdapter(ClassVisitor cv) {
        super(ASM4, cv);
    }
    @Override
    public void visit(int version, int access, String name,
        String signature, String superName, String[] interfaces) {
        cv.visit(V1_5, access, name, signature, superName, interfaces);
    } 
}

public class AddFieldAdapter extends ClassVisitor {
    private int fAcc;
    private String fName;
    private String fDesc;
    private boolean isFieldPresent;
    public AddFieldAdapter(ClassVisitor cv, int fAcc, String fName,
    String fDesc) {
        super(ASM4, cv);
        this.fAcc = fAcc;
        this.fName = fName;
        this.fDesc = fDesc;
    }
    @Override
    public FieldVisitor visitField(int access, String name, String desc,
    String signature, Object value) {
        if (name.equals(fName)) {
            isFieldPresent = true;
        }
        return cv.visitField(access, name, desc, signature, value);
    }
    @Override
    public void visitEnd() {
        if (!isFieldPresent) {
            FieldVisitor fv = cv.visitField(fAcc, fName, fDesc, null, null);
            if (fv != null) {
                fv.visitEnd();
            } 
        }
        cv.visitEnd();
    } 
}
```
ClassReader->ClassVisitor->ClassWriter

**可以使用ASM bytecode viewer查看**
### JavaAssis
#### 介绍
可以在编译期，也可以在运行期修改并重新加载字节码(HotSwapper)。默认情况下，jvm是不允许重新加载javassist修改的类的，需要java agent才能支持。
#### 关键的一些类
- ClassPool
- CtClass
- CtMethod

### AspectJ
#### 介绍
主要就是在编译时期，将aspect织入代码。


### Cglib
#### 介绍
cglib是基于asm实现的字节码增强工具，其不需要像jdk的动态代理，需要动态代理的类实现接口。对于singleton的代理对象或者具有实例池的代理，因为无需频繁的创建代理对象，所以比较适合采用CGLib动态代理，反正，则比较适用JDK动态代理。

### JdkProxy
#### 介绍
jdk代理需要代理类实现接口。
生成代理对象的方法调用链为Proxy.newProxyInstance()-------->getProxyClass0()------->ProxyClassFactory.apply()-------->ProxyGenerator.generateProxyClass()。
代理类在调用方法的时候，会委托给InvocationHandler的invoke方法，这一步的实现过程是在ProxyGenerator.generateProxyClass中，设置的。

    //$Proxy0继承了Proxy，且将InvocationHandler h在构造时传给了Proxy。
    //因此，super.h.invoke(this, m3, (Object[])null)其实就是调用的invoker handler的invoke方法。
    //也正是像InvocationHandler定义中所说的，【当proxy instance的方法被调用时，方法调用将会委派为invocation handler的invoke方法】。
    public final void request() throws Exception {
        try { 
            super.h.invoke(this, m3, (Object[])null);
        } catch (Exception | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }