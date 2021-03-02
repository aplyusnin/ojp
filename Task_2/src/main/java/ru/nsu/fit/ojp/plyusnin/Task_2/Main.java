package ru.nsu.fit.ojp.plyusnin.Task_2;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import static org.objectweb.asm.Opcodes.*;

public class Main {

	public static void main(String[] args) throws IOException {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		cw.visit(V1_8, ACC_PUBLIC, "GuessNumber", null, "java/lang/Object", null);

		MethodVisitor mvc = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);

		mvc.visitCode();
		//push this on top ot the stack and call constructor
		mvc.visitVarInsn(ALOAD, 0);
		mvc.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		mvc.visitInsn(RETURN);

		mvc.visitMaxs(0, 0);

		mvc.visitEnd();

		MethodVisitor mvm = cw.visitMethod(ACC_STATIC | ACC_PUBLIC, "main", "([Ljava/lang/String;)V", null, null);

		mvm.visitCode();

		//Generate number in range [1..100]
		mvm.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "random", "()D", false);
		mvm.visitLdcInsn(99.0);
		mvm.visitInsn(DMUL);
		mvm.visitLdcInsn(1.0);
		mvm.visitInsn(DADD);
		mvm.visitInsn(D2I);

		mvm.visitVarInsn(ISTORE, 1);

		mvm.visitTypeInsn(NEW, "java/util/Scanner");

		mvm.visitInsn(DUP);
		//Add System.in into stack and call constructor that pops 2 elements
		mvm.visitFieldInsn(GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;");
		mvm.visitMethodInsn(INVOKESPECIAL, "java/util/Scanner", "<init>", "(Ljava/io/InputStream;)V", false);

		mvm.visitVarInsn(ASTORE, 2);

		mvm.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		mvm.visitLdcInsn("Guess a number from 1 to 100");
		mvm.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

		//while
		Label loop  = new Label();
		Label greater  = new Label();
		Label less  = new Label();
		Label equal  = new Label();
		{
			mvm.visitLabel(loop);

			mvm.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
			mvm.visitLdcInsn("Enter number: ");
			mvm.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
			mvm.visitVarInsn(ALOAD, 2);

			mvm.visitMethodInsn(INVOKEVIRTUAL, "java/util/Scanner", "nextInt", "()I", false);
			mvm.visitVarInsn(ISTORE, 3);

			mvm.visitVarInsn(ILOAD, 1);
			mvm.visitVarInsn(ILOAD, 3);

			mvm.visitJumpInsn(IF_ICMPNE, less);

			mvm.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
			mvm.visitLdcInsn("Exactly! Good bye!\n");
			mvm.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
			mvm.visitJumpInsn(GOTO, equal);

			mvm.visitLabel(less);
			mvm.visitVarInsn(ILOAD, 1);
			mvm.visitVarInsn(ILOAD, 3);
			mvm.visitJumpInsn(IF_ICMPGE, greater);
			{

				mvm.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
				mvm.visitLdcInsn("Greater\n");
				mvm.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
				mvm.visitJumpInsn(GOTO, loop);
			}
			mvm.visitLabel(greater);
			mvm.visitVarInsn(ILOAD, 1);
			mvm.visitVarInsn(ILOAD, 3);
			mvm.visitJumpInsn(IF_ICMPLE, loop);
			{
				mvm.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
				mvm.visitLdcInsn("Less\n");
				mvm.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
				mvm.visitJumpInsn(GOTO, loop);
			}
			mvm.visitLabel(equal);
		}

		mvm.visitInsn(RETURN);
		mvm.visitMaxs(0, 0);
		mvm.visitEnd();


		FileOutputStream out = new FileOutputStream("GuessNumber.class");
		out.write(cw.toByteArray());
		out.close();

	}

}
