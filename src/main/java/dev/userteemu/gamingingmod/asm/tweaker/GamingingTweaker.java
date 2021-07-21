package dev.userteemu.gamingingmod.asm.tweaker;

import net.modcore.loader.ModCoreSetupTweaker;

public class GamingingTweaker extends ModCoreSetupTweaker {
    public GamingingTweaker() {
        super(new String[]{GamingingFMLoadingPlugin.class.getName()});
    }
}
