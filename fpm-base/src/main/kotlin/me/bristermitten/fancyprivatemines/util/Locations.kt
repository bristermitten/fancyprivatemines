package me.bristermitten.fancyprivatemines.util

import me.bristermitten.fancyprivatemines.data.ImmutableLocation
import me.bristermitten.fancyprivatemines.data.ImmutablePoint
import org.bukkit.Location
import java.util.*
import kotlin.math.abs

//Parallelise this?
infix fun ImmutableLocation.areaTo(other: ImmutableLocation): List<ImmutableLocation> {
    val diffX = abs(x - other.x).toInt()
    val diffY = abs(y - other.y).toInt()
    val diffZ = abs(z - other.z).toInt()
    val blocks = ArrayList<ImmutableLocation>(diffX * diffY * diffZ)

    for (x in (0..diffX)) {
        for (y in (0..diffY)) {
            for (z in (0..diffZ)) {
                blocks += add(x.toDouble(), y.toDouble(), z.toDouble())
            }
        }
    }
    return blocks
}

infix fun ImmutablePoint.areaTo(other: ImmutablePoint): List<ImmutablePoint> {
    val diffX = abs(x - other.x)
    val diffY = abs(y - other.y)
    val diffZ = abs(z - other.z)
    val blocks = ArrayList<ImmutablePoint>(diffX * diffY * diffZ)

    for (x in (0..diffX)) {
        for (y in (0..diffY)) {
            for (z in (0..diffZ)) {
                blocks += add(x, y, z)
            }
        }
    }
    return blocks
}

