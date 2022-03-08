package dev.userteemu.gamingingmod.config

import dev.userteemu.gamingingmod.GamingingMod
import gg.essential.vigilance.Vigilant
import java.awt.Color
import kotlin.reflect.jvm.javaField

interface ColorMode {
    val name: String
    val shouldBeHidden: () -> Boolean
    fun getColor(partialTicks: Float): Color
    fun addProperties(builder: Vigilant.CategoryPropertyBuilder)
    fun afterPropertiesAdded() {}
}

class CycleColorMode(getActiveColorMode: () -> ColorMode) : ColorMode {
    override val name: String = "Color cycle"
    override val shouldBeHidden: () -> Boolean = {getActiveColorMode() == this}

    @JvmField
    var cycleSpeed = 4900

    /**
     * @return Inverted version of speed.
     * Reason for inverting: It's easier to think that larger speed means faster, and not the other way around.
     */
    fun getSpeedDivider(): Float = (5010f - cycleSpeed).coerceAtMost(5000f)

    override fun getColor(partialTicks: Float): Color =
        Color.getHSBColor((GamingingMod.INSTANCE.timer + partialTicks) / getSpeedDivider() % 1f, 1f, 1f)

    override fun addProperties(builder: Vigilant.CategoryPropertyBuilder) {
        builder.slider(::cycleSpeed, "Cycle speed", max = 5000, hidden = shouldBeHidden())
    }

    override fun afterPropertiesAdded() {
        GamingingConfig.hidePropertyIf(::cycleSpeed.javaField!!, shouldBeHidden)
    }
}

class StaticColorMode(getActiveColorMode: () -> ColorMode) : ColorMode {
    override val name: String = "Static color"
    override val shouldBeHidden: () -> Boolean = {getActiveColorMode() == this}

    @JvmField
    var color: Color = Color.BLUE

    override fun getColor(partialTicks: Float): Color = color

    override fun addProperties(builder: Vigilant.CategoryPropertyBuilder) {
        builder.color(::color, "Static color", hidden = shouldBeHidden())
    }

    override fun afterPropertiesAdded() {
        GamingingConfig.hidePropertyIf(::color.javaField!!, shouldBeHidden)
    }
}