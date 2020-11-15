package me.bristermitten.fancyprivatemines.lang

import org.bukkit.ChatColor

enum class LangColour(val identifier: String, val defaultValue: ChatColor) {
    Primary("Primary", ChatColor.GREEN),
    Secondary("Secondary", ChatColor.LIGHT_PURPLE),
    Error("Error", ChatColor.RED),
}
