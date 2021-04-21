package ru.znakarik.otus.agent;

import org.objectweb.asm.*;
import ru.znakarik.otus.agent.asm.MyMethodVisitor;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.UUID;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("*** my premain starts ***");
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) {
                if (className.contains("ru/znakarik/otus")) {
                    return changeMethod(className, classfileBuffer);
                }
                return classfileBuffer;
            }
        });
    }

    public static byte[] changeMethod(String className, byte[] classfileBuffer) {
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM7, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new MyMethodVisitor(methodVisitor, descriptor, name);
            }
        };
        cr.accept(cv, Opcodes.ASM7);
        byte[] finalClass = cw.toByteArray();
        try (OutputStream fos = new FileOutputStream("className" + UUID.randomUUID() + ".class")) {
            fos.write(finalClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalClass;
    }
}
