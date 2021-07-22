package dev.userteemu.gamingingmod.config;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.io.File;

public class GamingingConfig extends Vigilant {
    public GamingingConfig() {
        super(new File("./config/gaminging.toml"));
        initialize();
    }

    @Property(
        type = PropertyType.SWITCH,
        name = "Enable sky coloring",
        category = "Gaminging", subcategory = "Gaminging"
    )
    public boolean isSkyEnabled = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Enable world border coloring",
            category = "Gaminging", subcategory = "Gaminging"
    )
    public boolean isWorldBorderEnabled = true;

    @Property(
        type = PropertyType.SLIDER,
        name = "Cycle speed",
        category = "Gaminging",
        subcategory = "Gaminging",
        max = 5000
    )
    private int speed = 4900;

    /**
     * @return Inverted version of speed.
     * Reason for inverting: It's easier to think that larger speed means faster, and not the other way around.
     */
    public float getSpeedDivider() {
        return Math.min(5010F - speed, 5000F);
    }
}
