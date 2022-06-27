package com.zjl.bytes;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class RouterClassVisitor extends ClassVisitor {

    private String className;
    private String superClassName;

    public RouterClassVisitor(int api, ClassVisitor cv) {
        super(api, cv);
        System.out.println("RouterClassVisitor() api=" + api + ",Opcodes.ASM4=" + Opcodes.ASM4 + ",Opcodes.ASM5=" + Opcodes.ASM5);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        System.out.println("visit() version=" + version + ",name=" + name + ",superName=" + superName + ",signature=" + signature);
        this.className = name;
        this.superClassName = superName;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("visitMethod() className=" + className + ",superClassName=" + superClassName + ",name=" + name + ",desc=" + desc + ",signature=" + signature);
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        if (className.equals("com/zjl/router/ZRouter")) {
            if (name.startsWith("loadRouter")) {
//        if (className.equals("com/zjl/hardware/MainActivity") && superClassName.equals("androidx/appcompat/app/AppCompatActivity")) {
//            if (name.startsWith("onCreate")) {
                System.out.println("find com/zjl/hardware/MainActivity.onCreate");
                return new RouterMethodVisitor(api, methodVisitor, access, name, desc, className, superClassName);
            }
        }
        return methodVisitor;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        System.out.println("visitEnd() ClassVisitor visitEnd()");
    }
}
