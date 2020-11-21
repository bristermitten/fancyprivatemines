package me.bristermitten.fancyprivatemines.util

import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import org.bukkit.Location
import java.util.*
import kotlin.math.abs

//Parallelise this?
infix fun ImmutableLocation.areaTo(other: ImmutableLocation): List<ImmutableLocation> {
    //We may be appending a huge amount here, so don't want to constantly be resizing an array
    val blocks = LinkedList<ImmutableLocation>()
    val diffX = abs(x - other.x).toInt()
    val diffY = abs(y - other.y).toInt()
    val diffZ = abs(z - other.z).toInt()

    for (x in (0..diffX)) {
        for (y in (0..diffY)) {
            for (z in (0..diffZ)) {
                blocks += add(x.toDouble(), y.toDouble(), z.toDouble())
            }
        }
    }
    return blocks
}


