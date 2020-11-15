package me.bristermitten.fancyprivatemines.util

import org.bukkit.Chunk

val Chunk.center get() = getBlock((15 + 1) / 2, (255 + 1) / 2, (15 + 1) / 2).location
