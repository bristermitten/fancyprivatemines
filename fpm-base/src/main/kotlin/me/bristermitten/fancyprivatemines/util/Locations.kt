package me.bristermitten.fancyprivatemines.util

import org.bukkit.Location
import java.util.*
import kotlin.math.abs

infix fun Location.areaTo(other: Location): List<Location> {
    //We may be appending a huge amount here, so don't want to constantly be resizing the array
    val blocks = LinkedList<Location>()
    val diffX = abs(blockX - other.blockX)
    val diffY = abs(blockY - other.blockY)
    val diffZ = abs(blockZ - other.blockZ)

    for (x in (0..diffX)) {
        for (y in (0..diffY)) {
            for (z in (0..diffZ)) {
                blocks += clone().add(x.toDouble(), y.toDouble(), z.toDouble())
            }
        }
    }
    return blocks
}

