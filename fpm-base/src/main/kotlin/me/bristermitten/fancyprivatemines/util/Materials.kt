package me.bristermitten.fancyprivatemines.util

import org.bukkit.Material

fun Material.prettyName() =
    name.toLowerCase().split("_").joinToString(" ") { it.capitalize() } //TODO an actual database
