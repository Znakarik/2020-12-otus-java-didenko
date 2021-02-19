package ru.znakarik.otus.agent.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;

public class MyMethodVisitor extends MethodVisitor {
    private boolean isOurAnnotation = false;
    private String methodDesc;
    private String name;

    public MyMethodVisitor(MethodVisitor methodVisitor, String descriptor, String name) {
        super(Opcodes.ASM7);
        this.mv = methodVisitor;
        this.methodDesc = descriptor;
        this.name = name;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(descriptor, visible);
        if (descriptor.contains("Log") && annotationVisitor != null) {
            this.isOurAnnotation = true;
        }
        return annotationVisitor;
    }

    @Override
    public void visitCode() {
        if (isOurAnnotation) {
            super.visitFieldInsn(Opcodes.GETSTATIC,
                    "java/lang/System", "out", "Ljava/io/PrintStream;");
            int varIndex = 1, numArgs = 0, p;
            for (p = 1; methodDesc.charAt(p) != ')'; p++) {
                switch (methodDesc.charAt(p)) {
                    case 'J':
                        super.visitVarInsn(Opcodes.LLOAD, varIndex);
                        ++varIndex;
                        break;
                    case 'D':
                        super.visitVarInsn(Opcodes.DLOAD, varIndex);
                        ++varIndex;
                        break;
                    case 'F':
                        super.visitVarInsn(Opcodes.FLOAD, varIndex);
                        break;
                    case 'I':
                        super.visitVarInsn(Opcodes.ILOAD, varIndex);
                        break;
                    case 'L':
                    case 'B':
                        super.visitVarInsn(Opcodes.ALOAD, varIndex);
                        p = methodDesc.indexOf(';', p);
                        break;
                    case '[':
                        super.visitVarInsn(Opcodes.ALOAD, varIndex);
                        do {
                        } while (methodDesc.charAt(++p) == '[');
                        if (methodDesc.charAt(p) == 'L') {
                            p = methodDesc.indexOf(';', p);
                        }
                        break;
                    default:
                        throw new IllegalStateException(methodDesc);
                }
                varIndex++;
                numArgs++;
            }
            String ret = "Ljava/lang/String;";
            String concatSig = new StringBuilder(++p + ret.length())
                    .append(methodDesc, 0, p).append(ret).toString();

            Handle handle = new Handle(
                    H_INVOKESTATIC,
                    "java/lang/invoke/StringConcatFactory",
                    "makeConcatWithConstants",
                    MethodType.methodType(CallSite.class, MethodHandles.Lookup.class,
                            String.class, MethodType.class, String.class, Object[].class)
                            .toMethodDescriptorString(),
                    false);
            super.visitInvokeDynamicInsn("makeConcatWithConstants", concatSig, handle,
                    "[ executed method: " + this.name + ", param: \u0001".repeat(numArgs) + "]");
            super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                    "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            super.visitCode();
        }
    }
}



