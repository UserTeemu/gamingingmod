package dev.userteemu.gamingingmod.config

import gg.essential.vigilance.Vigilant
import java.io.File

object GamingingConfig : Vigilant(File("./config/gaminging.toml")) {
    init {
        for (element in GamingingElement.values()) {
            category(element.title) {
                element.addProperties(this)
            }
        }
        initialize()
    }

    fun afterInit() {
        for (element in GamingingElement.values()) {
            element.afterPropertiesAdded()
        }
    }
}