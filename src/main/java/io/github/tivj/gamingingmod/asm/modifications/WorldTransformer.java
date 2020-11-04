package io.github.tivj.gamingingmod.asm.modifications;

import io.github.tivj.gamingingmod.asm.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ListIterator;

public class WorldTransformer implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.world.World"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            String methodName = mapMethodName(classNode, methodNode);
            if (methodName.equals("getSkyColor") || methodName.equals("func_72833_a")) {
                ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
                while (iterator.hasNext()) {
                    AbstractInsnNode node = iterator.next();
                    if (node.getOpcode() == Opcodes.ARETURN) {
                        methodNode.instructions.insert(getColor());
                    }
                }
            }
        }
    }

    // if (GamingingMod.INSTANCE.isEnabled) return GamingingMod.INSTANCE.getColor(f);
    public InsnList getColor() {
        InsnList list = new InsnList();
        LabelNode label = new LabelNode();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, "io/github/tivj/gamingingmod/GamingingMod", "INSTANCE", "Lio/github/tivj/gamingingmod/GamingingMod;"));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "io/github/tivj/gamingingmod/GamingingMod", "config", "Lio/github/tivj/gamingingmod/config/GamingingConfig;"));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "io/github/tivj/gamingingmod/config/GamingingConfig", "isEnabled", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, label));
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, "io/github/tivj/gamingingmod/GamingingMod", "INSTANCE", "Lio/github/tivj/gamingingmod/GamingingMod;"));
        list.add(new VarInsnNode(Opcodes.FLOAD, 2));
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "io/github/tivj/gamingingmod/GamingingMod", "getColor", "(F)Lnet/minecraft/util/Vec3;", false));
        list.add(new InsnNode(Opcodes.ARETURN));
        list.add(label);
        return list;
    }
}