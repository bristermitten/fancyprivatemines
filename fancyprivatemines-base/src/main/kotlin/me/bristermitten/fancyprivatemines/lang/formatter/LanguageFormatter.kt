package me.bristermitten.fancyprivatemines.lang.formatter

import org.bukkit.entity.Player

interface LanguageFormatter {
    fun format(message: String, player: Player?): String
}
