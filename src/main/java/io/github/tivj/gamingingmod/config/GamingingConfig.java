package io.github.tivj.gamingingmod.config;

import club.sk1er.vigilance.Vigilant;
import club.sk1er.vigilance.data.Property;
import club.sk1er.vigilance.data.PropertyType;

import java.io.File;

public class GamingingConfig extends Vigilant {
    public GamingingConfig() {
        super(new File("./config/gaminging.toml"));
        initialize();
    }

    @Property(
        type = PropertyType.SWITCH,
        name = "Enabled",
        category = "Gaminging", subcategory = "Gaminging"
    )
    public boolean isEnabled = true;

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
     * Reson for inverting: It's easier to think that larger speed means faster, and not the other way around.
     */
    public float getSpeedDivider() {
        return 5000F - speed;
    }
}
