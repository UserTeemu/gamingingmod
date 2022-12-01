package dev.userteemu.gamingingmod.config

import dev.userteemu.gamingingmod.GamingingMod
import gg.essential.vigilance.Vigilant
import java.awt.Color

interface ColorMode {
    val name: String
    fun getColor(partialTicks: Float): Color
    fun addProperties(builder: Vigilant.CategoryPropertyBuilder)
    fun afterPropertiesAdded() {
        // TODO: Hide if this mode is not used. Awaits https://github.com/EssentialGG/Vigilance/pull/25.
    }
}

class CycleColorMode : ColorMode {
    override val name: String = "Color Cycle"

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
        builder.subcategory("Color Cycle Options") {
            slider(::cycleSpeed, "Cycle speed", max = 5000)
        }
    }
}

class StaticColorMode : ColorMode {
    override val name: String = "Static Color"

    @JvmField
    var color: Color = Color.BLUE

    override fun getColor(partialTicks: Float): Color = color

    override fun addProperties(builder: Vigilant.CategoryPropertyBuilder) {
        builder.subcategory("Static Color Options") {
            color(::color, "Static color")
        }
    }
}