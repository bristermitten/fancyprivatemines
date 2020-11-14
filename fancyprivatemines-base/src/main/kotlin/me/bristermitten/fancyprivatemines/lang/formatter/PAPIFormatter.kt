package me.bristermitten.fancyprivatemines.lang.formatter

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player

class PAPIFormatter : LanguageFormatter {
    override fun format(message: String, player: Player?): String {
        return PlaceholderAPI.setPlaceholders(player, message)
    }
}
