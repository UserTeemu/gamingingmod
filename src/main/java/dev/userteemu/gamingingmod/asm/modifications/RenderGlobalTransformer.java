package dev.userteemu.gamingingmod.asm.modifications;

import dev.userteemu.gamingingmod.asm.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

public class RenderGlobalTransformer implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.renderer.RenderGlobal"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            String methodName = mapMethodName(classNode, methodNode);
            if (methodName.equals("renderWorldBorder") || methodName.equals("todo")) {
                ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
                LabelNode afterColorObtained = new LabelNode();

                while (iterator.hasNext()) {
                    AbstractInsnNode node = iterator.next();
                    if (node.getOpcode() == Opcodes.INVOKESTATIC && node.getNext().getNext().getNext().getNext().getNext().getOpcode() == Opcodes.INVOKEVIRTUAL) {
                        String methodInvokeName = mapMethodNameFromNode(node);
                        if (methodInvokeName.equals("pushMatrix") || methodInvokeName.equals("todo")) {
                            String laterMethodInvokeName = mapMethodNameFromNode(node.getNext().getNext().getNext().getNext().getNext());
                            if (laterMethodInvokeName.equals("getID") || laterMethodInvokeName.equals("todo")) {
                                methodNode.instructions.insert(node.getNext().getNext().getNext().getNext().getNext(), afterColorObtained);
                                methodNode.instructions.insert(node, storeGamingingColorsForWorldBorder(afterColorObtained));
                            }
                        }
                    }
                }
            }
        }
    }

    private InsnList storeGamingingColorsForWorldBorder(LabelNode colorCall) {
        InsnList list = new InsnList();
        LabelNode label = new LabelNode();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, "dev/userteemu/gamingingmod/GamingingMod", "INSTANCE", "Ldev/userteemu/gamingingmod/GamingingMod;"));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "dev/userteemu/gamingingmod/GamingingMod", "config", "Ldev/userteemu/gamingingmod/config/GamingingConfig;"));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "dev/userteemu/gamingingmod/config/GamingingConfig", "isWorldBorderEnabled", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, label));
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, "dev/userteemu/gamingingmod/GamingingMod", "INSTANCE", "Ldev/userteemu/gamingingmod/GamingingMod;"));
        list.add(new VarInsnNode(Opcodes.FLOAD, 2));
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "dev/userteemu/gamingingmod/GamingingMod", "getColor", "(F)Ljava/awt/Color;", false));
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/awt/Color", "getRGB", "()I", false));
        list.add(new JumpInsnNode(Opcodes.GOTO, colorCall));
        list.add(label);
        return list;
    }
}