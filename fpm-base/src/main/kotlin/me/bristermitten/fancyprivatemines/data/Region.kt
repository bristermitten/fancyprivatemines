package me.bristermitten.fancyprivatemines.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import me.bristermitten.fancyprivatemines.util.areaTo
import org.bukkit.Bukkit
import org.bukkit.World
import kotlin.math.max
import kotlin.math.min

@Serializable
data class Region(val min: ImmutableLocation, val max: ImmutableLocation) : Iterable<ImmutableLocation> {
    @Transient
    val origin = min //Can't have this in the constructor due to KTX.Serialization #133

    init {
        require(min.world == max.world && min.world == origin.world) {
            "Worlds are not the same"
        }
    }

    val world: World
        get() = Bukkit.getWorld(min.world)

    val points by lazy {
        min areaTo max
    }

    val chunks by lazy {
        points.map { it.chunkData }
                .toSet() //TODO this is pretty wasteful, could be optimised
    }

    override fun iterator(): Iterator<ImmutableLocation> {
        return points.iterator()
    }
}

fun makeRegion(point1: ImmutableLocation, point2: ImmutableLocation): Region {
    require(point1.world == point2.world) {
        "Region spans 2 different worlds"
    }

    val world = point1.world
    val min = ImmutableLocation(
            world,
            min(point1.x, point2.x),
            min(point1.y, point2.y),
            min(point1.z, point2.z),
    )

    val max = ImmutableLocation(
            world,
            max(point1.x, point2.x),
            max(point1.y, point2.y),
            max(point1.z, point2.z),
    )

    return Region(min, max)
}

