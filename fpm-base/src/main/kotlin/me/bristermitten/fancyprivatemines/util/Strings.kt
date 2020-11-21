package me.bristermitten.fancyprivatemines.util

import org.bukkit.ChatColor

fun String.color(): String = ChatColor.translateAlternateColorCodes('&', this)
