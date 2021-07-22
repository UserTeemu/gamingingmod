package dev.userteemu.gamingingmod.asm;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import dev.userteemu.gamingingmod.asm.modifications.RenderGlobalTransformer;
import dev.userteemu.gamingingmod.asm.modifications.WorldTransformer;
import dev.userteemu.gamingingmod.asm.transformer.ITransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.Collection;

public class ClassTransformer implements IClassTransformer {
    private final Multimap<String, ITransformer> transformerMap = ArrayListMultimap.create();

    public ClassTransformer() {
        registerTransformer(new WorldTransformer());
        registerTransformer(new RenderGlobalTransformer());
    }

    private void registerTransformer(ITransformer transformer) {
        for (String cls : transformer.getClassName()) {
            transformerMap.put(cls, transformer);
        }
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) return null;

        Collection<ITransformer> transformers = transformerMap.get(transformedName);
        if (transformers.isEmpty()) return bytes;

        System.out.println("Found " + transformers.size() + " transformers for {}" + transformedName);

        ClassReader reader = new ClassReader(bytes);
        ClassNode node = new ClassNode();
        reader.accept(node, ClassReader.EXPAND_FRAMES);

        transformers.forEach(transformer -> {
            System.out.println("Applying transformer " + transformer.getClass().getName() + " on {}..." + transformedName);
            transformer.transform(node, transformedName);
        });

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        try {
            node.accept(writer);
        } catch (Throwable t) {
            System.out.println("Exception when transforming " + transformedName + ": " + t.getClass().getSimpleName());
            t.printStackTrace();
        }

        return writer.toByteArray();
    }
}
