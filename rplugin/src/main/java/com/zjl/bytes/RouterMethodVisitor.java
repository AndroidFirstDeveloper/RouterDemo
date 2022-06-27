package com.zjl.bytes;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class RouterMethodVisitor extends AdviceAdapter {
    /**
     * Creates a new {@link AdviceAdapter}.
     *
     * @param api    the ASM API version implemented by this visitor. Must be one
     * of {@link Opcodes#ASM4}, {@link Opcodes#ASM5} or {@link Opcodes#ASM6}.
     * @param mv     the method visitor to which this adapter delegates calls.
     * @param access the method's access flags (see {@link Opcodes}).
     * @param name   the method's name.
     * @param desc   the method's descriptor (see {@link  }).
     */
    private final String className;
    private final String superClassName;
    private final String methodName;

    protected RouterMethodVisitor(int api, MethodVisitor mv, int access, String name, String desc, String className, String superClassName) {
        super(api, mv, access, name, desc);
        this.className = className;
        this.superClassName = superClassName;
        this.methodName = name;
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        System.out.println("onMethodEnter()");
        mv.visitLdcInsn("TAG");
        mv.visitLdcInsn(className + "-------->" + methodName);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(Opcodes.POP);
        mv.visitTypeInsn(NEW, "com/zjl/routers/foodRouterImpl");
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/zjl/routers/foodRouterImpl", "<init>", "()V", false);
    }

    @Override
    protected void onMethodExit(int opcode) {
        System.out.println("onMethodExit()");
        mv.visitLdcInsn("TAG");
        mv.visitLdcInsn("this is end");
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(Opcodes.POP);
        super.onMethodExit(opcode);
    }
}
