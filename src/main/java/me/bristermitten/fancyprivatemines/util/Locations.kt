package me.bristermitten.fancyprivatemines.util

import org.bukkit.Location

infix fun Location.areaTo(other: Location): List<Location> {
    val blocks = mutableListOf<Location>()
    val maxX = maxOf(blockX, other.blockX)
    val maxY = maxOf(blockY, other.blockY)
    val maxZ = maxOf(blockZ, other.blockZ)

    for (x in (0..maxX)) {
        for (y in (0..maxY)) {
            for (z in (0..maxZ)) {
                blocks += clone().add(x.toDouble(), y.toDouble(), z.toDouble())
            }
        }
    }

    return blocks
}
