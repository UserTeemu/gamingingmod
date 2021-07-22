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

    // if (GamingingMod.INSTANCE.isEnabled) return GamingingMod.INSTANCE.getColor(f);
    public InsnList getColor() {
        InsnList list = new InsnList();
        LabelNode label = new LabelNode();
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, "dev/userteemu/gamingingmod/GamingingMod", "INSTANCE", "Ldev/userteemu/gamingingmod/GamingingMod;"));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "dev/userteemu/gamingingmod/GamingingMod", "config", "Ldev/userteemu/gamingingmod/config/GamingingConfig;"));
        list.add(new FieldInsnNode(Opcodes.GETFIELD, "dev/userteemu/gamingingmod/config/GamingingConfig", "isSkyEnabled", "Z"));
        list.add(new JumpInsnNode(Opcodes.IFEQ, label));
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, "dev/userteemu/gamingingmod/GamingingMod", "INSTANCE", "Ldev/userteemu/gamingingmod/GamingingMod;"));
        list.add(new VarInsnNode(Opcodes.FLOAD, 2));
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "dev/userteemu/gamingingmod/GamingingMod", "getColorInVec3", "(F)Lnet/minecraft/util/Vec3;", false));
        list.add(new InsnNode(Opcodes.ARETURN));
        list.add(label);
        return list;
    }
}