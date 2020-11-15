package me.bristermitten.fancyprivatemines.data

import me.bristermitten.fancyprivatemines.util.areaTo
import org.bukkit.Location
import kotlin.math.max
import kotlin.math.min

class Region(private val minPoint: Location, private val maxPoint: Location) {
    val min get() = minPoint.clone()
    val max get() = maxPoint.clone()
    //Whoever decided to make Location mutable is now my arch enemy

    val points: List<Location>
        get() = minPoint areaTo maxPoint
}

fun makeRegion(point1: Location, point2: Location): Region {
    require(point1.world == point2.world) {
        "Region spans 2 different worlds"
    }

    val world = point1.world
    val min = Location(
            world,
            min(point1.x, point2.x),
            min(point1.y, point2.y),
            min(point1.z, point2.z),
    )
    val max = Location(
            world,
            max(point1.x, point2.x),
            max(point1.y, point2.y),
            max(point1.z, point2.z),
    )

    return Region(min.clone(), max.clone())
}
