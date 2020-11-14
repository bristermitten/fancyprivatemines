package me.bristermitten.fancyprivatemines.lang.formatter

import org.bukkit.command.CommandSender

interface LanguageFormatter {
    fun format(message: String, receiver: CommandSender): String
}
