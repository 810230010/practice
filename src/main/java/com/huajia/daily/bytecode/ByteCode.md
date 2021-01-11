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
