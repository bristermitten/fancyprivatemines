package me.bristermitten.fancyprivatemines.lang

import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import org.bukkit.ChatColor

enum class LangColour(val identifier: String, val defaultValue: String) {
    Primary("Primary", "green>"),
    Secondary("Secondary", "light_purple>"),
    Error("Error", "red>"),
}
