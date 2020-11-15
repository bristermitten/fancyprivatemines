package me.bristermitten.fancyprivatemines.lang.formatter

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PAPIFormatter : LanguageFormatter {
    override fun format(message: String, receiver: CommandSender): String {
        return PlaceholderAPI.setPlaceholders(receiver as? Player, message)
    }
}
