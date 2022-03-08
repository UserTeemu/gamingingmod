package dev.userteemu.gamingingmod;

import dev.userteemu.gamingingmod.config.GamingingConfig;
import dev.userteemu.gamingingmod.config.GamingingElement;
import gg.essential.api.EssentialAPI;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

@Mod(modid = GamingingMod.MODID, name = GamingingMod.NAME, version = GamingingMod.VERSION, clientSideOnly = true)
public class GamingingMod {
    public static final String MODID = "gamingingmod";
    public static final String NAME = "Gamingingmod";
    public static final String VERSION = "@MOD_VERSION@"; // this will be replaced by Gradle
    public static final Logger LOGGER = LogManager.getLogger("Gamingingmod");
    @Mod.Instance(MODID)
    public static GamingingMod INSTANCE;

    public long timer = 0L;

    public GamingingMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        GamingingConfig.INSTANCE.preload();
        GamingingConfig.INSTANCE.afterInit();
        EssentialAPI.getCommandRegistry().registerCommand(new GamingingCommand());
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        this.timer++;
    }

    public static Color getColor(float partialTicks, GamingingElement gamingingElement) {
        return gamingingElement.getColorMode().getColor(partialTicks);
    }

    @SuppressWarnings("unused") // used by asm
    public static Vec3 getColorInVec3(float partialTicks, GamingingElement gamingingElement) {
        Color color = getColor(partialTicks, gamingingElement);
        return new Vec3(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
    }
}