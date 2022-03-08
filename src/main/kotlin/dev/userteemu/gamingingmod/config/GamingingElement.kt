package dev.userteemu.gamingingmod.config

import gg.essential.vigilance.Vigilant

enum class GamingingElement(val title: String) {
    SKY("Sky"),
    WORLD_BORDER("World Border");

    @JvmField
    var enabled = true

    private val colorModeOptions = listOf(CycleColorMode(::colorMode.getter), StaticColorMode(::colorMode.getter))

    @JvmField
    var colorModeInt: Int = 0

    var colorMode: ColorMode
        get() = colorModeOptions[colorModeInt]
        set(value) { colorModeInt = colorModeOptions.indexOf(value) }

    fun addProperties(builder: Vigilant.CategoryPropertyBuilder) {
        builder.checkbox(::enabled, "Enabled")
        builder.selector(::colorModeInt, "Color mode", options = colorModeOptions.map { it.name })

        for (mode in colorModeOptions) {
            builder.subcategory("Color options") {
                mode.addProperties(this)
            }
        }
    }

    fun afterPropertiesAdded() {
        collector
    }
}