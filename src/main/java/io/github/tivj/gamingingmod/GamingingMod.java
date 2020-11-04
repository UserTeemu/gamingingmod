package io.github.tivj.gamingingmod;

import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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

    public boolean isEnabled = true;
    public float speed = 100F;

    public long timer = 0L;

    public GamingingMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        this.timer++;
    }

    public Vec3 getColor(float partialTicks) {
        Color color = Color.getHSBColor((this.timer + partialTicks) / this.speed % 1F, 1F, 1F);
        return new Vec3(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
    }
}