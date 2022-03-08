package dev.userteemu.gamingingmod.asm.modifications;

import dev.userteemu.gamingingmod.asm.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

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
                methodNode.instructions.insert(getColor());
            }
        }
    }

    // if (GamingingElement.SKY.enabled) return GamingingMod.getColor(partialTicks, GamingingElement.SKY);
    public InsnList getColor() {
        InsnList list = new InsnList();
        LabelNode label = new LabelNode();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, "dev/userteemu/gamingingmod/config/GamingingElement", "SKY", "Ldev/userteemu/gamingingmod/config/GamingingElement;"));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "dev/userteemu/gamingingmod/config/GamingingElement", "enabled", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, label));
        list.add(new VarInsnNode(Opcodes.FLOAD, 2));
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, "dev/userteemu/gamingingmod/config/GamingingElement", "SKY", "Ldev/userteemu/gamingingmod/config/GamingingElement;"));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "dev/userteemu/gamingingmod/GamingingMod", "getColorInVec3", "(FLdev/userteemu/gamingingmod/config/GamingingElement;)Lnet/minecraft/util/Vec3;", false));
        list.add(new InsnNode(Opcodes.ARETURN));
        list.add(label);
        return list;
    }
}