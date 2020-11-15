package me.bristermitten.fancyprivatemines.lang

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

data class LanguageSettings(
        var lang: String,
        val colors: MutableMap<LangColour, String>
)

object PlayerLanguageSettings {
    //TODO serialize
    private val map = mutableMapOf<UUID, LanguageSettings>()

    operator fun get(player: Player): LanguageSettings {
        return map.getOrPut(player.uniqueId) {
            LanguageSettings("en", mutableMapOf())
        }
    }
}

val Player.languageSettings
    get() = PlayerLanguageSettings[this]
